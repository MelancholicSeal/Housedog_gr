package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("owner")
public class Owner extends User {
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> Properties;

    public Owner(String username, String email, String password,
                 String firstName, String lastName, String phoneNumber,
                 String AFM, String idNumber) {
        super(username, email, password, firstName, lastName, phoneNumber, AFM, idNumber);
    }

    public Owner() {

    }
}
