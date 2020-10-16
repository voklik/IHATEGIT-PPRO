package com.PPROHORAK.Projekt.Kontrolery;

import com.PPROHORAK.Projekt.DAO.PlatformyDao;
import com.PPROHORAK.Projekt.Model.Platforma;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamPlatforem;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
@Controller
@Data
public class PlatformaControler {

    private final PlatformyDao seznamPlatformy;


    @RequestMapping("/Sprava_Platformy")
    public String showAll(Map<String, Object> model){
        SeznamPlatforem platformy = new SeznamPlatforem();
        platformy.getSeznamPlatformy().addAll(seznamPlatformy.findAll());

        model.put("ListPlatformy", platformy.getSeznamPlatformy());


        return "Sprava_Platformy";



    }




    @PostMapping("/Sprava_PlatformyUpdate")
    public String updateUcetSprava(
            @RequestParam("platformaID") Integer platformaID,
            @RequestParam("action") String akce ,@RequestParam("nazev") String nazev)
    {


        if(akce.equals("Delete"))
        {
            System.out.println("Delete");
            seznamPlatformy.deleteById(platformaID);
        }
        else if(akce.equals("Create"))
        {
            Platforma nova = new Platforma();
            nova.setNazev(nazev);
            seznamPlatformy.save(nova);
        }
        else  if (akce.equals("Update"))
        {

            System.out.println("update");
            System.out.println(nazev);
            Platforma zmenena = new Platforma();
          zmenena = seznamPlatformy.findById((platformaID));
            seznamPlatformy.save(zmenena);

        }



        return "redirect:/Sprava_Platformy";

    }

}
