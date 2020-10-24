package com.PPROHORAK.Projekt.Kontrolery;

import com.PPROHORAK.Projekt.DAO.PlatformyDao;
import com.PPROHORAK.Projekt.DAO.ProduktyDao;
import com.PPROHORAK.Projekt.DAO.UctyDao;
import com.PPROHORAK.Projekt.Model.Objednavka;
import com.PPROHORAK.Projekt.Model.Produkt;
import com.PPROHORAK.Projekt.Model.Seznamy.SeznamProduktu;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Controller
@Data
public class ProduktControler {
    private final UctyDao seznamUcty;
    private final PlatformyDao seznamPlatformy;
    private final ProduktyDao seznamProduktu;


    @RequestMapping(value = {"/","/Index","/Home","/home","/index"})
    public String Home(@CookieValue(value = "kosik", defaultValue = "") String kos,Map<String, Object> model){
        new UtilControler().GetPocetPolozekVkosiku(model,seznamProduktu,seznamUcty,kos);
        new UtilControler().GetPlatformList(model,seznamPlatformy);

        return "Index";



    }

    @RequestMapping(value = {"/admin/Sprava_Produkty","/admin/sprava_produkty"})
    public String AdminShowALL(Pageable pageable,
                               @RequestParam("page") Optional<Integer> page,Map<String, Object> model){
        final int currentPage = page.orElse(0);
        final int size = 3;
        Pageable customPageable = PageRequest.of(currentPage, size);
        Page<Produkt> stranka = this.seznamProduktu.findAllPagesProdukty(customPageable);
        model.put("ProduktStranka", stranka);

        new UtilControler().GetPlatformList(model,seznamPlatformy);
        return "Spravy/Sprava_Produkty";



    }


    @RequestMapping(value = {"/DetailProduktu","/detailprodukt"})
    public String DetailHry(@CookieValue(value = "kosik", defaultValue = "") String kos,
            @RequestParam(value="produktID") Integer ProduktID,
            Map<String, Object> model )
    {
        new UtilControler().GetPocetPolozekVkosiku(model,seznamProduktu,seznamUcty,kos);
        SeznamProduktu produkty = new SeznamProduktu();
        produkty.getSeznamProduktu().add(seznamProduktu.findById(ProduktID));
        model.put("ListProdukty", produkty.getSeznamProduktu());

        new UtilControler().GetPlatformList(model,seznamPlatformy);

            return "Prodej/DetailProduktu";
       }
    @RequestMapping(value = {"/admin/DetailProduktu","/admin/detailprodukt"})
    public String DetailHryAdmin(
                            @RequestParam(value="produktID") Integer ProduktID,
                            Map<String, Object> model )
    {
        SeznamProduktu produkty = new SeznamProduktu();
        produkty.getSeznamProduktu().add(seznamProduktu.findById(ProduktID));
        model.put("ListProdukty", produkty.getSeznamProduktu());

        new UtilControler().GetPlatformList(model,seznamPlatformy);

        return "Spravy/DetailProdukt";
    }

    @RequestMapping(value = {"/HledaniProduktu","/hledaniproduktu"})
    public String Hledani(Pageable pageable,
                          @RequestParam("page") Optional<Integer> page,@CookieValue(value = "kosik", defaultValue = "") String kos,
            @RequestParam(value="nalezeno",required = false) String nazev,
            Map<String, Object> model ) {
        new UtilControler().GetPocetPolozekVkosiku(model,seznamProduktu,seznamUcty,kos);
        model.put("nalezeno",nazev);
        if (nazev!=null) {
            model.put("nalezeno",nazev);
            nazev ='%'+nazev +'%';


            SeznamProduktu produkty = new SeznamProduktu();
            produkty.getSeznamProduktu().addAll(seznamProduktu.findByNazevSeznam(nazev));

            final int currentPage = page.orElse(0);
            final int size = 3;
            Pageable customPageable = PageRequest.of(currentPage, size);
            Page<Produkt> stranka = this.seznamProduktu.finHledanyPage(customPageable,nazev);
            model.put("ProduktStranka", stranka);
            model.put("typ","HLEDANY");
          //  model.put("nalezeno",nazev);
            return "Prodej/SeznamHer";
        }
        else

        {

            model.put("hlaska","Žádný produkt s takovým názvem nebyl nalezen");



            return "Prodej/SeznamHer";
        }
    }


