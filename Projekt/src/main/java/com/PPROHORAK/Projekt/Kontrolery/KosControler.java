package com.PPROHORAK.Projekt.Kontrolery;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.PPROHORAK.Projekt.DAO.*;
import com.PPROHORAK.Projekt.Model.*;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamPolozek;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamPolozekKosik;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@Data
public class KosControler {
    private final UctyDao seznamUcty;
    private final PlatformyDao seznamPlatformy;
    private final PolozkyKosikDao seznamPolozekKosik;
    private final ProduktyDao seznamProduktu;
    private final StavyDao seznamStavu;
    private final ObjednavkyDao seznamObjednavek;
    private final PolozkyDao    seznamPolozek;
    @RequestMapping(value = {"/kos"})
    public String show(@CookieValue(value = "kosik", defaultValue = "") String kos,
                       HttpServletResponse response, Map<String, Object> model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new UtilControler().GetPocetPolozekVkosiku(model,seznamProduktu,seznamUcty,kos);
        SeznamPolozekKosik pole=new SeznamPolozekKosik();
        if(authentication.getName().equals("anonymousUser"))
        {
        pole=new UtilControler().getPole(seznamProduktu,kos);
        }
        else
        {
            pole.getSeznamPolozekKosik().addAll(seznamUcty.findByLogin(authentication.getName()).getKosikPolozky());
        }


model.put("SeznamPolozek",pole.getSeznamPolozekKosik());

        return "/Prodej/Kos";
    }
    @PostMapping(value = {"/kosikpotvrzeni"})
    public String potvrzeni(@CookieValue(value = "kosik", defaultValue = "") String kos,
                       HttpServletResponse response, Map<String, Object> model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new UtilControler().GetPocetPolozekVkosiku(model,seznamProduktu,seznamUcty,kos);
        SeznamPolozekKosik pole=new SeznamPolozekKosik();
        if(authentication.getName().equals("anonymousUser"))
        {
            pole=new UtilControler().getPole(seznamProduktu,kos);
        }
        else
        {
            pole.getSeznamPolozekKosik().addAll(seznamUcty.findByLogin(authentication.getName()).getKosikPolozky());
        }


        model.put("SeznamPolozek",pole.getSeznamPolozekKosik());

        return "/Prodej/KosikPotvrzeni";
    }
    @PostMapping(value = {"/vytvoreniobjednavky"})
    public String createOrder(@CookieValue(value = "kosik", defaultValue = "") String kos,
                       HttpServletResponse response, Map<String, Object> model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new UtilControler().GetPocetPolozekVkosiku(model,seznamProduktu,seznamUcty,kos);


        String jmeno="";
        String prijmeni="";
        String email="";
        String ulice="";
        String cp="";
        String mesto="";
        String psc="";
        SeznamPolozekKosik pole=new SeznamPolozekKosik();
        if(authentication.getName().equals("anonymousUser"))
        {
            pole=new UtilControler().getPole(seznamProduktu,kos);
        }
        else
        {
            Ucet ucet = seznamUcty.findByLogin(authentication.getName());
            jmeno=ucet.getJmeno();
            prijmeni=ucet.getPrijmeni();
            email=ucet.getEmail();
            ulice=ucet.getAdresa().getUlice();
            cp=ucet.getAdresa().getCps();
            mesto=ucet.getAdresa().getMesto();
            psc=ucet.getAdresa().getPsc();
            pole.getSeznamPolozekKosik().addAll((ucet.getKosikPolozky()));
        }


        model.put("SeznamPolozek",pole.getSeznamPolozekKosik());
        model.put("jmeno",jmeno);
        model.put("prijmeni",prijmeni);
        model.put("email",email);
        model.put("ulice",ulice);
        model.put("cp",cp);
        model.put("mesto",mesto);
        model.put("psc",psc);
        return "/Prodej/VytvoreniObjednavky";
    }
    @PostMapping(value = { "/dokonceniobjednavky"})
    public String vytvoreniObjednavky(  HttpServletResponse response, Map<String, Object> model
            ,@RequestParam("jmeno") String jmeno,@RequestParam("prijmeni") String prijmeni,
             @RequestParam("email") String email,@RequestParam("ulice") String ulice,
             @RequestParam("mesto") String mesto,@RequestParam("cp") String cp,
             @RequestParam("psc") String psc
         //   , @RequestParam("pole") List<PolozkaKosik> pole
            , @CookieValue(value = "kosik", defaultValue = "") String kos
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        SeznamPolozekKosik pole=new SeznamPolozekKosik();
        if(authentication.getName().equals("anonymousUser"))
        {
            pole=new UtilControler().getPole(seznamProduktu,kos);
        }
        else
        {
            Ucet ucet = seznamUcty.findByLogin(authentication.getName());
            jmeno=ucet.getJmeno();
            prijmeni=ucet.getPrijmeni();
            email=ucet.getEmail();
            ulice=ucet.getAdresa().getUlice();
            cp=ucet.getAdresa().getCps();
            mesto=ucet.getAdresa().getMesto();
            psc=ucet.getAdresa().getPsc();
            pole.getSeznamPolozekKosik().addAll((ucet.getKosikPolozky()));
        }
        SeznamPolozek seznam = new SeznamPolozek();
        Objednavka objednavka=new Objednavka();
        int cena=0;
        for (PolozkaKosik p :pole.getSeznamPolozekKosik())
        {
            Polozka nova = new Polozka();
            nova.setProdukt(p.getProdukt());
            nova.setPocet(p.getPocet());
            nova.setCena(Math.round(p.getProdukt().getAktualniCena()*p.getPocet()));
            nova.setAktivni(true);
            nova.setObjednavka(objednavka);
            seznam.getSeznamPolozek().add(nova);
            cena+=nova.getCena();
        }
        Adresa adresa=new Adresa();
        adresa.setPsc(psc);
        adresa.setMesto(mesto);
        adresa.setCps(cp);
        adresa.setUlice(ulice);
        adresa.setAktivni(true);
        adresa.setObjednavka(objednavka);
        objednavka.setAdresa(adresa);
        objednavka.setDatumObjednani( new Date());
        objednavka.setPolozky(seznam.getSeznamPolozek());
        objednavka.setStav(seznamStavu.findById(1));
objednavka.setCena(cena+100);
        if(!authentication.getName().equals("anonymousUser"))
        {
            Ucet ucet=seznamUcty.findByLogin(authentication.getName());
            objednavka.setUcet(ucet);

        }
        else
        {
            Ucet ucet=seznamUcty.findById(-3);
            objednavka.setUcet(ucet);

        }
        try{
            seznamObjednavek.save(objednavka);

            model.put("hlaska","Úspěšně jste objednali vše. Brzy Vám příjde email s potvrzením a s číslem objednávky");
            if(!authentication.getName().equals("anonymousUser"))
            {
                Ucet ucet=seznamUcty.findByLogin(authentication.getName());
                ucet.getKosikPolozky().clear();
                seznamUcty.save(ucet);
            }
            else
            {
                new UtilControler().zapisSusenkyKosik(response,new SeznamPolozekKosik());
            }

        }
        catch (Exception e)
        {
            model.put("hlaska","něco se pokazilo");
        }

        return "/Prodej/VysledekObjednavky";
    }

