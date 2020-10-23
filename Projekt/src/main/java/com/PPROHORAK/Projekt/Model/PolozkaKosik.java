package com.PPROHORAK.Projekt.Model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name="t_PolozkyKosik")
//@Getter
//@Setter
//@RequiredArgsConstructor
public class PolozkaKosik implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private  Integer polozkaKosiks_ID;

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
        return polozkaKosiks_ID;
    }



    public int getPocet() {
        return pocet;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
    }



    @ManyToOne(cascade = CascadeType.PERSIST)
    private Ucet ucet;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Produkt produkt;


    public Ucet getUcet() {
        return ucet;
    }

    public void setUcet(Ucet ucet) {
        this.ucet = ucet;
    }

    public void setProdukt(Produkt produkt) {
        this.produkt = produkt;
    }

    public Produkt getProdukt() {
        return produkt;
    }
}
