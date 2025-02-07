package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Owner;
import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import gr.hua.dit.ds.ds_lab_2024.entities.Rent;
import gr.hua.dit.ds.ds_lab_2024.service.OwnerService;
import gr.hua.dit.ds.ds_lab_2024.service.PropertyService;
import gr.hua.dit.ds.ds_lab_2024.service.RentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/rent")
public class RentRestController {
    
    private RentService rentService;
    private OwnerService ownerService;

    public RentRestController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping("")
    public List<Rent> getAllRents() {
        return rentService.getRent();
    }
    
    @PostMapping("/new")
    public Rent createRent(@RequestBody Rent rent) {
        rentService.saveRent(rent);
        return rent;
    }

    @PutMapping("/accept")
    public void acceptRent(@PathVariable int id) {

    }

    @GetMapping("/requests/{id}")
    public List<Rent> getRentRequests(@PathVariable Long id) {
        Owner owner = ownerService.getOwner(id);
        List<Property> Properties = ownerService.getProperties(owner);
        return rentService.getRentReqOfOwner(Properties);
    }

    
    @GetMapping("/{id}")
    public Rent getRentId(@PathVariable int id) {
        return rentService.getRent(id);
    }
    
}
