package com.example.demo.core.web;

import com.example.demo.common.Response;
import com.example.demo.core.entiry.User;
import com.example.demo.core.servers.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public Object login(String name,String password){

        User user = userService.getNameAndPassword(name, password);
        if(user == null)
            return new Response<>("404,用户不存在或密码错误");
        return new Response<>("200,登录成功");
    }
    @RequestMapping("/save")
    public Object save(User user){
        userService.save(user);
        return new Response<>("200,保存成功");
    }
}
