package me.tynahan.demoinsttinfomanage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello(ModelMap modelMap) {
        modelMap.addAttribute("data", "newbie");
        return "hello";
    }
}
