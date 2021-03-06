package com.PPROHORAK.Projekt.Model;

import com.PPROHORAK.Projekt.Model.Seznamy.SeznamPolozek;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_Objednavky")
//@Getter
//@Setter
//@RequiredArgsConstructor
public class Objednavka implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private  Integer objednavka_ID;

    @NotBlank(message = "Hodnota musí být vyplněna")
   @Size(max=250)
    private Integer cena;

   // @NotBlank (message = "Hodnota musí být vyplněna")
   @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date datumObjednani;


    @OneToOne(mappedBy = "objednavka", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Adresa adresa;

    @OneToMany(mappedBy = "objednavka", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private List<Polozka> polozky;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Stav stav;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Ucet ucet;


    @NotBlank (message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  String jmeno;

    @NotBlank (message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  String prijmeni;
    @NotBlank (message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  String email;

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public void setPolozky(List<Polozka> polozky) {
        this.polozky = polozky;
    }

    public void setStav(Stav stav) {
        this.stav = stav;
    }

    public void setUcet(Ucet ucet) {
        this.ucet = ucet;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public void setDatumObjednani(Date datumObjednani) {
        this.datumObjednani = datumObjednani;
    }

    public Integer getObjednavka_ID() {
        return objednavka_ID;
    }

    public Integer getCena() {
        return cena;
    }

    public Date getDatumObjednani() {
        return datumObjednani;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public Stav getStav() {
        return stav;
    }

    public List<Polozka> getPolozky() {
        return polozky;
    }

    public Ucet getUcet() {
        return ucet;
    }
}
