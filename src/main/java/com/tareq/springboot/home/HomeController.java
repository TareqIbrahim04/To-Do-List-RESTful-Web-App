package com.tareq.springboot.home;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @RequestMapping(value = "/")
    public String gretting(){
        return "Hello to TareqAndulsi Web-site!";
    }
    @GetMapping(value = "/{name}")
    public String greetingWithName(@PathVariable String name){
        return "Welcome to TareqAndulsi Web-Site, " + name;
    }
}
