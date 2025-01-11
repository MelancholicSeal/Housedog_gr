package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Owner;
import gr.hua.dit.ds.ds_lab_2024.service.OwnerService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String showOwners(Model model) {
        model.addAttribute("owners", ownerService.getOwners());
        return "owner/owners";
    }

    @GetMapping("/{id}")
    public String showOwner(@PathVariable Integer id, Model model){
        Owner owner = ownerService.getOwner(id);
        model.addAttribute("owners", owner);
        return "owner/owners";
    }

    @GetMapping("/new")
    public String addOwner(Model model){
        Owner owner = new Owner();
        model.addAttribute("owner", owner);
        return "owner/owners";

    }

    @PostMapping("/new")
    public String saveOwners(@ModelAttribute("course") Owner owner, Model model) {
        ownerService.saveOwner(owner);
        model.addAttribute("owners", ownerService.getOwners());
        return "owner/owners";
    }

}
