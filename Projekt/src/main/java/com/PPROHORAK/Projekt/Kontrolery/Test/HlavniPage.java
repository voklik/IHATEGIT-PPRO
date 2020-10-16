package com.PPROHORAK.Projekt.Kontrolery.Test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("")
public class HlavniPage {


    @GetMapping
    public String hello ()
    {
        return "Hlavní stránka";
    }


}