    @PostMapping(value = {"/admin/hledaniproduktu"})
    public String HledaniAdmin(@RequestParam(value = "nalezeno", required = false) int hledany,
                          Map<String, Object> model) {

        Produkt produkt = seznamProduktu.findById(hledany);


        if (produkt == null) {
            model.put("hlaska", "Žádný produkt s takovým názvem nebyl nalezen");
            return "/Spravy/Sprava_Produkty";
        } else {

SeznamProduktu seznam=new SeznamProduktu();
seznam.getSeznamProduktu().add(produkt);
          model.put("ListProdukty",seznam.getSeznamProduktu());
          new UtilControler().GetPlatformList(model,seznamPlatformy);
return "/Spravy/DetailProdukt";
        }


    }
//strankovani
@RequestMapping(value = {"/Slevy","slevy"})
    public String ShowAllVeSleve(@CookieValue(value = "kosik", defaultValue = "") String kos,
                                 HttpServletResponse response, Pageable pageable,
        @RequestParam("page") Optional<Integer> page, Map<String, Object> model)
{

    new UtilControler().GetPocetPolozekVkosiku(model,seznamProduktu,seznamUcty,kos);
    final int currentPage = page.orElse(0);
        final int size = 3;
        Pageable customPageable = PageRequest.of(currentPage, size);
        Page<Produkt> stranka = this.seznamProduktu.findAllPagesVeSleve(customPageable);
         model.put("ProduktStranka", stranka);
    model.put("typ","SLEVY");
    new UtilControler().GetPlatformList(model, seznamPlatformy);
        return "Prodej/SeznamHer";
    }



    @RequestMapping(value = {"/SeznamHer","/seznamher"})
    public String ShowAllByPlatforma(@CookieValue(value = "kosik", defaultValue = "") String kos,Pageable pageable,
                                     @RequestParam("page") Optional<Integer> page,
                     @RequestParam(value="id",required = false) Integer platformaID,
Map<String, Object> model )
    {
        new UtilControler().GetPocetPolozekVkosiku(model,seznamProduktu,seznamUcty,kos);
        new UtilControler().GetPlatformList(model, seznamPlatformy);
        if(platformaID==null)
        {
            final int currentPage = page.orElse(0);
            final int size = 3;
            Pageable customPageable = PageRequest.of(currentPage, size);
            Page<Produkt> stranka = this.seznamProduktu.findAllPagesProdukty(customPageable);
            model.put("ProduktStranka", stranka);
            model.put("typ","ALL");
            return "Prodej/SeznamHer";
        }
        else
        {
            final int currentPage = page.orElse(0);
            final int size = 3;
            Pageable customPageable = PageRequest.of(currentPage, size);
            Page<Produkt> stranka = this.seznamProduktu.findAllPagesByPlatforma(customPageable,platformaID);
            model.put("ProduktStranka", stranka);
                model.put("typ","PLATFORMA");
            model.put("id",platformaID);
            return "Prodej/SeznamHer";
    }}



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


    @GetMapping (value = {"/NaseptavacProdukty","/naseptavacprodukty"},
            produces =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
   String naseptavacProdukty(
            @RequestParam("nalezeno") String nalezeno) {
        if (nalezeno != null)
        {
String hledany= "%"+nalezeno.toLowerCase()+"%";
            ArrayList<Produkt> x =seznamProduktu.findByNazevSeznam(hledany);
    int max = x.size();
            String vrat="Nalezeno polozek:" +x.size();
    if (max>10)
    {
max=10;
vrat+=" Tady máte prvních deset možností <BR>";
    }
    else
    {
        vrat+="<BR>";
    }

    for (int i=0;i<max;i++)
    {
        Produkt p = x.get(i);

        vrat+=" <a href='/DetailProduktu?produktID="+p.getProdukt_ID() +"' >"
                +p.getNazev()+ " "+p.getPlatforma().getNazev()
                +" Aktuální cena "+p.getAktualniCena()+ " sleva "
                + p.getSleva()+"%</a><BR>";
    }
          

            return new String(vrat);
        }

else
    return   new String("");
    }


    @PostMapping(value = {"/admin/upravaprodukt"})
    public String adminchangeDetail(
            @RequestParam("id") Integer produktID,
            @RequestParam("nazev") String nazev,
            @RequestParam("cena") Integer cena,
            @RequestParam("sleva") Integer sleva,
            @RequestParam("platforma") Integer platformaID,
            Map<String, Object> model
    )
    {





            Produkt produkt = seznamProduktu.findById(produktID);
            produkt.setCena(cena);
            produkt.setSleva(sleva);
            produkt.setNazev(nazev);
            produkt.setPlatforma(seznamPlatformy.findById(platformaID));
            seznamProduktu.save(produkt);
model.put("hlaska","Produkt byl upraven");

        return DetailHryAdmin(produktID,model);
    }


}
