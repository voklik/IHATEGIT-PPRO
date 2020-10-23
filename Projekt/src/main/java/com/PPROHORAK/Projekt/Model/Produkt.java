package com.PPROHORAK.Projekt.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="t_Produkty")
//@Getter
//@Setter
//@RequiredArgsConstructor
public class Produkt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private  Integer produkt_ID;

    @NotBlank(message = "Hodnota musí být vyplněna")
   @Size(max=250)
    private  String nazev;
    @NotBlank (message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  Integer cena;
    @NotBlank (message = "Hodnota musí být vyplněna")
    @Size(max=100)
    private  Integer sleva;

    public Integer getProdukt_ID() {
        return produkt_ID;
    }


    private boolean aktivni=false;

    public boolean isAktivni() {
        return aktivni;
    }

    public void setAktivni(boolean aktivni) {
        this.aktivni = aktivni;
    }
    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public Integer getSleva() {
        return sleva;
    }

    public void setSleva(Integer sleva) {
        this.sleva = sleva;
    }

    public void setPlatforma(Platforma platforma) {
        this.platforma = platforma;
    }

    public Platforma getPlatforma() {
        return platforma;
    }

    // @OneToOne(mappedBy = "ucet", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ManyToOne(cascade = CascadeType.PERSIST)
   private Platforma platforma;

    @OneToOne(mappedBy = "produkt", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Polozka polozka;
    @OneToMany(mappedBy = "produkt", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PolozkaKosik> KosikPolozky;

    public float   getAktualniCena()
    {
        if (sleva!=0)
        return Math.round(cena-((cena*sleva)/100f));
        else
            return
            cena;
    }
}

