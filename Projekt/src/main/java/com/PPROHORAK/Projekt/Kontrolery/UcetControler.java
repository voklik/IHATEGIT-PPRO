package com.PPROHORAK.Projekt.Kontrolery;

import com.PPROHORAK.Projekt.DAO.AdresyDao;
import com.PPROHORAK.Projekt.DAO.TypyUctuDao;
import com.PPROHORAK.Projekt.DAO.UctyDao;
import com.PPROHORAK.Projekt.Model.Adresa;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamUcet;
import com.PPROHORAK.Projekt.Model.Ucet;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@Data
public class UcetControler {

    private final UctyDao seznamUcty;
    private final TypyUctuDao seznamyTypyUcty;
    private final AdresyDao seznamyAdres;

    public UcetControler(UctyDao seznamUcty, TypyUctuDao seznamyTypyUcty, AdresyDao seznamyAdres) {
        this.seznamUcty = seznamUcty;
        this.seznamyTypyUcty = seznamyTypyUcty;
        this.seznamyAdres = seznamyAdres;
    }

    /*
    @GetMapping
    public String hello ()
    {
        return "hello youtube";
    }
    */
    @RequestMapping("/admin")
    public String showAllUsers(Map<String, Object> model){
        SeznamUcet ucty = new SeznamUcet();
        ucty.getSeznamUctu().addAll(seznamUcty.findAll());

        model.put("ListUcet", ucty.getSeznamUctu());

        return "Ucty";
    }
    @RequestMapping("/Sprava_Ucty")
    public String showAll(Map<String, Object> model){
        SeznamUcet ucty = new SeznamUcet();
        ucty.getSeznamUctu().addAll(seznamUcty.findAll());

        model.put("ListUcet", ucty.getSeznamUctu());


            return "Sprava_Ucty";



    }




    @PostMapping("/UpdateUcetSprava")
    public String updateUcetSprava( @RequestParam("ucetID") Integer ucetID,
            @RequestParam("action") String akce ,@RequestParam("jmeno") String jmeno,
             @RequestParam("prijmeni") String prijmeni, @RequestParam("login") String login,
            @RequestParam("heslo") String heslo,@RequestParam("ulice") String ulice,
           @RequestParam("cp") String cp,@RequestParam("mesto") String mesto,
               @RequestParam("psc") String psc

       )  {


         if(akce.equals("Delete"))
         {
             System.out.println("Delete");
             seznamUcty.deleteById(ucetID);
         }
       else if(akce.equals("Create"))
        {
            System.out.println("Create");
            Ucet ucet= new Ucet();
            ucet.setAdresa(new Adresa());
            ucet.setJmeno(jmeno);
            ucet.setPrijmeni(prijmeni);
            ucet.setHeslo(heslo);
            ucet.setUcet_login(login);
            ucet.setTypUctu(seznamyTypyUcty.findById(3));
            ucet.getAdresa().setCps(cp);
            ucet.getAdresa().setUlice(ulice);
            ucet.getAdresa().setMesto(mesto);
            ucet.getAdresa().setPsc(psc);
            ucet.getAdresa().setUcet(ucet);
           seznamUcty.save(ucet);
        }
        else  if (akce.equals("Update"))
         {

             System.out.println("update");
             System.out.println(jmeno+" "+prijmeni+" "+heslo+" "+ login+" "+ulice+" "+cp+" "+ mesto+" "+psc);

         Ucet ucet= seznamUcty.findById((ucetID));
         ucet.setJmeno(jmeno);
         ucet.setPrijmeni(prijmeni);
         ucet.setHeslo(heslo);
         ucet.setUcet_login(login);
         ucet.getAdresa().setCps(cp);
         ucet.getAdresa().setUlice(ulice);
         ucet.getAdresa().setMesto(mesto);
         ucet.getAdresa().setPsc(psc);

     //    seznamUcty.save(ucet);
         }

/*
        ModelAndView mav = new ModelAndView("blog-update");
        Ucet ucet =seznamUcty.findById(ucetID);

ucet.setJmeno("");
ucet.setPrijmeni("");
ucet.setUcet_login("");
ucet.setHeslo("test");
;*/


        return "redirect:/Sprava_Ucty";

    }
    @RequestMapping(value = "/registrace", method = RequestMethod.POST)
    public String Registrace(@Valid @ModelAttribute("ucet") Ucet ucet,
                             BindingResult result, ModelMap model) {
        SeznamUcet ucty = new SeznamUcet();
        ucty.getSeznamUctu().addAll(seznamUcty.findAll());

        model.put("ListUcet", ucty.getSeznamUctu());


        return "Sprava_Ucty";
    }
}
