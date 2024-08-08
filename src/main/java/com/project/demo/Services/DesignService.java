package com.project.demo.Services;

import com.project.demo.model.Design;

import java.util.List;

public interface DesignService {
    Design createDesign(Design design);
    List<Design> getAllDesigns();
    Design updateDesign(Long id, Design design);
    void deleteDesign(Long id);
}
