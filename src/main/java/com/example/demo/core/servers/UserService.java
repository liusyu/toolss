package com.example.demo.core.servers;

import com.example.demo.core.dao.UserDao;
import com.example.demo.core.entiry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserService  {

    @Autowired
    private UserDao userDao;
    public User getNameAndPassword(String password,String name){
        return userDao.getByNameAndPassword(name,password);
    }

    public Object save(User user){
        return userDao.save(user);
    }
}
