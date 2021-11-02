package com.ituwei.app.controller;

import com.ituwei.app.Exception.ResourceNotFoundException;
import com.ituwei.app.Model.User;
import com.ituwei.app.Repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class UserController {
    private UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("user/save")
    public User saveuser(@RequestBody User user) {
        return this.userRepo.save(user);

    }

    @GetMapping("user/all")
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok(

                this.userRepo.findAll());

    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {

        User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok().body(user);

    }

    @PutMapping("user/{id}")
    public User updateUser(@RequestBody User newUser, @PathVariable(value = "id") Long id) {

        return this.userRepo.findById(id).map(user -> {
            user.setUsername(newUser.getUsername());
            user.setPassword(newUser.getPassword());
            user.setEmail(newUser.getEmail());

            return this.userRepo.save(user);

        }

        ).orElseGet(() -> {
            newUser.setId(id);
            return this.userRepo.save(newUser);
        });

    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id) {

        User user = this.userRepo.findById(id).orElseThrow(() ->

        new ResourceNotFoundException("User not found" + id));
        this.userRepo.delete(user);
        return ResponseEntity.ok().build();

    }
}
