package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import gr.hua.dit.ds.ds_lab_2024.service.PropertyService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/property")
public class PropertyRestController {


    private PropertyService propertyService;

    public PropertyRestController(PropertyService propertyService) {
        this.propertyService = propertyService;
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
    public List<Property> showProperties(Model model) {
        return propertyService.getProperty();
    }

    @GetMapping("/{id}")
    public Property showProperty(@PathVariable Integer id, Model model){
        return propertyService.getProperty(id);
    }

    @PostMapping("/new")
    public Property createProperty(@RequestBody Property property){
        propertyService.saveProperty(property);
        return property;
    }

    /*
    @PostMapping("/new")
    public String saveTeacher(@ModelAttribute("course") Property property, Model model) {
        propertyService.saveProperty(property);
        model.addAttribute("teachers", propertyService.getProperty());
        return "teacher/teachers";
    }

    @GetMapping("/{id}/courses")
    public String showCourses(@PathVariable("id") Integer id, Model model){
        Property property = propertyService.getProperty(id);
        model.addAttribute("courses", property.getCourses());
        return "course/courses";
    }*/

}
