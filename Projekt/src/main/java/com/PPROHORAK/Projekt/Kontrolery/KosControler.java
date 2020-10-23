package com.PPROHORAK.Projekt.Kontrolery;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.PPROHORAK.Projekt.DAO.*;
import com.PPROHORAK.Projekt.Model.Polozka;
import com.PPROHORAK.Projekt.Model.PolozkaKosik;
import com.PPROHORAK.Projekt.Model.Produkt;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamPolozekKosik;
import com.PPROHORAK.Projekt.Model.Ucet;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
@Controller
@Data
public class KosControler {
    private final UctyDao seznamUcty;
    private final PlatformyDao seznamPlatformy;
    private final PolozkyKosikDao seznamPolozekKosik;
    private final ProduktyDao seznamProduktu;

    @RequestMapping(value = {"/kos"})
    public String show(@CookieValue(value = "kosik", defaultValue = "") String kos,
                       HttpServletResponse response, Map<String, Object> model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new UtilControler().GetPocetPolozekVkosiku(model,seznamProduktu,seznamUcty,kos);
        SeznamPolozekKosik pole=new SeznamPolozekKosik();
        if(authentication.getName().equals("anonymousUser"))
        {
        pole=new UtilControler().getPole(seznamProduktu,kos);


        /*
        String novy="";
        for (String s:stary)
        {
            novy+="-"+s;
        }
            Cookie kosik = new Cookie("kos", novy); //bake cookie
            kosik.setMaxAge(60*60*24*3); //set expire time in sec
            response.addCookie(kosik);*/
        }
        else
        {
            pole.getSeznamPolozekKosik().addAll(seznamUcty.findByLogin(authentication.getName()).getKosikPolozky());
        }


model.put("SeznamPolozek",pole.getSeznamPolozekKosik());

        return "/Prodej/Kos";
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
