package com.PPROHORAK.Projekt.Model.Seznamy;



import com.PPROHORAK.Projekt.Model.Platforma;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SeznamPlatforem {
    private List<Platforma> platformy;

    @XmlElement
    public List<Platforma> getSeznamPlatformy(){
        if (platformy == null){
            platformy = new ArrayList<>();
        }
        return platformy;
    }
}

