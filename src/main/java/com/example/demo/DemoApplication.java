package com.example.demo;

import com.example.demo.config.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
//@SpringBootApplication
//@RestController
@EnableAutoConfiguration
public class DemoApplication {

    @RequestMapping("/")
    public String index(){
        Properties p = new Properties();
        return "user:name"+p.getPname()+"=====age:"+p.getAge();
    }
    public static void main(String[] args) {
       // SpringApplication.run(DemoApplication.class, args);
//      new  SpringApplicationBuilder(DemoApplication.class)
        SpringApplication application = new SpringApplication(DemoApplication.class);
        application.run();


    }

}
