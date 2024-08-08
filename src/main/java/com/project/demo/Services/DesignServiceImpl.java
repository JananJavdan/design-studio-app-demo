package com.project.demo.Services;

import com.project.demo.Repositories.DesignRepository;
import com.project.demo.model.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DesignServiceImpl implements DesignService{
    @Autowired
    private DesignRepository designRepository;
    @Override
    public Design createDesign(Design design) {
        return designRepository.save(design);
    }

    @Override
    public List<Design> getAllDesigns() {
        return designRepository.findAll();
    }

    @Override
    public Design updateDesign(Long id, Design design) {
        design.setId(id);
        return designRepository.save(design);
    }

    @Override
    public void deleteDesign(Long id) {
        designRepository.deleteById(id);

    }
}