    @PostMapping("/kosikadd")
    public String add(@RequestParam("id") Integer id,@RequestParam("pocet") Integer pocet,
                      @CookieValue(value = "kosik", defaultValue = "") String kos,
                      HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SeznamPolozekKosik pole =new SeznamPolozekKosik();
        if(authentication.getName().equals("anonymousUser"))
        {
          pole= new UtilControler().getPole(seznamProduktu,kos);



            if (pole.getSeznamPolozekKosik().size()==0)
            {
                Produkt produkt = seznamProduktu.findById(id);
                PolozkaKosik p = new PolozkaKosik();
                p.setProdukt(produkt);
                p.setPocet(pocet);
                pole.getSeznamPolozekKosik().add(p);
            }
            else {
                boolean nalezeno=false;
            for (PolozkaKosik p :pole.getSeznamPolozekKosik()) {
            if (p.getProdukt().getProdukt_ID()==id) {

                nalezeno=true;
                p.setPocet(pocet);
break;

            }

            }
            if(nalezeno==false) {
                Produkt produkt = seznamProduktu.findById(id);
                PolozkaKosik p = new PolozkaKosik();
                p.setProdukt(produkt);
                p.setPocet(pocet);
                pole.getSeznamPolozekKosik().add(p);
            }

            }


            new UtilControler().zapisSusenkyKosik(response,pole);




        }
        else
        {
            Ucet ucet=seznamUcty.findByLogin(authentication.getName());
            boolean nalezeno = false;
            for (PolozkaKosik p :ucet.getKosikPolozky())
            {
                if(p.getProdukt().getProdukt_ID()==id)
                {
                    p.setPocet(pocet);
                    nalezeno=true;
                     seznamPolozekKosik.save(p);
                    break;
                }
            }

            if(nalezeno==false)
            {
                Produkt produkt= seznamProduktu.findById(id);
                PolozkaKosik p =new PolozkaKosik();
                p.setProdukt(produkt);
                p.setPocet(pocet);
                p.setUcet(ucet);

                ucet.getKosikPolozky().add(p);
                seznamPolozekKosik.save(p);
            }

        }
        return "redirect:/kos";
    }



    @GetMapping("/kosikcookies")
    public String add(
    @CookieValue(value = "kosik", defaultValue = "") String kos,
                      HttpServletResponse response, Map<String, Object> model) {

        new UtilControler().GetPocetPolozekVkosiku(model,seznamProduktu,seznamUcty,kos);
model.put("kos",kos);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.put("hlaska",authentication.getAuthorities());

        return "cook";
    }


    @PostMapping("/kosikremoveat")
    public String removeOne(@RequestParam("id") Integer id,
                            @CookieValue(value = "kosik", defaultValue = "") String kos,
                            HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SeznamPolozekKosik pole =new SeznamPolozekKosik();
        if(authentication.getName().equals("anonymousUser"))
        {
            pole= new UtilControler().getPole(seznamProduktu,kos);




                for (PolozkaKosik p :pole.getSeznamPolozekKosik()) {
                    if (p.getProdukt().getProdukt_ID()==id) {

                  pole.getSeznamPolozekKosik().remove(p);
                break;
                    }

                }



            new UtilControler().zapisSusenkyKosik(response,pole);
        }
        else
        {
            seznamPolozekKosik.deleteByUserOne(id);
        }
        return "redirect:/kos";
    }

    @PostMapping("/kosikremoveall")
    public String removeAll(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")) {

            Cookie kosik = new Cookie("kosik", ""); //bake cookie
            kosik.setMaxAge(60 * 60 * 24 * 3); //set expire time in sec
         //  kosik.setPath("/");

            response.addCookie(kosik);
        }
        else
        {
        seznamPolozekKosik.deleteByUserAll(seznamUcty.findByLogin(authentication.getName()).getUcet_ID());
        }

        return "redirect:/kos";
    }
}
