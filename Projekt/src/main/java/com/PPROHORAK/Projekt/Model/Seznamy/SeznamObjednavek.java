package com.PPROHORAK.Projekt.Model.Seznamy;



import com.PPROHORAK.Projekt.Model.Objednavka;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SeznamObjednavek {
    private List<Objednavka> objednavky;

    @XmlElement
    public List<Objednavka> getSeznamObjednavek(){
        if (objednavky == null){
            objednavky = new ArrayList<>();
        }
        return objednavky;
    }
}
