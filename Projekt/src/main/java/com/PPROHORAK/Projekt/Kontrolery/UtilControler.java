package com.PPROHORAK.Projekt.Kontrolery;

import com.PPROHORAK.Projekt.DAO.PlatformyDao;
import com.PPROHORAK.Projekt.DAO.ProduktyDao;
import com.PPROHORAK.Projekt.DAO.UctyDao;
import com.PPROHORAK.Projekt.Model.*;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamPlatforem;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamPolozek;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamPolozekKosik;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;
@Controller
@Data
public class UtilControler {



    public  void GetPlatformList(Map<String, Object> model,PlatformyDao seznamPlatformy){

        SeznamPlatforem platformy = new SeznamPlatforem();
        platformy.getSeznamPlatformy().addAll(seznamPlatformy.findAll());

        model.put("ListPlatformy", platformy.getSeznamPlatformy());





    }
public SeznamPolozekKosik getPole(ProduktyDao seznamProduktu, String kos) {
    SeznamPolozekKosik pole = new SeznamPolozekKosik();

    String[] stary = kos.split("-");
    for (String s : stary) {
        if (!s.equals("")) {
            boolean nalezeno = false;
            for (PolozkaKosik pol : pole.getSeznamPolozekKosik()) {
                if (pol.getProdukt().getProdukt_ID().toString().equals(s)) {
                    pol.setPocet(pol.getPocet() + 1);
                    nalezeno = true;
                    break;
                }

            }
            if (nalezeno == false) {
                Produkt produkt = seznamProduktu.findById(Integer.parseInt(s));
                PolozkaKosik p = new PolozkaKosik();
                p.setProdukt(produkt);
                p.setPocet(1);
                pole.getSeznamPolozekKosik().add(p);
            }


        }



    }
    return pole;
}

public void zapisSusenkyKosik(HttpServletResponse response, SeznamPolozekKosik pole)
{String novy="";
    for (PolozkaKosik p :pole.getSeznamPolozekKosik())
    {

        for (int x=1;x<=p.getPocet();x++) {

            novy+="-"+p.getProdukt().getProdukt_ID().toString();
        }}

    Cookie kosik = new Cookie("kosik", novy); //bake cookie
  //  kosik.setPath("/");

    kosik.setMaxAge(60 * 60 * 24 * 3); //set expire time in sec
    response.addCookie(kosik);
}
    public void GetPocetPolozekVkosiku(Map<String,Object> model, ProduktyDao seznamProduktu,UctyDao seznamUctu,String cookies)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
              if(authentication.getName().equals("anonymousUser"))
        {model.put("typuctu","Anonym");
            ArrayList<Polozka> pole=new ArrayList<Polozka>();
            String[] stary=cookies.split("-");
            for (String s:stary)
            {
                if (!s.equals(""))
                {
                    boolean nalezeno=false;
                    for (Polozka pol:pole) {
                        if (pol.getProdukt().getProdukt_ID().toString().equals(s))
                        {pol.setPocet(pol.getPocet()+1);
                            nalezeno=true;
                            break;
                        }

                    }
                    if(nalezeno==false)
                    {
                        Produkt produkt= seznamProduktu.findById(Integer.parseInt(s));
                        Polozka p =new Polozka();
                        p.setProdukt(produkt);
                        p.setPocet(1);
                        p.setSleva(produkt.getSleva());
                        p.setCena(Math.round(produkt.getAktualniCena()));
                        pole.add(p);
                    }



                }


            }
            int celkemPolozek=0;
            int cena =0;
            for (Polozka p:pole
                 ) {cena+=p.getProdukt().getAktualniCena()*p.getPocet();
                celkemPolozek+=p.getPocet();
            }
            model.put("pocetpolozekvkosiku"," Máte typu produktu v košíku "+pole.size());
            model.put("pocetpolozek","Máte celkem položek v košíku v počtu kopii "+celkemPolozek);
            model.put("hodnote","v celkové hodnotě "+cena);
        }
        else
        {
         //   for (authentication.getPrincipal())
         //   authentication.getAuthorities()
            Ucet ucet=seznamUctu.findByLogin(authentication.getName());
            model.put("typuctu",ucet.getTypUctu().getNazev());
            System.out.println(authentication.getAuthorities());
            int x =0;
            int cena =0;

            for (PolozkaKosik p:ucet.getKosikPolozky()
                 ) {
                cena+=p.getProdukt().getAktualniCena()*p.getPocet();
                x+=p.getPocet();
            }
            model.put("pocetpolozekvkosiku"," Máte typu produktu v košíku "+ucet.getKosikPolozky().size());
            model.put("pocetpolozek","Máte celkem položek v košíku v počtu kopii "+x);
            model.put("hodnote","v celkové hodnotě "+cena);
        }


    }


}
