package com.PPROHORAK.Projekt.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="t_Typy_Uctu")
//@Getter
//@Setter
//@RequiredArgsConstructor
public class TypUctu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private  Integer typUctu_ID;

    @NotBlank(message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  String nazev;
    @OneToMany(mappedBy = "typUctu", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Ucet> ucty;

    private boolean aktivni=false;

    public boolean isAktivni() {
        return aktivni;
    }

    public void setAktivni(boolean aktivni) {
        this.aktivni = aktivni;
    }

    public Integer getTypUctu_ID() {
        return typUctu_ID;
    }


    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }
}
