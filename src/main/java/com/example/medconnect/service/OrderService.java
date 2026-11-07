package com.example.medconnect.service;

import java.math.BigDecimal;
import com.example.medconnect.dto.OrderRequest;
import com.example.medconnect.dto.OrderResponse;
import com.example.medconnect.entity.Medicine;
import com.example.medconnect.entity.Order;
import com.example.medconnect.entity.OrderItem;
import com.example.medconnect.entity.User;
import com.example.medconnect.repository.MedicineRepository;
import com.example.medconnect.repository.OrderItemRepository;
import com.example.medconnect.repository.OrderRepository;
import com.example.medconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private MedicineRepository medicineRepository;
    @Autowired private UserRepository userRepository;

    private ResponseStatusException bad(String msg) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }

    private User getLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged in user not found"));
    }

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        User patient = getLoggedInUser();

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw bad("Cart khali hai");
        }
        if (request.getDeliveryAddress() == null || request.getDeliveryAddress().isBlank()) {
            throw bad("Delivery address zaroori hai");
        }

        Order order = new Order();
        order.setPatient(patient);
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setTotalAmount(BigDecimal.ZERO);
        order.setStatus(Order.Status.PLACED);
        order = orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> saved = new ArrayList<>();

        for (OrderRequest.Item i : request.getItems()) {
            if (i.getQuantity() == null || i.getQuantity() <= 0) {
                throw bad("Quantity 1 ya usse zyada honi chahiye");
            }
            Medicine m = medicineRepository.findById(i.getMedicineId())
                    .orElseThrow(() -> bad("Medicine nahi mili: " + i.getMedicineId()));

            if (m.getStockQuantity() < i.getQuantity()) {
                throw bad(m.getName() + " ka stock kam hai (available: " + m.getStockQuantity() + ")");
            }

            m.setStockQuantity(m.getStockQuantity() - i.getQuantity());
            medicineRepository.save(m);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setMedicine(m);
            item.setQuantity(i.getQuantity());
            item.setPriceAtOrder(m.getPrice());
            saved.add(orderItemRepository.save(item));

            total = total.add(m.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())));
        }

        order.setTotalAmount(total);
        order = orderRepository.save(order);
        return toResponse(order, saved);
    }

    public List<OrderResponse> getMyOrders() {
        User patient = getLoggedInUser();
        return orderRepository.findByPatientId(patient.getId()).stream()
                .map(o -> toResponse(o, orderItemRepository.findByOrderId(o.getId())))
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(o -> toResponse(o, orderItemRepository.findByOrderId(o.getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse cancelOrder(Long id) {
        User patient = getLoggedInUser();
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> bad("Order nahi mila"));

        if (!order.getPatient().getId().equals(patient.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ye aapka order nahi hai");
        }
        if (order.getStatus() != Order.Status.PLACED) {
            throw bad("Sirf PLACED order cancel ho sakta hai");
        }

        List<OrderItem> items = orderItemRepository.findByOrderId(id);
        for (OrderItem it : items) {
            Medicine m = it.getMedicine();
            m.setStockQuantity(m.getStockQuantity() + it.getQuantity());
            medicineRepository.save(m);
        }
        order.setStatus(Order.Status.CANCELLED);
        return toResponse(orderRepository.save(order), items);
    }

    public OrderResponse updateStatus(Long id, Order.Status status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> bad("Order nahi mila"));
        order.setStatus(status);
        order = orderRepository.save(order);
        return toResponse(order, orderItemRepository.findByOrderId(id));
    }

    private OrderResponse toResponse(Order o, List<OrderItem> items) {
        List<OrderResponse.Item> list = items.stream()
                .map(i -> new OrderResponse.Item(i.getMedicine().getName(), i.getQuantity(), i.getPriceAtOrder()))
                .collect(Collectors.toList());
        return new OrderResponse(o.getId(), o.getPatient().getName(), o.getOrderDate(),
                o.getTotalAmount(), o.getStatus().name(), o.getDeliveryAddress(), list);
    }
}
