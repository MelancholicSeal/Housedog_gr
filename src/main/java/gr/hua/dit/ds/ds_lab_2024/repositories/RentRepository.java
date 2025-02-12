package gr.hua.dit.ds.ds_lab_2024.repositories;

import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import gr.hua.dit.ds.ds_lab_2024.entities.Rent;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Integer> {
    List<Rent> findByPropertyIn(List<Property> properties);


    List<Rent> findByUser(User user);

    List<Rent> findByPropertyInAndRented(List<Property> properties, boolean b);
}