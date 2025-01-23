package gr.hua.dit.ds.ds_lab_2024.service;

import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import gr.hua.dit.ds.ds_lab_2024.entities.Owner;
import gr.hua.dit.ds.ds_lab_2024.repositories.PropertyRepository;
import gr.hua.dit.ds.ds_lab_2024.repositories.OwnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private PropertyRepository propertyRepository;

    private OwnerRepository ownerRepository;

    public PropertyService(PropertyRepository propertyRepository, OwnerRepository ownerRepository) {
        this.propertyRepository = propertyRepository;
        this.ownerRepository = ownerRepository;
    }

    @Transactional
    public List<Property> getProperty(){
        return propertyRepository.findAll();
    }

    @Transactional
    public List<Property> searchProperty(List<String> Cities, Boolean available, List<String> Type){
        return propertyRepository.findByCityInAndAvailableAndTypeIn(Cities,available,Type);
    }
    public List<String> distinctCities(){
        return propertyRepository.findDistinctCities();
    }

    @Transactional
    public void saveProperty(Property property) {
        propertyRepository.save(property);
    }

    @Transactional
    public int updateProperty(Property property) {
       property = propertyRepository.save(property);
       return property.getId(); 
    }

    @Transactional
    public Property getProperty(Integer propertyId) {
        return propertyRepository.findById(propertyId).get();
    }

    @Transactional
    public void assignOwnerToProperty(int propertyId, Owner owner) {
        Property property = propertyRepository.findById(propertyId).get();
        System.out.println(property);
        System.out.println(property.getOwner());
        property.setOwner(owner);
        System.out.println(property.getOwner());
        propertyRepository.save(property);
    }

    @Transactional
    public void unassignOwnerFromProperty(int propertyId) {
        Property property = propertyRepository.findById(propertyId).get();
        property.setOwner(null);
        propertyRepository.save(property);
    }

    /*
    @Transactional
    public void assignStudentToCourse(int courseId, Student student) {
        Course course = courseRepository.findById(courseId).get();
        course.addStudent(student);
        System.out.println("Course students: ");
        System.out.println(course.getStudents());
        courseRepository.save(course);
    }
    */

}
