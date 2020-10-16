package com.PPROHORAK.Projekt.Model.Seznamy;


import com.PPROHORAK.Projekt.Model.Polozka;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SeznamPolozek {
    private List<Polozka> polozky;

    @XmlElement
    public List<Polozka> getSeznamPolozek(){
        if (polozky == null){
            polozky = new ArrayList<>();
        }
        return polozky;
    }
}

