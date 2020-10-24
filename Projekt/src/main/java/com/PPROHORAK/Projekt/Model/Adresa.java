package com.PPROHORAK.Projekt.Model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name="t_Adresy")
//@Getter
//@Setter
//@RequiredArgsConstructor
public class Adresa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)

    private  Integer adresa_ID;


    @NotBlank (message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  String mesto;
    @NotBlank(message = "Hodnota musí být vyplněna")
   @Size(max=250)
    private  String cps;
    @NotBlank (message = "Hodnota musí být vyplněna")
   @Size(max=250)
    private  String ulice;
    @NotBlank (message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private String psc;

    public void setUcet(Ucet ucet) {
        this.ucet = ucet;
    }


    @OneToOne(cascade = CascadeType.PERSIST)
    private Ucet ucet;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Objednavka objednavka;

    public void setObjednavka(Objednavka objednavka) {
        this.objednavka = objednavka;
    }

    public Integer  getAdresa_ID() {
        return adresa_ID;
    }
    public String getMesto() {
        return mesto;
    }

    public String getCps() {
        return cps;
    }

    public String getUlice() {
        return ulice;
    }

    public String getPsc() {
        return psc;
    }


    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public void setCps(String cps) {
        this.cps = cps;
    }

    public void setUlice(String ulice) {
        this.ulice = ulice;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public Ucet getUcet() {
        return ucet;
    }

    public Objednavka getObjednavka() {
        return objednavka;
    }
}
