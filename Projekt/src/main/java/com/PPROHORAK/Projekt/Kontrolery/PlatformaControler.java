package com.PPROHORAK.Projekt.Kontrolery;

import com.PPROHORAK.Projekt.DAO.PlatformyDao;
import com.PPROHORAK.Projekt.Model.Platforma;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamPlatforem;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Controller
@Data
public class PlatformaControler {

    private final PlatformyDao seznamPlatformy;


    @RequestMapping(value = {"/admin/Sprava_Platformy","/admin/sprava_platformy"})
    public String showAll(Map<String, Object> model){
     new UtilControler().GetPlatformList(model,seznamPlatformy);

        return "Spravy/Sprava_Platformy";



    }




    @PostMapping(value = {"/admin/Sprava_PlatformyUpdate","/admin/sprava_platformyupdate"})
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



        return "redirect:/admin/Sprava_Platformy";

    }

}
