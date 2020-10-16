package com.PPROHORAK.Projekt.Kontrolery;

import com.PPROHORAK.Projekt.DAO.PlatformyDao;
import com.PPROHORAK.Projekt.DAO.ProduktyDao;
import com.PPROHORAK.Projekt.Model.Platforma;
import com.PPROHORAK.Projekt.Model.Produkt;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamPlatforem;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamProduktu;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
@Controller
@Data
public class ProduktControler {

    private final PlatformyDao seznamPlatformy;
    private final ProduktyDao seznamProduktu;

    @RequestMapping("/Sprava_Produkty")
    public String showAll(Map<String, Object> model){
        SeznamProduktu produkty = new SeznamProduktu();
        produkty.getSeznamProduktu().addAll(seznamProduktu.findAll());

        model.put("ListProdukty", produkty.getSeznamProduktu());

        SeznamPlatforem platformy = new SeznamPlatforem();
        platformy.getSeznamPlatformy().addAll(seznamPlatformy.findAll());

        model.put("ListPlatformy", platformy.getSeznamPlatformy());

        return "Sprava_Produkty";



    }




    @PostMapping("/Sprava_ProduktyUpdate")
    public String updateUcetSprava(
            @RequestParam("produktID") Integer produktID,
            @RequestParam("action") String akce ,@RequestParam("nazev") String nazev,
            @RequestParam("cena") Integer cena, @RequestParam("sleva") Integer sleva,
            @RequestParam("platforma") Integer platformaID


    )
    {


        if(akce.equals("Delete"))
        {
            System.out.println("Delete");
            seznamProduktu.deleteById(produktID);
        }
        else if(akce.equals("Create"))
        {
            Produkt novy = new Produkt();
            novy.setCena(cena);
            novy.setSleva(sleva);
            novy.setNazev(nazev);
           novy.setPlatforma(seznamPlatformy.findById(platformaID));
           seznamProduktu.save(novy);

        }
        else  if (akce.equals("Update"))
        {

            Produkt produkt = seznamProduktu.findById(produktID);
            produkt.setCena(cena);
            produkt.setSleva(sleva);
            produkt.setNazev(nazev);
            produkt.setPlatforma(seznamPlatformy.findById(platformaID));
            seznamProduktu.save(produkt);

        }



        return "redirect:/Sprava_Produkty";

    }

}
