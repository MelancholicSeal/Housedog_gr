package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import gr.hua.dit.ds.ds_lab_2024.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("property")
public class PropertyController {


    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
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
    public String showTeachers(Model model) {
        model.addAttribute("teachers", propertyService.getProperty());
        return "teacher/teachers";
    }

    @GetMapping("/{id}")
    public String showTeacher(@PathVariable Integer id, Model model){
        Property property = propertyService.getProperty(id);
        model.addAttribute("teachers", property);
        return "teacher/teachers";
    }

    @GetMapping("/new")
    public String addTeacher(Model model){
        Property property = new Property();
        model.addAttribute("teacher", property);
        return "teacher/teacher";

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
