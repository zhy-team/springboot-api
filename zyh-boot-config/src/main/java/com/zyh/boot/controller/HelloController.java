package com.zyh.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String home(){
        System.out.println("2222");
        return "hello ";
    }
}
