package com.PPROHORAK.Projekt.Kontrolery;

import com.PPROHORAK.Projekt.DAO.PlatformyDao;
import com.PPROHORAK.Projekt.DAO.ProduktyDao;
import com.PPROHORAK.Projekt.DAO.UctyDao;
import com.PPROHORAK.Projekt.Model.Platforma;
import com.PPROHORAK.Projekt.Model.Polozka;
import com.PPROHORAK.Projekt.Model.Produkt;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamPlatforem;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    public void GetPocetPolozekVkosiku(Map<String,Object> model, ProduktyDao seznamProduktu,String cookies)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());

        if(authentication.getName().equals("anonymousUser"))
        {
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
            for (Polozka p:pole
                 ) {
                celkemPolozek+=p.getPocet();
            }
            model.put("pocetpolozekvkosiku",pole.size());
            model.put("pocetpolozek",celkemPolozek);
        }
        else
        {
            //sáhni do databáze a zjisti, co má uživatel v košíku
            System.out.println(authentication.getAuthorities());
            model.put("pocetpolozekvkosiku",0);
            model.put("pocetpolozek",0);
        }


    }


}
