package com.PPROHORAK.Projekt.Kontrolery;


import com.PPROHORAK.Projekt.DAO.ObjednavkyDao;
import com.PPROHORAK.Projekt.DAO.StavyDao;
import com.PPROHORAK.Projekt.DAO.UctyDao;
import com.PPROHORAK.Projekt.Model.Objednavka;
import com.PPROHORAK.Projekt.Model.Produkt;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamObjednavek;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamProduktu;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamStavu;
import com.PPROHORAK.Projekt.Model.Stav;
import com.PPROHORAK.Projekt.Model.Ucet;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import java.security.spec.ECParameterSpec;
import java.util.Map;
import java.util.Optional;

@Controller
@Data
public class ObjednavkaControler {
    private final StavyDao seznamStavu;
    private final ObjednavkyDao seznamObjednavek;
    private final UctyDao seznamUcty;


    @PostMapping(value = "/detailobjednavky")
    public String detailObjednavky(@RequestParam("id") Integer id, Map<String, Object> model, @ModelAttribute("hlaska") String hlaska) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Objednavka objednavka = seznamObjednavek.findById(id);
        model.put("objednavka", objednavka);
        return "/Ucet/DetailObjednavky";

    }


    @PostMapping(value = "/admin/upravitadresu")
    public String upraveniadresy(
            @RequestParam("id") Integer id,
            @RequestParam("mesto") String mesto,
            @RequestParam("psc") String psc,
            @RequestParam("ulice") String ulice,
            @RequestParam("cp") String cp,
            @RequestParam("stav") Integer stav,
            @RequestParam Map<String, Object> model
            , RedirectAttributes redirectAttributes
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Objednavka objednavka = seznamObjednavek.findById(id);
        objednavka.getAdresa().setMesto(mesto);
        objednavka.getAdresa().setPsc(psc);
        objednavka.getAdresa().setUlice(ulice);
        objednavka.getAdresa().setCps(cp);
        SeznamStavu stavy = new SeznamStavu();
        stavy.getSeznamStavu().addAll(seznamStavu.findAll());
        for (Stav stava : stavy.getSeznamStavu()
        ) {
            if (stava.getStav_ID() == stav) {
                objednavka.setStav(stava);

            }
        }
        seznamObjednavek.save(objednavka);
        //  Objednavka objednavk2a= seznamObjednavek.findById(id);
        //  model.clear();

        // model.put("stavy",stavy.getSeznamStavu());
        //  model.put("objednavka",objednavk2a);
        // redirectAttributes.addAttribute("id",id);
        //return "/Spravy/DetailObjednakvySprava";
        //return   detailObjednavkySprava(id,model);
        return "redirect:/admin/sprava_objednavek";

    }

    @PostMapping(value = "/admin/detailobjednavky")
    public String detailObjednavkySprava(@RequestParam("id") Integer id, Map<String, Object> model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Ucet ucet = seznamUcty.findByLogin(authentication.getName());
        Objednavka objednavka = seznamObjednavek.findById(id);

        SeznamStavu stavy = new SeznamStavu();
        stavy.getSeznamStavu().addAll(seznamStavu.findAll());
        model.put("stavy", stavy.getSeznamStavu());
        model.put("objednavka", objednavka);
        return "/Spravy/DetailObjednakvySprava";

    }

    @GetMapping(value = {"/admin/Sprava_Objednavek", "/admin/sprava_objednavek"})
    public String ShowAllObjednavky(Pageable pageable,
                                    @RequestParam("page") Optional<Integer> page, Map<String, Object> model) {

        final int currentPage = page.orElse(0);
        final int size = 3;
        Pageable customPageable = PageRequest.of(currentPage, size);
        Page<Objednavka> stranka = this.seznamObjednavek.findAllPagesobjednavky(customPageable);
        model.put("stranka", stranka);

        return "Spravy/Sprava_Objednavky";
    }


    @PostMapping(value = {"/hledaniproduktu"})
    public String Hledani(@RequestParam(value = "nalezeno", required = false) int hledany,
                          Map<String, Object> model) {


        Objednavka objednavka = seznamObjednavek.findById(hledany);


        if (objednavka == null) {
            model.put("hlaska", "Žádný produkt s takovým názvem nebyl nalezen");
            return "Spravy/Sprava_Objednavky";
        } else {


            return detailObjednavkySprava(objednavka.getObjednavka_ID(), model);

        }


    }
}
