package com.project.demo.controllers;


import com.project.demo.repositories.CustomerRepository;
import com.project.demo.services.CustomerService;
import com.project.demo.services.DesignManagerService;
import com.project.demo.services.DesignService;
import com.project.demo.models.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/designs")
public class DesignController {

    @Autowired
    private DesignService designService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DesignManagerService designManagerService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createDesign(
            @RequestParam("color") String color,
            @RequestParam("font") String font,
            @RequestParam("image") MultipartFile image,
            @RequestParam("customerId") Long customerId,
            Authentication authentication) {


        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String customerEmail = userDetails.getUsername();

        Design newDesign = designService.createDesign(color, font, image, customerId);
        return new ResponseEntity<>(newDesign, HttpStatus.CREATED);
    }

    private String saveImage(MultipartFile image) {
        // Define the directory to save the images
        String uploadDir = "uploads/";

        // Generate a unique file name to avoid conflicts
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

        try {
            // Create the directory if it doesn't exist
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();  // Create the directory if not present
            }

            // Create the file path
            Path filePath = Paths.get(uploadDir + fileName);

            // Write the file to the disk
            Files.write(filePath, image.getBytes());

            // Return the path of the saved image, this can be used later to fetch the image
            return fileName;

        } catch (IOException e) {
            // Log the error and throw an exception if file saving fails
            throw new RuntimeException("Failed to store the image file", e);
        }
    }



    @GetMapping("/view")
    public ResponseEntity<List<Design>> getAllDesigns() {
        List<Design> designs = designService.getAllDesigns();
        return new ResponseEntity<>(designs, HttpStatus.OK);
    }

    @GetMapping("/my-designs")
    @PreAuthorize("hasRole('CUSTOMER')")  // Alleen klanten kunnen toegang krijgen tot deze lijst
    public ResponseEntity<List<Design>> getCustomerDesigns(Authentication authentication) {
        // Haal de gebruiker op
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String customerEmail = userDetails.getUsername();

        // Roep de service aan om ontwerpen op basis van klant-email op te halen
        List<Design> customerDesigns = designService.findDesignsByCustomerEmail(customerEmail);

        // Stuur een HTTP 200 response terug met de lijst van ontwerpen
        return new ResponseEntity<>(customerDesigns, HttpStatus.OK);
    }

    // Methode om een ontwerp op ID te krijgen
    @GetMapping("/{id}")
    public ResponseEntity<Design> getDesignById(@PathVariable Long id) {
        return ResponseEntity.ok(designService.getDesignById(id)
                .orElseThrow(() -> new RuntimeException("Design not found")));
    }

    // Methode om ontwerpen van een specifieke klant op naam te krijgen
    @GetMapping("/customer/{name}")
    public ResponseEntity<List<Design>> getDesignsByCustomerName(@PathVariable String name) {
        List<Design> designs = designService.getDesignsByCustomerName(name);
        return ResponseEntity.ok(designs);
    }

    // Methode om een ontwerp te verwijderen op basis van ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDesign(@PathVariable Long id) {
        designService.deleteDesign(id);
        return ResponseEntity.noContent().build();
    }



}

