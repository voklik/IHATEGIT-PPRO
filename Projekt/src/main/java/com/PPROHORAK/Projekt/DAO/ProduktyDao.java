package com.PPROHORAK.Projekt.DAO;

import com.PPROHORAK.Projekt.Model.Produkt;
import com.PPROHORAK.Projekt.Model.Stav;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface ProduktyDao  extends Repository<Produkt, Integer> {



    @Transactional(readOnly = true)
    @Cacheable("t_produkty")
    Collection<Produkt> findAll() throws DataAccessException;


    @Transactional(readOnly = true)
    Produkt findById(Integer id);


    @Query("SELECT DISTINCT produkt FROM Produkt  produkt WHERE produkt.nazev LIKE :nazev%")
    @Transactional(readOnly = true)
    Produkt findByNazev(@Param("nazev") String nazev);



    void save(Produkt produkt);

    @Query("DELETE FROM Produkt produkt WHERE produkt.produkt_ID =:id")
    @Transactional
    @Modifying
    void deleteById(@Param("id") Integer id);
}
