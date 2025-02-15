package gr.hua.dit.ds.ds_lab_2024.controllers;

import gr.hua.dit.ds.ds_lab_2024.entities.Property;
import gr.hua.dit.ds.ds_lab_2024.entities.Role;
import gr.hua.dit.ds.ds_lab_2024.entities.User;
import gr.hua.dit.ds.ds_lab_2024.repositories.RoleRepository;
import gr.hua.dit.ds.ds_lab_2024.service.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;


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
        Long id = userService.saveUser(user);
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
        user.setAFM("*******");
        user.setIdNumber("*******");
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


    @DeleteMapping("/{user_id}")
    public void deleteUser(@PathVariable Long user_id){
        userService.delete(user_id);
    }
}