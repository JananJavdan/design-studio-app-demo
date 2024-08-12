package com.project.demo.Controller;

import com.project.demo.Services.DesignService;
import com.project.demo.model.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/designs")
public class DesignController {

    @Autowired
    private DesignService designService;

    @PostMapping
    public ResponseEntity<Design> createDesign(@RequestBody Design design) {
        Design newDesign = designService.createDesign(design);
        return ResponseEntity.ok(newDesign);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Design> getDesignById(@PathVariable Long id) {
        return ResponseEntity.ok(designService.getDesignById(id).orElseThrow(() -> new RuntimeException("Design not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDesign(@PathVariable Long id) {
        designService.deleteDesign(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Design>> getAllDesigns() {
        return ResponseEntity.ok(designService.getAllDesigns());
    }


}
