package gr.hua.dit.ds.ds_lab_2024.service;

import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import gr.hua.dit.ds.ds_lab_2024.entities.Rent;
import gr.hua.dit.ds.ds_lab_2024.repositories.RentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentService {

    private RentRepository rentRepository;

    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    @Transactional
    public List<Rent> getRent() {
        return rentRepository.findAll();
    }

    @Transactional
    public void saveRent(Rent rent) {
        rentRepository.save(rent);
    }

    @Transactional
    public Rent getRent(Integer rentId) {
        return rentRepository.findById(rentId).get();
    }

    @Transactional
    public List<Rent> getRentReqOfOwner(List<Property> Properties) {
        return  rentRepository.findByPropertyIn(Properties);
    }
}
