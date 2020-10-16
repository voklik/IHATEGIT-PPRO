package com.PPROHORAK.Projekt.Kontrolery;


import com.PPROHORAK.Projekt.DAO.StavyDao;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamStavu;
import com.PPROHORAK.Projekt.Model.Stav;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
@Controller
@Data
public class StavControler {




    private final StavyDao seznamStavu ;



    @RequestMapping("/Sprava_Stavy")
    public String showAll(Map<String, Object> model){
        SeznamStavu stavy = new SeznamStavu();
        stavy.getSeznamStavu().addAll(seznamStavu.findAll());

        model.put("ListStavu", stavy.getSeznamStavu());


        return "Sprava_Stavy";



    }




    @PostMapping("/Sprava_StavuUpdate")
    public String updateUcetSprava(
        @RequestParam("stavID") Integer stavID,
        @RequestParam("action") String akce ,@RequestParam("nazev") String nazev)
    {


        if(akce.equals("Delete"))
        {
            System.out.println("Delete");
            seznamStavu.deleteById(stavID);
        }
        else if(akce.equals("Create"))
        {
            Stav nova = new Stav();
            nova.setNazev(nazev);
            seznamStavu.save(nova);
        }
        else  if (akce.equals("Update"))
        {

            System.out.println("update");
            System.out.println(nazev);
            Stav zmenena = new Stav();
            zmenena = seznamStavu.findById((stavID));
            seznamStavu.save(zmenena);

        }



        return "redirect:/Sprava_Stavy";

    }
}