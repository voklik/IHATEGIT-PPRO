package com.PPROHORAK.Projekt.Kontrolery;

import com.PPROHORAK.Projekt.DAO.*;
import com.PPROHORAK.Projekt.Model.Adresa;
import com.PPROHORAK.Projekt.Model.Objednavka;
import com.PPROHORAK.Projekt.Model.Produkt;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamObjednavek;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamProduktu;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamTypyUctu;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamUcet;
import com.PPROHORAK.Projekt.Model.Ucet;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Controller
public @Data
 class UcetControler {

    private final UctyDao seznamUcty;
    private final TypyUctuDao seznamyTypyUcty;
    private final AdresyDao seznamyAdres;
    private final StavyDao seznamStavu;
    private final ObjednavkyDao seznamObjednavek;
    private final PolozkyDao    seznamPolozek;
    @Autowired
    private PasswordEncoder passwordEncoder;



    //strankovani
    @GetMapping(value = {"/admin/Sprava_Ucty","/admin/sprava_ucty","/admin/"})
    public String ShowAllUcty(Pageable pageable,
                           @RequestParam("page") Optional<Integer> page, Map<String, Object> model) {
        final int currentPage = page.orElse(0);
        final int size = 3;
        Pageable customPageable = PageRequest.of(currentPage, size);
        Page<Produkt> stranka = this.seznamUcty.findAllPagesUcty(customPageable);


        new UtilControler().GetTypUcet(model,seznamUcty);
        model.put("UcetStranka", stranka);

        return "Spravy/Sprava_Ucty";
    }




    @PostMapping(value = {"/admin/updateheslo"})
    public String updateUcetDetailHeslo( @RequestParam("id") Integer ucetID,
                                    @RequestParam("heslo") String heslo,Map<String, Object> model

    )  {



           Ucet ucet= seznamUcty.findById((ucetID));
        ucet.setHeslo(passwordEncoder.encode(heslo));
seznamUcty.save(ucet);
        return DetailUctuAdmin(model,ucetID);

    }
    @PostMapping(value = {"/admin/updatetyp"})
    public String updateUcetDetailHeslo( @RequestParam("id") Integer ucetID,
                                         @RequestParam("typ") Integer typ,Map<String, Object> model

    )  {



        Ucet ucet= seznamUcty.findById((ucetID));
         ucet.setTypUctu(seznamyTypyUcty.findById(typ));
        seznamUcty.save(ucet);
        return DetailUctuAdmin(model,ucetID);

    }
    @PostMapping(value = {"/admin/updatedetailucet"})
    public String updateUcetDetail( @RequestParam("id") Integer ucetID,
                                   @RequestParam("jmeno") String jmeno,
                                    @RequestParam("prijmeni") String prijmeni, @RequestParam("login") String login,
                                  @RequestParam("ulice") String ulice,
                                    @RequestParam("cp") String cp,@RequestParam("mesto") String mesto,
                                    @RequestParam("psc") String psc,Map<String, Object> model

    )  {



            System.out.println("update");
            System.out.println(jmeno+" "+prijmeni+" "+" "+ login+" "+ulice+" "+cp+" "+ mesto+" "+psc);

            Ucet ucet= seznamUcty.findById((ucetID));
            ucet.setJmeno(jmeno);
            ucet.setPrijmeni(prijmeni);

            ucet.setUcet_login(login);
            ucet.getAdresa().setCps(cp);
            ucet.getAdresa().setUlice(ulice);
            ucet.getAdresa().setMesto(mesto);
            ucet.getAdresa().setPsc(psc);



seznamUcty.save(ucet);
      return DetailUctuAdmin(model,ucetID);

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
            ucet.setHeslo(passwordEncoder.encode(heslo));
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
         ucet.setHeslo(passwordEncoder.encode(heslo));
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
        if (error != null) {
            model.put("hlaska", "Your username and password is invalid.");
        }
        if (logout != null) {
            model.put("hlaska", "You have been logged out successfully.");
        }

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

        novy.setHeslo(passwordEncoder.encode(heslo));
        novy.setPrijmeni(prijmeni);
        novy.setJmeno(jmeno);
        novy.setUcet_login(login);
        novy.setEmail(email);
        novy.setTypUctu(seznamyTypyUcty.findById(3));
        novy.setAktivni(true);
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
            seznamUcty.save(novy);
            return "Ucet/Prihlaseni";
        }



    }

    @RequestMapping(value = "/ucet/detail")
    public String DetailUctu( Map<String, Object> model,@ModelAttribute("hlaska") String hlaska) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                Ucet ucet = seznamUcty.findByLogin(authentication.getName());

        model.put("login",ucet.getUcet_login());
        model.put("jmeno",ucet.getJmeno());
        model.put("prijmeni",ucet.getPrijmeni());
        model.put("email",ucet.getEmail());
        model.put("ulice",ucet.getAdresa().getUlice());
        model.put("cp",ucet.getAdresa().getCps());
        model.put("mesto",ucet.getAdresa().getMesto());
        model.put("psc",ucet.getAdresa().getPsc());
        model.put("hlaska",hlaska);
