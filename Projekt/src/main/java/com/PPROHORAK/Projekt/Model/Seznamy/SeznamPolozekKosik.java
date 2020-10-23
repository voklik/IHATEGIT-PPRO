package com.PPROHORAK.Projekt.Model.Seznamy;


import com.PPROHORAK.Projekt.Model.Polozka;
import com.PPROHORAK.Projekt.Model.PolozkaKosik;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SeznamPolozekKosik {
    private List<PolozkaKosik> polozky;

    @XmlElement
    public List<PolozkaKosik> getSeznamPolozekKosik(){
        if (polozky == null){
            polozky = new ArrayList<>();
        }
        return polozky;
    }
}

