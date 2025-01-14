package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Owner;
import gr.hua.dit.ds.ds_lab_2024.service.OwnerService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/owner")
public class OwnerRestController {


    private OwnerService ownerService;

    public OwnerRestController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    //    @PostConstruct
//    public void setup() {
//       Teacher t1 = new Teacher("Prof","Mark","Tailor","mark@company.com");
//       Teacher t2 = new Teacher("Lec","John","Marmor","john@example.com");
//
//       teacherService.saveTeacher(t1);
//       teacherService.saveTeacher(t2);
//    }

    @RequestMapping()
    public List <Owner> showOwners(Model model) {
        return ownerService.getOwners();
    }

    @GetMapping("/{id}")
    public Owner showOwner(@PathVariable Integer id, Model model){
        return ownerService.getOwner(id);
    }



    @PostMapping("/new")
    public Owner saveOwner(@ModelAttribute("property") Owner owner, Model model) {
        ownerService.saveOwner(owner);
        return owner;
    }

}