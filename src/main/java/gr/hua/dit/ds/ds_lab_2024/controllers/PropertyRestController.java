package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import gr.hua.dit.ds.ds_lab_2024.service.PropertyService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/property")
public class PropertyRestController {


    private PropertyService propertyService;

    public PropertyRestController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }


    @GetMapping()
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

    @PutMapping("/{property_id}")
    public Property updateProperty(@PathVariable int property_id, @RequestBody Property propertyDetails) {
        Property property = (Property) propertyService.getProperty(property_id);

        property.setAddress(propertyDetails.getAddress());
        property.setAvailable(propertyDetails.getAvailable());
        property.setCity(propertyDetails.getCity());
        property.setOwner(propertyDetails.getOwner());
        property.setType(propertyDetails.getType());
        propertyService.updateProperty(property);
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
