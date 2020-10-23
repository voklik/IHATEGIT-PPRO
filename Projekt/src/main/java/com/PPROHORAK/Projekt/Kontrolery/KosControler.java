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

    @RequestMapping("/kosik")
    public String show(@CookieValue(value = "kosik", defaultValue = "") String kos,
                       HttpServletResponse response, Map<String, Object> model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        SeznamPolozekKosik pole=new SeznamPolozekKosik();
        if(authentication.getName().equals("anonymousUser"))
        {
        String[] stary=kos.split("-");
        for (String s:stary)
        {
           if (!s.equals(""))
           {
               boolean nalezeno=false;
               for (PolozkaKosik pol:pole.getSeznamPolozekKosik()) {
                   if (pol.getProdukt().getProdukt_ID().toString().equals(s))
                   {pol.setPocet(pol.getPocet()+1);
                       nalezeno=true;
                       break;
                   }

               }
               if(nalezeno==false)
               {
                   Produkt produkt= seznamProduktu.findById(Integer.parseInt(s));
                   PolozkaKosik p =new PolozkaKosik();
                   p.setProdukt(produkt);
                   p.setPocet(1);
                   pole.getSeznamPolozekKosik().add(p);
               }



           }


        }
        String novy="";
        for (String s:stary)
        {
            novy+="-"+s;
        }}
        else
        {
            pole.getSeznamPolozekKosik().addAll(seznamUcty.findByLogin(authentication.getName()).getKosikPolozky());
        }


model.put("SeznamPolozek",pole.getSeznamPolozekKosik());
     //   Cookie kosik = new Cookie("kos", ""); //bake cookie
    //    kosik.setMaxAge(60*60*24*3); //set expire time in sec
     //   response.addCookie(kosik);
        return "/Prodej/Kos";
    }
    @PostMapping("/kosik/add")
    public String add(@RequestParam("id") Integer id,@RequestParam("pocet") Integer pocet,
                      @CookieValue(value = "kosik", defaultValue = "") String kos,
                      HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SeznamPolozekKosik pole =new SeznamPolozekKosik();
        if(authentication.getName().equals("anonymousUser"))
        {
            String[] stary=kos.split("-");
            for (String s:stary)
            {
                if (!s.equals(""))
                {
                    boolean nalezeno=false;
                    for (PolozkaKosik pol:pole.getSeznamPolozekKosik()) {
                        if (pol.getProdukt().getProdukt_ID().toString().equals(s))
                        {pol.setPocet(pol.getPocet()+1);
                            nalezeno=true;
                            break;
                        }

                    }
                    if(nalezeno==false)
                    {
                        Produkt produkt= seznamProduktu.findById(Integer.parseInt(s));
                        PolozkaKosik p =new PolozkaKosik();
                        p.setProdukt(produkt);
                        p.setPocet(1);
                        pole.getSeznamPolozekKosik().add(p);
                    }



                }


            }

            String novy="";
            for (PolozkaKosik p :pole.getSeznamPolozekKosik())
            {
                if(p.getProdukt().getProdukt_ID()==id)
                {
                    p.setPocet(pocet);
                }
                for (int x=1;x<=p.getPocet();x++) {

                novy+="-"+p.getProdukt().getProdukt_ID().toString();
            }}


            Cookie kosik = new Cookie("kosik", novy); //bake cookie
            kosik.setMaxAge(60 * 60 * 24 * 3); //set expire time in sec
            response.addCookie(kosik);
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
        return "redirect:/kosik";
    }



    @GetMapping("/kosik/cookies")
    public String add(
    @CookieValue(value = "kosik", defaultValue = "") String kos,
                      HttpServletResponse response, Map<String, Object> model) {
        new UtilControler().GetPocetPolozekVkosiku(model,seznamProduktu,kos);
model.put("kos",kos);
        return "cook";
    }


    @PostMapping("/kosik/removeat")
    public String removeOne(@RequestParam("id") Integer id,
                            @CookieValue(value = "kosik", defaultValue = "") String kos,
                            HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SeznamPolozekKosik pole =new SeznamPolozekKosik();
        if(authentication.getName().equals("anonymousUser"))
        {
            String[] stary=kos.split("-");
            for (String s:stary)
            {
                if (!s.equals(""))
                {
                    boolean nalezeno=false;
                    for (PolozkaKosik pol:pole.getSeznamPolozekKosik()) {
                        if (pol.getProdukt().getProdukt_ID().toString().equals(s))
                        {pol.setPocet(pol.getPocet()+1);
                            nalezeno=true;
                            break;
                        }

                    }
                    if(nalezeno==false)
                    {
                        Produkt produkt= seznamProduktu.findById(Integer.parseInt(s));
                        PolozkaKosik p =new PolozkaKosik();
                        p.setProdukt(produkt);
                        p.setPocet(1);
                        pole.getSeznamPolozekKosik().add(p);
                    }



                }


            }

            String novy="";
            for (PolozkaKosik p :pole.getSeznamPolozekKosik())
            {
                if(p.getProdukt().getProdukt_ID()!=id)
            for (int x=1;x<=p.getPocet();x++) {
                    novy+="-"+p.getProdukt().getProdukt_ID().toString();
                }}


            Cookie kosik = new Cookie("kosik", novy); //bake cookie
            kosik.setMaxAge(60 * 60 * 24 * 3); //set expire time in sec
            response.addCookie(kosik);
        }
        else
        {
            seznamPolozekKosik.deleteByUserOne(id);
        }
        return "redirect:/kosik";
    }

    @PostMapping("/kosik/removeall")
    public String removeAll(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")) {
            Cookie kosik = new Cookie("kosik", ""); //bake cookie
            kosik.setMaxAge(60 * 60 * 24 * 3); //set expire time in sec
            response.addCookie(kosik);
        }
        else
        {
        seznamPolozekKosik.deleteByUserAll(seznamUcty.findByLogin(authentication.getName()).getUcet_ID());
        }

        return "redirect:/kosik";
    }
}
