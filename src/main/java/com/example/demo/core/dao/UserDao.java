package com.example.demo.core.dao;

import com.example.demo.core.entiry.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User,Long> {

    User getByNameAndPassword(String name,String password);
}
