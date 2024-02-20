package com.muralis.pdfReport.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.muralis.pdfReport.domain.Student;


@Controller
public class ThymeleafController {
    

    @GetMapping("home")
    public String getMethodName(ModelMap model) {
        model.addAttribute("nomeDoAtributo", "Treinaweb");
        model.addAttribute("areaValor", "Aplicações em Geofísica e Sensoriamento Remoto/ Brasil/ Bahia\r");

        model.addAttribute("students",List.of(new Student("nome","outro"),new Student("numer","dasd")));
        return "home";
    }
    
}
