package com.project.demo.services;

import com.project.demo.exceptions.DesignNotFoundException;
import com.project.demo.models.Customer;
import com.project.demo.repositories.CustomerRepository;
import com.project.demo.repositories.DesignRepository;
import com.project.demo.models.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.io.ByteArrayOutputStream;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.nio.file.Files;  // For file operations
import java.nio.file.Path;  // For representing file paths
import java.nio.file.Paths;  // For manipulating paths
import java.util.UUID;  // For generating unique file names



@Service
public class DesignServiceImpl implements DesignService{

    private final DesignRepository designRepository;
    @Autowired
    public DesignServiceImpl(DesignRepository designRepository){
        this.designRepository = designRepository;
    }
    @Autowired
    private DesignManagerService designManagerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    private final String uploadDirectory = "C:/Users/Siamak/IdeaProjects/demo-textiledesign-application/uploads";

    public String saveImage(MultipartFile image) {
        String directory = "C:/Users/Siamak/IdeaProjects/demo-textiledesign-application/uploads";
        File directoryFile = new File(directory);


        if (!directoryFile.exists()) {
            directoryFile.mkdirs();
        }

        try {
            String uniqueFilename = UUID.randomUUID().toString() + "-" + image.getOriginalFilename();
            Path path = Paths.get(directory + File.separator + uniqueFilename);
            Files.write(path, image.getBytes());
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }


    public Design createDesign(String color, String font, MultipartFile image, Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));


        String imageUrl = saveImage(image);


        Design design = new Design();
        design.setColor(color);
        design.setFont(font);
        design.setImageUrl(imageUrl);
        design.setCustomer(customer);

        return designRepository.save(design);
    }



    @Override
    public List<Design> findByCustomer_Id(Long customerId) {
        return designRepository.findByCustomer_Id(customerId);
    }

    @Override
    public List<Design> findDesignsByCustomerEmail(String customerEmail) {
        return designRepository.findByCustomerEmail(customerEmail);
    }

    public List<Design> getDesignsByCustomerId(Long customerId) {
        return designRepository.findByCustomer_Id(customerId);
    }


    @Override
    public Design createDesign(Design design) {
        return designRepository.save(design);
    }


    @Override
    public Design saveDesign(Design design) {
        return designRepository.save(design);
    }


    @Override
    public Optional<Design> getDesignById(Long id) {
        return designRepository.findById(id);
    }

    @Override
    public List<Design> getAllDesigns() {
        return designRepository.findAll();
    }

    @Override
    public Design updateDesign(Long id, Design updatedDesign) {
        Design design = designRepository.findById(id)
                .orElseThrow(() -> new DesignNotFoundException("Design not found with id " + id));

        design.setName(updatedDesign.getName());
        design.setCategory(updatedDesign.getCategory());
        design.setColor(updatedDesign.getColor());
        design.setSize(updatedDesign.getSize());
        design.setLogo(updatedDesign.getLogo());

        return designRepository.save(design);
    }

    @Override
    public void deleteDesign(Long id) {
        if (!designRepository.existsById(id)) {
            throw new DesignNotFoundException("Design not found with id " + id);
        }
        designRepository.deleteById(id);
    }

    @Override
    public void selectItem(Long designId, String item) {
        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new DesignNotFoundException("Design not found with id " + designId));
        design.setCategory(item);
        designRepository.save(design);
    }

    @Override
    public void uploadLogo(Long designId, String logo) {
        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new DesignNotFoundException("Design not found with id " + designId));
        design.setLogo(logo);
        designRepository.save(design);
    }

    @Override
    public void customize(Long designId, String customizationType, String customizationValue) {
        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new DesignNotFoundException("Design not found with id " + designId));

        switch (customizationType.toLowerCase()) {
            case "text":
                design.setText(customizationValue);
                break;

            case "color":
                design.setColor(customizationValue);
                break;

            case "size":
                design.setSize(customizationValue);
                break;

            case "logo_position":
                design.setLogoPosition(customizationValue);
                break;

            case "font":
                design.setFont(customizationValue);
                break;

            default:
                throw new IllegalArgumentException("Invalid customization type: " + customizationType);
        }

        designRepository.save(design);
    }


    @Override
    public void saveDesign(Long designId) {
        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new DesignNotFoundException("Design not found with id " + designId));
        designRepository.save(design);
    }

    @Override
    public byte[] generatePreview(Long designId) {
        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new DesignNotFoundException("Design not found with id " + designId));

        // Create a new BufferedImage for the preview
        BufferedImage previewImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = previewImage.createGraphics();

        // Set the background color
        g2d.setColor(Color.decode(design.getColor()));
        g2d.fillRect(0, 0, 400, 400);

        // Draw the logo if it exists
        if (design.getLogo() != null) {
            try {
                // Load the logo image from a file path (adjust as necessary)
                BufferedImage logoImage = ImageIO.read(new File(design.getLogo()));
                g2d.drawImage(logoImage, 100, 100, null); // Position and size can be adjusted
            } catch (IOException e) {
                throw new RuntimeException("Failed to load logo image", e);
            }
        }

        // Draw the text
        g2d.setColor(Color.BLACK); // Set text color
        g2d.setFont(new Font(design.getFont(), Font.PLAIN, 20)); // Set the font and size
        g2d.drawString(design.getText(), 50, 350); // Position can be adjusted

        // Dispose the graphics context and release resources
        g2d.dispose();

        // Convert the BufferedImage to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(previewImage, "png", baos);
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate preview image", e);
        }

        return baos.toByteArray();

    }

    @Override
    public List<Design> getDesignsByCustomerName(String name) {
        return designRepository.findByCustomerName(name);
    }


    }



