package com.PPROHORAK.Projekt.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="t_Stavy")
//@Getter
//@Setter
//@RequiredArgsConstructor
public class Stav implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stav_id", updatable = false, nullable = false)
    private  Integer stav_ID;

    @NotBlank(message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  String nazev;

    private boolean aktivni=false;

    public boolean isAktivni() {
        return aktivni;
    }

    public void setAktivni(boolean aktivni) {
        this.aktivni = aktivni;
    }
    public Integer getStav_ID() {
        return stav_ID;
    }


    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }


    @OneToMany(mappedBy = "stav", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Objednavka> objednavky;
}
