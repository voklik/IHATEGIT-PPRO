package com.PPROHORAK.Projekt.Model.Seznamy;


import com.PPROHORAK.Projekt.Model.Produkt;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SeznamProduktu{
    private List<Produkt> produkty;

    @XmlElement
    public List<Produkt> getSeznamProduktu(){
        if (produkty == null){
            produkty = new ArrayList<>();
        }
        return produkty;
    }
}
