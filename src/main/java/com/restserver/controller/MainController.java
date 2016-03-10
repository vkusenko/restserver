package com.restserver.controller;

import com.restserver.entity.Users;

import com.restserver.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    UsersService usersService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "Hello World";
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List <Users> getList() {
        return usersService.getAll();
    }

    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    public Users getByName(@PathVariable("username") String username) {
        return usersService.getByName(username);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public boolean deleteByName(@PathVariable("username") String username) {
        try {
            usersService.delete(username);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public boolean createUser(@RequestBody Users user) {
        Users model = usersService.getByName(user.getUsername());
        if (model == null) {
            try {
                usersService.save(user);
                return true;
            } catch (Exception ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.POST)
    public boolean updateUser(@PathVariable("username") String username, @RequestBody Users user) {
        Users model = usersService.getByName(username);
        if (model != null) {
            try {
                usersService.save(user);
                return true;
            } catch (Exception ex) {
                return false;
            }
        } else {
            return false;
        }

    }

}