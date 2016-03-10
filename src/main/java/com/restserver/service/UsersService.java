package com.restserver.service;


import com.restserver.entity.Users;

import java.util.List;

public interface UsersService {
    void save(Users user);
    void delete(String username);
    List <Users> getAll();
    Users getByName(String username);
}
