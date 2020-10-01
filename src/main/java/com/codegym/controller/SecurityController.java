package com.codegym.controller;

import com.codegym.model.Member;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {
    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails){
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @GetMapping(value = {"/home"})
    public String Homepage(Model model){
        model.addAttribute("member",getPrincipal());
        return "home";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap modelMap){
        modelMap.addAttribute("member",getPrincipal());
        return "admin";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage(ModelMap modelMap){
        modelMap.addAttribute("member",getPrincipal());
        return "access-denied";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }



}
