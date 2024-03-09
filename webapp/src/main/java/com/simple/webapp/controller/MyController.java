package com.simple.webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.webapp.CountryInfo;
import com.simple.webapp.User;
import com.simple.webapp.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class MyController {

    private static final String COUNTRIES_API = "https://restcountries.com/v3.1/all?fields=name,capital";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String homePage(Model model) throws JsonProcessingException {
        String countriesResponse = restTemplate.getForObject(COUNTRIES_API, String.class);
        List<CountryInfo> countryInfoList = objectMapper.readValue(countriesResponse, List.class);
        model.addAttribute("countriesInfo", countryInfoList);
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Name of the Thymeleaf template
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute User user) {
        userRepository.save(user);
        return "success";
    }
}

