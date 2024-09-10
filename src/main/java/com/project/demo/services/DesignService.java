package com.project.demo.services;

import com.project.demo.models.Customer;
import com.project.demo.models.Design;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DesignService {
    Design createDesign(Design design);
    Design saveDesign(Design design);
    Optional<Design> getDesignById(Long id);
    List<Design> getAllDesigns();
    Design updateDesign(Long id, Design updatedDesign);
    void deleteDesign(Long id);
    void selectItem(Long designId, String item);
    void uploadLogo(Long designId, String logo);
    void customize(Long designId, String customizationType, String customizationValue);
    void saveDesign(Long designId);
    byte[] generatePreview(Long designId);
    List<Design> getDesignsByCustomerName(String name);
    String saveImage(MultipartFile image);
    Design createDesign(String color, String font, MultipartFile image, Long customerId);

    List<Design> findByCustomer_Id(Long customerId);

    List<Design> findDesignsByCustomerEmail(String customerEmail);
}
