package com.example.medconnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.medconnect.dto.MedicineRequest;
import com.example.medconnect.entity.Medicine;
import com.example.medconnect.repository.MedicineRepository;

@Service
public class MedicineService {

	 @Autowired
	    private MedicineRepository medicineRepository;

	    public Medicine addMedicine(MedicineRequest request) {
	        Medicine m = new Medicine();
	        applyRequest(m, request);
	        return medicineRepository.save(m);
	    }

	    public List<Medicine> getMedicines(String name) {
	        if (name != null && !name.isEmpty()) {
	            return medicineRepository.findByNameContainingIgnoreCase(name);
	        }
	        return medicineRepository.findAll();
	    }

	    public Medicine getMedicineById(Long id) {
	        return medicineRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
	    }

	    public Medicine updateMedicine(Long id, MedicineRequest request) {
	        Medicine m = getMedicineById(id);
	        applyRequest(m, request);
	        return medicineRepository.save(m);
	    }

	    public void deleteMedicine(Long id) {
	        medicineRepository.delete(getMedicineById(id));
	    }

	    private void applyRequest(Medicine m, MedicineRequest r) {
	        m.setName(r.getName());
	        m.setDescription(r.getDescription());
	        m.setCategory(r.getCategory());
	        m.setPrice(r.getPrice());
	        m.setStockQuantity(r.getStockQuantity() == null ? 0 : r.getStockQuantity());
	        m.setRequiresPrescription(r.getRequiresPrescription() != null && r.getRequiresPrescription());
	    }
}
