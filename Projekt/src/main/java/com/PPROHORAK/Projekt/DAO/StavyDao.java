package com.PPROHORAK.Projekt.DAO;

import com.PPROHORAK.Projekt.Model.Adresa;
import com.PPROHORAK.Projekt.Model.Stav;
import com.PPROHORAK.Projekt.Model.Ucet;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public  interface StavyDao extends Repository<Stav, Integer> {



    @Transactional(readOnly = true)
    @Cacheable("t_stavy")
    Collection<Stav> findAll() throws DataAccessException;


    @Transactional(readOnly = true)
    Stav findById(Integer id);


    @Query("SELECT DISTINCT stav FROM Stav stav WHERE stav.nazev LIKE :nazev%")
    @Transactional(readOnly = true)
    Stav findByNazev(@Param("nazev") String nazev);



    void save(Stav stav);

    @Query("DELETE FROM Stav stav WHERE stav.stav_ID =:id")
    @Transactional
    @Modifying
    void deleteById(@Param("id") Integer id);


}
