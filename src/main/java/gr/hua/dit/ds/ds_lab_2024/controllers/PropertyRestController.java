package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Owner;
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
        List<Property> props = propertyService.getProperty();
        //Don't send sensitive user info
        props.forEach(property -> {
            Owner owner = property.getOwner();
            owner.setAFM("*******");
            owner.setIdNumber("*******");
            property.setOwner(owner);
        });
        return props;
    }


    @GetMapping("/filter")
    public List<Property> filterProperties(
            @RequestParam(required = false) List<String> cities,
            @RequestParam(required = false) List<String> types,
            @RequestParam(required = false) Boolean available
    ) {

        List<Property> props = propertyService.searchProperty(cities,available,types);
        //Don't send sensitive user info
        props.forEach(property -> {
            Owner owner = property.getOwner();
            owner.setAFM("*******");
            owner.setIdNumber("*******");
            property.setOwner(owner);
        });
        return props;
    }

    @GetMapping("/cities")
    public List<String> getCities(Model model) {
        return propertyService.distinctCities();
    }

    @GetMapping("/types")
    public List<String> getPropertyTypes(Model model) {
        return propertyService.distinctPropertyTypes();
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

    @PutMapping("/{property_id}/changeAvailability")
    public Property updateProperty(@PathVariable int property_id) {
        Property property = (Property) propertyService.getProperty(property_id);

        property.setAvailable(!property.getAvailable());
        propertyService.updateProperty(property);
        return property;
    }

    @DeleteMapping("/{property_id}")
    public void deleteProperty(@PathVariable int property_id) {
        propertyService.delete(property_id);
    }



}
