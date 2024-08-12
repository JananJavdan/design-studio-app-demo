package com.project.demo.Controller;

import com.project.demo.Services.DesignService;
import com.project.demo.model.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class DesignManagerController {
    @Autowired
    private DesignService designService;

    @GetMapping("/designs")
    public ResponseEntity<List<Design>> getAllDesigns() {
        return ResponseEntity.ok(designService.getAllDesigns());
    }

    @GetMapping("/designs/{id}")
    public ResponseEntity<Design> getDesignById(@PathVariable Long id) {
        return ResponseEntity.ok(designService.getDesignById(id).orElseThrow(() -> new RuntimeException("Design not found")));
    }

    @PutMapping("/designs/{id}")
    public ResponseEntity<Design> updateDesign(@PathVariable Long id, @RequestBody Design design) {
        return ResponseEntity.ok(designService.updateDesign(id, design));
    }

    @DeleteMapping("/designs/{id}")
    public ResponseEntity<Void> deleteDesign(@PathVariable Long id) {
        designService.deleteDesign(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<Void> approveDesign(@PathVariable Long id) {
        Design design = designService.getDesignById(id).orElseThrow(() -> new RuntimeException("Design not found"));
        design.setApproved(true);
        designService.updateDesign(id, design);
        return ResponseEntity.noContent().build();
    }
}
