package com.PPROHORAK.Projekt.Model;
//import lombok.Data;
//import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;


@Entity
@Table(name="t_Ucty")
//@Getter
//@Setter
//@RequiredArgsConstructor
public class Ucet implements Serializable{



    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ucet_id", updatable = false, nullable = false)
      Integer ucet_ID;

 @NotBlank (message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  String jmeno;

    @NotBlank (message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  String prijmeni;

    @NotBlank (message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private   String ucet_login;

    @NotBlank (message = "Hodnota musí být vyplněna")
    @Size(max=250)
    private  String heslo;
    @OneToOne(mappedBy = "ucet", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Adresa adresa;

    @OneToMany(mappedBy = "ucet", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Objednavka> objednavky;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TypUctu typUctu;

    private boolean aktivni=false;

    public boolean isAktivni() {
        return aktivni;
    }

    public void setAktivni(boolean aktivni) {
        this.aktivni = aktivni;
    }

    public Integer getUcet_ID() {
        return ucet_ID;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public String getUcet_login() {
        return ucet_login;
    }

    public String getHeslo() {
        return heslo;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public void setUcet_login(String ucet_login) {
        this.ucet_login = ucet_login;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public void setHeslo(String heslo) {

        this.heslo =  heslo;
    }



    public List<Objednavka> getObjednavky() {
        return objednavky;
    }


    public TypUctu getTypUctu() {
        return typUctu;
    }

    public void setTypUctu(TypUctu typUctu) {
        this.typUctu = typUctu;
    }
}
