package gr.hua.dit.ds.ds_lab_2024.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Owner extends User{


    @OneToMany(mappedBy = "owner")
    private List<Property> Properties;
}
