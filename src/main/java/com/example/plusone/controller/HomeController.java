package com.example.plusone.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/home")
    public String home (){
        System.out.println("9000");

        return "savedb.html";

    }
}
