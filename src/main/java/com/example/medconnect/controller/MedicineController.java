package com.example.medconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.medconnect.dto.MedicineRequest;
import com.example.medconnect.entity.Medicine;
import com.example.medconnect.service.MedicineService;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

	
	@Autowired
    private MedicineService medicineService;

    @PostMapping
    public ResponseEntity<Medicine> add(@RequestBody MedicineRequest request) {
        return ResponseEntity.ok(medicineService.addMedicine(request));
    }

    @GetMapping
    public ResponseEntity<List<Medicine>> list(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(medicineService.getMedicines(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> get(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicine> update(@PathVariable Long id, @RequestBody MedicineRequest request) {
        return ResponseEntity.ok(medicineService.updateMedicine(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.ok("Medicine deleted");
    }
}
