package com.PPROHORAK.Projekt.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="t_Platformy")
//@Getter
//@Setter
//@RequiredArgsConstructor
public class Platforma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "platforma_id", updatable = false, nullable = false)
    private  Integer platforma_ID;

    @NotBlank(message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  String nazev;


     @OneToMany(mappedBy = "platforma", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
      private List<Produkt> produkty;

    public Integer getPlatforma_ID() {
        return platforma_ID;
    }



    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }
   
}

