package gr.hua.dit.ds.ds_lab_2024.service;

import gr.hua.dit.ds.ds_lab_2024.entities.Owner;
import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.repositories.OwnerRepository;
import gr.hua.dit.ds.ds_lab_2024.repositories.PropertyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private PropertyRepository propertyRepository;

    private OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository, PropertyRepository propertyRepository) {
        this.ownerRepository = ownerRepository;
        this.propertyRepository = propertyRepository;
    }

    @Transactional
    public List<Owner> getOwners() {
        return ownerRepository.findAll();
    }

    @Transactional
    public void saveOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    @Transactional
    public Owner getOwner(Long ownerId) {
        return ownerRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("User not found."));
    }

    public List<Property> getProperties(Owner owner) {
        return propertyRepository.findByOwner(owner);
    }
}
