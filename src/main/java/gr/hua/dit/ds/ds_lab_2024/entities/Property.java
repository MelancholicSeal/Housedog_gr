package gr.hua.dit.ds.ds_lab_2024.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    @NotBlank
    @Size(max = 50)
    @Column
    private String address;

    @NotBlank
    @Size(max = 20)
    @Column
    private String city;

    @Size(max = 20)
    @Column
    private String type;

    @NotNull
    @Column
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rent> rents;

    public Property(String address, String city, String type, Owner owner) {
        this.address = address;
        this.city = city;
        this.type = type;
        this.owner = owner;
    }

    public Property(String address, String city, Owner owner) {
        this.address = address;
        this.city = city;
        this.owner = owner;
    }

    public Property() {}

    public String getAddress() {
        return address;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return type;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
