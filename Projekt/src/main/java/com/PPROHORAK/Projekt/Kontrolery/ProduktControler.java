package com.PPROHORAK.Projekt.Kontrolery;

import com.PPROHORAK.Projekt.DAO.PlatformyDao;
import com.PPROHORAK.Projekt.DAO.ProduktyDao;
import com.PPROHORAK.Projekt.Model.Produkt;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamPlatforem;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamProduktu;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Controller
@Data
public class ProduktControler {

    private final PlatformyDao seznamPlatformy;
    private final ProduktyDao seznamProduktu;

    @RequestMapping(value = {"/admin/Sprava_Produkty","/admin/sprava_produkty"})
    public String AdminShowALL(Map<String, Object> model){
        SeznamProduktu produkty = new SeznamProduktu();
        produkty.getSeznamProduktu().addAll(seznamProduktu.findAll());

        model.put("ListProdukty", produkty.getSeznamProduktu());

        new UtilControler().GetPlatformList(model,seznamPlatformy);

        return "Spravy/Sprava_Produkty";



    }


    @RequestMapping(value = {"/DetailProduktu","/detailprodukt"})
    public String DetailHry(
            @RequestParam(value="produktID") Integer ProduktID,
            Map<String, Object> model )
    {
        SeznamProduktu produkty = new SeznamProduktu();
        produkty.getSeznamProduktu().add(seznamProduktu.findById(ProduktID));
        model.put("ListProdukty", produkty.getSeznamProduktu());

        new UtilControler().GetPlatformList(model,seznamPlatformy);

            return "Prodej/DetailProduktu";
       }



    @RequestMapping(value = {"/SeznamHer","/seznamher"})
    public String ShowAllByPlatforma(
                     @RequestParam(value="id",required = false) Integer platformaID,
Map<String, Object> model )
    {

        if(platformaID==null)
        {
            SeznamProduktu produkty = new SeznamProduktu();
            produkty.getSeznamProduktu().addAll(seznamProduktu.findAll());

            model.put("ListProdukty", produkty.getSeznamProduktu());

            new UtilControler().GetPlatformList(model,seznamPlatformy);

            return "Prodej/SeznamHer";
        }
        else
        {
        SeznamProduktu produkty = new SeznamProduktu();
        produkty.getSeznamProduktu().addAll(seznamProduktu.findByPlatforma(platformaID));

        model.put("ListProdukty", produkty.getSeznamProduktu());

        new UtilControler().GetPlatformList(model,seznamPlatformy);

        return "Prodej/SeznamHer";
    }}

    @RequestMapping(value = {"/Slevy","slevy"})
    public String ShowVeSleve(
                        Map<String, Object> model )
    {
        SeznamProduktu produkty = new SeznamProduktu();
        produkty.getSeznamProduktu().addAll(seznamProduktu.findVeSleve());

        model.put("ListProdukty", produkty.getSeznamProduktu());

        new UtilControler().GetPlatformList(model,seznamPlatformy);

        return "Prodej/SeznamHer";
    }


    @PostMapping(value = {"/admin/Sprava_ProduktyUpdate","/admin/sprava_produktyupdate"})
    public String AdminChange(
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



        return "redirect:/admin/Sprava_Produkty";

    }

}
