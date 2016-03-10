package com.restserver.service;

import com.restserver.entity.Users;
import com.restserver.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRepository usersRepository;

    public void save(Users user) {
        usersRepository.saveAndFlush(user);
    }

    public void delete(String username) {
        usersRepository.delete(username);
    }

    public List<Users> getAll() {
        return usersRepository.findAll();
    }

    public Users getByName(String username) {
        return usersRepository.findOne(username);
    }
}