return"/Ucet/DetailUctu";

    }

    @RequestMapping(value = "/admin/ucetdetail")
    public String DetailUctuAdmin( Map<String, Object> model,@RequestParam("id") Integer id) {

         Ucet ucet = seznamUcty.findById(id);
         new UtilControler();
        new UtilControler().GetTypUcet(model,seznamUcty);
        model.put("login",ucet.getUcet_login());
        model.put("jmeno",ucet.getJmeno());
        model.put("prijmeni",ucet.getPrijmeni());
        model.put("email",ucet.getEmail());
        model.put("ulice",ucet.getAdresa().getUlice());
        model.put("heslo",ucet.getHeslo());
        model.put("cp",ucet.getAdresa().getCps());
        model.put("mesto",ucet.getAdresa().getMesto());
        model.put("psc",ucet.getAdresa().getPsc());
        model.put("typuctu1",ucet.getTypUctu().getTypUctu_ID());
model.put("id",id);

        SeznamTypyUctu seznamTypyUctu=new SeznamTypyUctu();
        seznamTypyUctu.getSeznamTypyUctu().addAll(seznamyTypyUcty.findAll());
        model.put("typy",seznamTypyUctu.getSeznamTypyUctu());
        return"/Spravy/DetailUcet";

    }


    @PostMapping(value = {"/admin/hledaniuctu"})
    public String HledaniAdmin(@RequestParam(value = "nalezeno", required = false) int hledany,
                               Map<String, Object> model) {

        Ucet ucet = seznamUcty.findById(hledany);


        if (ucet == null) {
            model.put("hlaska", "Žádný účet s takovým názvem nebyl nalezen");
            return "/Spravy/Sprava_Ucty";
        } else {

            SeznamUcet seznam=new SeznamUcet();
            seznam.getSeznamUctu().add(ucet);
           return DetailUctuAdmin(model, ucet.getUcet_ID());
        }


    }

    @RequestMapping(value = "/historie")
    public String HistorieUctu( Map<String, Object> model,@ModelAttribute("hlaska") String hlaska) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Ucet ucet = seznamUcty.findByLogin(authentication.getName());

        SeznamObjednavek seznam =new SeznamObjednavek();
        seznam.getSeznamObjednavek().addAll( seznamObjednavek.findByUcet(ucet.getUcet_ID()));
        model.put("seznam",seznam.getSeznamObjednavek());
        return"/Ucet/HistorieUctu";

    }


    @PostMapping(value = "/ucet/detailUpdate")
    public String DetailUctu( Map<String, Object> model
            ,@RequestParam("jmeno") String jmeno,@RequestParam("prijmeni") String prijmeni,
                              @RequestParam("login") String login,
                              @RequestParam("email") String email,@RequestParam("ulice") String ulice,
                              @RequestParam("mesto") String mesto,@RequestParam("cp") String cp,
                              @RequestParam("psc") String psc , RedirectAttributes redirectAttributes ) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Ucet ucet = seznamUcty.findByLogin(authentication.getName());

        ucet.setJmeno(jmeno);
        ucet.setPrijmeni(prijmeni);
        ucet.setEmail(email);
        ucet.getAdresa().setUlice(ulice);
        ucet.getAdresa().setCps(cp);
        ucet.getAdresa().setMesto(mesto);
        ucet.getAdresa().setPsc(psc);
        seznamUcty.save(ucet);
        redirectAttributes.addAttribute("hlaska","Údaje byly změněny");
      return "redirect:/ucet/detail";

    }

    @PostMapping(value = "/ucet/hesloUpdate")
    public String HesloUpdate(Map<String, Object> model
            , @RequestParam("heslo") String heslo, @RequestParam("nheslo")String nheslo
    , RedirectAttributes redirectAttributes) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Ucet ucet = seznamUcty.findByLogin(authentication.getName());
        if (ucet.getHeslo().equals(passwordEncoder.encode(heslo)))
        {
            ucet.setHeslo(passwordEncoder.encode(nheslo));
            seznamUcty.save(ucet);
            redirectAttributes.addAttribute("hlaska","Heslo bylo změněno");
        }
        else
        {
            redirectAttributes.addAttribute("hlaska","Staré heslo nesouhlasí");
        }

        return "redirect:/ucet/detail";

    }


}
