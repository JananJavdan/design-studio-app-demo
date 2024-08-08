package com.project.demo.Services;

import com.project.demo.model.DesignManager;

import java.util.List;

public interface DesignManagerService {
    DesignManager createDesignManager(DesignManager designManager);
    List<DesignManager> getAllDesignManagers();
    DesignManager updateDesignManager(Long id, DesignManager designManager);
    void deleteDesignManager(Long id);
}
