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


    @RequestMapping(value = {"/admin/Sprava_Ucty","/admin/sprava_ucty","/admin/"})
    public String showAll(Map<String, Object> model){
        SeznamUcet ucty = new SeznamUcet();
        ucty.getSeznamUctu().addAll(seznamUcty.findAll());

        model.put("ListUcet", ucty.getSeznamUctu());


            return "Spravy/Sprava_Ucty";



    }




    @PostMapping(value = {"/admin/UpdateUcetSprava","/admin/updateucetsprav"})
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


        return "redirect:/admin/Sprava_Ucty";

    }
    @GetMapping(value = {"/Login","/login"})
    public String login(Map<String, Object> model, String error, String logout) {
        if (error != null)
            model.put("hlaska", "Your username and password is invalid.");

        if (logout != null)
            model.put("hlaska", "You have been logged out successfully.");

        return "Ucet/Prihlaseni";
    }
  /*  @RequestMapping(value = {"/Login","/login"})
    public String Login(  Map<String, Object> model) {



        return "Ucet/Prihlaseni";
    }*/
    @PostMapping(value = {"/LoginAkce","/loginakce"})
    public String LoginAkce( Map<String, Object> model,
            @RequestParam("login") String login,@RequestParam("heslo") String heslo
    ) {



        Ucet test= seznamUcty.findByLogin(login);
        if(test!=null)
        {
            if(test.getHeslo().equals(heslo))
            {
                model.put("hlaska","Úspěšně jste se přihlásili");


                return "Ucet/Prihlaseni";
            }
            else
            {
                model.put("hlaska","Heslo NESOUHLASÍ");
                    return "Ucet/Prihlaseni";
            }

        }
        else
        {

            model.put("hlaska","TAKOVÝ LOGIN  SE V ESHOPU NEVYSKYTUJE");
            return "Ucet/Prihlaseni";
        }



    }
    @RequestMapping(value = {"/Registrace","/registrace"})
    public String Registrace( Map<String, Object> model) {



        return "Ucet/Registrace";
    }
    @PostMapping(value = { "/RegistraceAkce","/registraceakce"})
    public String RegistraceAkce( Map<String, Object> model
                            ,@RequestParam("jmeno") String jmeno,@RequestParam("prijmeni") String prijmeni,
                             @RequestParam("login") String login,@RequestParam("heslo") String heslo,
                             @RequestParam("email") String email,@RequestParam("ulice") String ulice,
                             @RequestParam("mesto") String mesto,@RequestParam("cp") String cp,
                             @RequestParam("psc") String psc  ) {

        Ucet novy = new Ucet();
        Adresa nova = new Adresa();
        nova.setUcet(novy);
        novy.setAdresa(nova);
        nova.setMesto(mesto);
        nova.setPsc(psc);
        nova.setCps(cp);
        nova.setUlice(ulice);

        novy.setHeslo(heslo);
        novy.setPrijmeni(prijmeni);
        novy.setJmeno(jmeno);
        novy.setUcet_login(login);
        novy.setEmail(email);

        Ucet test= seznamUcty.findByLogin(login);
        if(test!=null)
        {
            model.put("hlaska","TAKOVÝ LOGIN UŽ SE V ESHOPU VYSKYTUJE");

            model.put("login",login);
            model.put("heslo",heslo);
            model.put("jmeno",jmeno);
            model.put("prijmeni",prijmeni);
            model.put("email",email);

            model.put("ulice",ulice);
            model.put("cp",cp);
            model.put("mesto",mesto);
            model.put("psc",psc);

            return "Ucet/Registrace";
        }
        else
        {
            model.put("hlaska","Úspěšně jste se zaregistrovali a nyní se můžete přihlásit");
           // seznamUcty.save(novy);
            return "Ucet/Prihlaseni";
        }



    }

}
