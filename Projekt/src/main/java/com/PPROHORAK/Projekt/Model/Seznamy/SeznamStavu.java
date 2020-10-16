package com.PPROHORAK.Projekt.Model.Seznamy;


import com.PPROHORAK.Projekt.Model.Stav;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SeznamStavu {
    private List<Stav> stavy;

    @XmlElement
    public List<Stav> getSeznamStavu(){
        if (stavy == null){
            stavy = new ArrayList<>();
        }
        return stavy;
    }
}
