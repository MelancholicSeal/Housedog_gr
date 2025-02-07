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

    public RentRestController(RentService rentService, OwnerService ownerService) {
        this.rentService = rentService;this.ownerService=ownerService;
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

    @GetMapping("/requests/{owner_id}")
    public List<Rent> getRentRequests(@PathVariable Long owner_id) {
        Owner owner = ownerService.getOwner(owner_id);
        List<Property> Properties = ownerService.getProperties(owner);
        return rentService.getRentReqOfOwner(Properties);
    }

    
    @GetMapping("/{id}")
    public Rent getRentId(@PathVariable int id) {
        return rentService.getRent(id);
    }


    @PutMapping("/accept/{id}")
    public void acceptRent(@PathVariable int id, @RequestBody Rent rent) {

    }

    @PutMapping("/reject/{id}")
    public void rejectRent(@PathVariable int id, @RequestBody Rent rent) {

    }
    
}
