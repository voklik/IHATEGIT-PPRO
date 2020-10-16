package com.PPROHORAK.Projekt.Model.Seznamy;


import com.PPROHORAK.Projekt.Model.TypUctu;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SeznamTypyUctu {
    private List<TypUctu> typyUctu;

    @XmlElement
    public List<TypUctu> getSeznamTypyUctu(){
        if (typyUctu == null){
            typyUctu = new ArrayList<>();
        }
        return typyUctu;
    }
}
