package com.PPROHORAK.Projekt.DAO;

import com.PPROHORAK.Projekt.Model.Adresa;
import com.PPROHORAK.Projekt.Model.Objednavka;
import com.PPROHORAK.Projekt.Model.Ucet;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface AdresyDao extends Repository<Adresa, Integer> {


    @Transactional(readOnly = true)
    @Cacheable("t_adresy")
    Collection<Adresa> findAll() throws DataAccessException;


    @Transactional(readOnly = true)
    Adresa findById(Integer id);


    void save(Adresa adresa);

    @Query("DELETE FROM Adresa adresa WHERE adresa.adresa_ID =:id")
    @Transactional
    @Modifying
    void deleteById(@Param("id") Integer id);
}
