package com.project.demo.Services;

import com.project.demo.Exceptions.DesignNotFoundException;
import com.project.demo.Repositories.DesignRepository;
import com.project.demo.model.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
