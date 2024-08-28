package com.project.demo.services;

import com.project.demo.models.Design;

import java.util.List;
import java.util.Optional;

public interface DesignService {
    Design createDesign(Design design);
    Optional<Design> getDesignById(Long id);
    List<Design> getAllDesigns();
    Design updateDesign(Long id, Design design);
    void deleteDesign(Long id);
    void selectItem(Long designId, String item);
    void uploadLogo(Long designId, String logo);
    void customize(Long designId, String customizationDetails, String customizationValue);
    void saveDesign(Long designId);
    byte[] generatePreview(Long designId);
}
