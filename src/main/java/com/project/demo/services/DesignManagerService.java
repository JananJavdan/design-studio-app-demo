package com.project.demo.services;

import com.project.demo.models.Design;
import com.project.demo.models.DesignManager;

import java.util.List;
import java.util.Optional;

public interface DesignManagerService {
    Design createDesign(Long designManagerId, Design design);
    Design updateDesign(Long designManagerId, Long designId, Design updatedDesign);
    void deleteDesign(Long designManagerId, Long designId);
    List<Design> fetchDesigns(Long designManagerId);
    void assistCustomer(Long designManagerId, Long customerId);
    Optional<DesignManager> findById(Long designManagerId);
}
