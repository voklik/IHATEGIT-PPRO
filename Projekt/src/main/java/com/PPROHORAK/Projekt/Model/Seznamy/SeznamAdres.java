package com.PPROHORAK.Projekt.Model.Seznamy;


import com.PPROHORAK.Projekt.Model.Adresa;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SeznamAdres{
    private List<Adresa> adresy;

    @XmlElement
    public List<Adresa> getSeznamAdres(){
        if (adresy == null){
            adresy = new ArrayList<>();
        }
        return adresy;
    }
}
