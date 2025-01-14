package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Role;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.repositories.RoleRepository;
import gr.hua.dit.ds.ds_lab_2024.service.UserService;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private UserService userService;

    private RoleRepository roleRepository;

    public UserRestController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        Integer id = userService.saveUser(user);
        return userService.getUser(Long.valueOf(id));
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{user_id}")
    public User getUser(@PathVariable Long user_id) {
        User user = (User) userService.getUser(user_id);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + user_id);
        }
        return user;
    } 
    
    @PutMapping("/{user_id}")
    public User updateUser(@PathVariable Long user_id, @RequestBody User userDetails) {
        User user = (User) userService.getUser(user_id);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + user_id);
        }
        user.setEmail(userDetails.getEmail());
        user.setUsername(userDetails.getUsername());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setAFM(userDetails.getAFM());
        user.setIdNumber(userDetails.getIDNumber());
        userService.updateUser(user);
        return user;
    }

    @GetMapping("/user/role/delete/{user_id}/{role_id}")
    public String deleteRolefromUser(@PathVariable Long user_id, @PathVariable Integer role_id){
        User user = (User) userService.getUser(user_id);
        Optional<Role> roleOptional = roleRepository.findById(role_id);

        if(roleOptional.isPresent()) {
            user.getRoles().remove(roleOptional.get());
            userService.updateUser(user);
            return "Role removed successfully.";
        } else {
            throw new RuntimeException("Role not found.");
        }
    }

    @GetMapping("/user/role/add/{user_id}/{role_id}")
    public String addRoletoUser(@PathVariable Long user_id, @PathVariable Integer role_id){
        User user = (User) userService.getUser(user_id);
        Optional<Role> roleOptional = roleRepository.findById(role_id);

        if(roleOptional.isPresent()) {
            user.getRoles().add(roleOptional.get());
            userService.updateUser(user);
            return "Role added successfully.";
        } else {
            throw new RuntimeException("Role not found.");
        }
    }
}