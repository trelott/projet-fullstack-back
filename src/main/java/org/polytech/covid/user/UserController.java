package org.polytech.covid.user;

import org.polytech.covid.user.User;
import org.polytech.covid.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/superadmin/user")
    public List<User> getAllUser(){
        return userService.findAllUser();
    }

    @GetMapping("/admin/user/center/{id}")
    public List<User> getAllUserByCenter(@PathVariable("id") Integer id){
        return userService.findAllUserByCenter(id);
    }

    @GetMapping("/admin/user/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }


    @PatchMapping("/admin/user/{id}")
    public User updateUser(@PathVariable("id") Integer id, User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/admin/user/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
    }

    @PostMapping("/public/auth")
    public AuthResponse authenticate(@RequestBody AuthRequest request) {
        return this.userService.authenticate(request);
    }

    @PostMapping("/admin/register")
    public AuthResponse register(@RequestBody RegisterRequest registerRequest) {
        return this.userService.register(registerRequest);
    }
}
