package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Rent;
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

    public RentRestController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping("")
    public List<Rent> getAllRents() {
        return rentService.getRent();
    }
    
    @PostMapping("")
    public Rent createRent(@RequestBody Rent rent) {
        rentService.saveRent(rent);

        return rent;
    }

    
    @GetMapping("/{id}")
    public Rent getRentId(@PathVariable int id) {
        return rentService.getRent(id);
    }
    
}
