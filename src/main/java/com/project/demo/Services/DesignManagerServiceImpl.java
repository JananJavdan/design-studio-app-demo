package com.project.demo.Services;

import com.project.demo.Exceptions.DesignNotFoundException;
import com.project.demo.Repositories.CustomerRepository;
import com.project.demo.Repositories.DesignManagerRepository;
import com.project.demo.Repositories.DesignRepository;
import com.project.demo.model.Customer;
import com.project.demo.model.Design;
import com.project.demo.model.DesignManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DesignManagerServiceImpl implements DesignManagerService {

    private final DesignManagerRepository designManagerRepository;
    private final DesignRepository designRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public DesignManagerServiceImpl(DesignManagerRepository designManagerRepository, DesignRepository designRepository, CustomerRepository customerRepository) {
        this.designManagerRepository = designManagerRepository;
        this.designRepository = designRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Design createDesign(Long designManagerId, Design design) {
        DesignManager designManager = designManagerRepository.findById(designManagerId)
                .orElseThrow(() -> new RuntimeException("Design Manager not found with id " + designManagerId));

        design.setDesignManager(designManager);
        return designRepository.save(design);
    }

    @Override
    public Design updateDesign(Long designManagerId, Long designId, Design updatedDesign) {
        DesignManager designManager = designManagerRepository.findById(designManagerId)
                .orElseThrow(() -> new RuntimeException("Design Manager not found with id " + designManagerId));

        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new DesignNotFoundException("Design not found with id " + designId));

        if (!design.getDesignManager().equals(designManager)) {
            throw new RuntimeException("Design not managed by this Design Manager");
        }
        design.setName(updatedDesign.getName());
        design.setCategory(updatedDesign.getCategory());
        design.setColor(updatedDesign.getColor());
        design.setSize(updatedDesign.getSize());
        design.setLogo(updatedDesign.getLogo());

        return designRepository.save(design);
    }

    @Override
    public void deleteDesign(Long designManagerId, Long designId) {
        DesignManager designManager = designManagerRepository.findById(designManagerId)
                .orElseThrow(() -> new RuntimeException("Design Manager not found with id " + designManagerId));

        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new DesignNotFoundException("Design not found with id " + designId));

        if (!design.getDesignManager().equals(designManager)) {
            throw new RuntimeException("Design not managed by this Design Manager");
        }
        designRepository.delete(design);
    }

    @Override
    public List<Design> fetchDesigns(Long designManagerId) {
        DesignManager designManager = designManagerRepository.findById(designManagerId)
                .orElseThrow(() -> new RuntimeException("Design Manager not found with id " + designManagerId));

        return designRepository.findByDesignManager(designManager);
    }

    @Override
    public void assistCustomer(Long designManagerId, Long customerId) {
        DesignManager designManager = designManagerRepository.findById(designManagerId)
                .orElseThrow(() -> new RuntimeException("Design Manager not found with id " + designManagerId));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
    }
}
