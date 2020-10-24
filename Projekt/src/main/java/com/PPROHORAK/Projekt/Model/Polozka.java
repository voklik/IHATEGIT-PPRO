package com.PPROHORAK.Projekt.Model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name="t_Polozky")
//@Getter
//@Setter
//@RequiredArgsConstructor
public class Polozka implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private  Integer polozka_ID;

    @NotBlank(message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  int pocet;
    @NotBlank(message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  int sleva;
    @NotBlank(message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  int cena;

    private boolean aktivni=false;

    public boolean isAktivni() {
        return aktivni;
    }

    public void setAktivni(boolean aktivni) {
        this.aktivni = aktivni;
    }

   //@ManytoOne(mappedBy = "platforma", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  //  private Produkt produkt;


    public Integer getPolozka_ID() {
        return polozka_ID;
    }



    public int getPocet() {
        return pocet;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
    }

    public int getSleva() {
        return sleva;
    }

    public void setSleva(int sleva) {
        this.sleva = sleva;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Objednavka objednavka;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Produkt produkt;



    public void setObjednavka(Objednavka objednavka) {
        this.objednavka = objednavka;
    }

    public void setProdukt(Produkt produkt) {
        this.produkt = produkt;
    }

    public Objednavka getObjednavka() {
        return objednavka;
    }

    public Produkt getProdukt() {
        return produkt;
    }
}
