package com.PPROHORAK.Projekt.Model.Seznamy;

import com.PPROHORAK.Projekt.Model.Ucet;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SeznamUcet {
    private List<Ucet> ucty;

    @XmlElement
    public List<Ucet> getSeznamUctu(){
        if (ucty == null){
            ucty = new ArrayList<>();
        }
        return ucty;
    }
}
