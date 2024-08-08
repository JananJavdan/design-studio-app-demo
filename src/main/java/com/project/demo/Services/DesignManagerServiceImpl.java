package com.project.demo.Services;

import com.project.demo.Repositories.DesignManagerRepository;
import com.project.demo.model.Design;
import com.project.demo.model.DesignManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DesignManagerServiceImpl implements DesignManagerService{
    @Autowired
    private DesignManagerRepository designManagerRepository;


    @Override
    public DesignManager createDesignManager(DesignManager designManager) {
        return designManagerRepository.save(designManager);
    }

    @Override
    public List<DesignManager> getAllDesignManagers() {
        return designManagerRepository.findAll();
    }

    @Override
    public DesignManager updateDesignManager(Long id, DesignManager designManager) {
        designManager.setId(id);
        return designManagerRepository.save(designManager);
    }

    @Override
    public void deleteDesignManager(Long id) {
        designManagerRepository.deleteById(id);

    }
}
