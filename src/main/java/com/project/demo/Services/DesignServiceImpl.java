package com.project.demo.Services;

import com.project.demo.Exceptions.DesignNotFoundException;
import com.project.demo.Repositories.DesignRepository;
import com.project.demo.model.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class DesignServiceImpl implements DesignService{

    private final DesignRepository designRepository;
    @Autowired
    public DesignServiceImpl(DesignRepository designRepository){
        this.designRepository = designRepository;
    }
    @Override
    public Design createDesign(Design design) {
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
}
