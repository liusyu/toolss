package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "user",locations={"classpath:other.properties"})
@ConfigurationProperties(prefix = "user")
public class Properties {

    private String pname;
    private int age;

    public String getPname() {
        return pname;
    }

    public void setPname(String name) {
        this.pname = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
