package com.PPROHORAK.Projekt.DAO;

import com.PPROHORAK.Projekt.Model.Adresa;
import com.PPROHORAK.Projekt.Model.TypUctu;
import com.PPROHORAK.Projekt.Model.Ucet;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface TypyUctuDao extends Repository<TypUctu, Integer> {



    @Transactional(readOnly = true)
    @Cacheable("t_typy_uctu")
    Collection<TypUctu> findAll() throws DataAccessException;


    @Transactional(readOnly = true)
    TypUctu findById(Integer id);


    @Query("SELECT DISTINCT typ FROM TypUctu typ WHERE typ.nazev LIKE :nazev%")
    @Transactional(readOnly = true)
    TypUctu findByNazev(@Param("nazev") String nazev);



    void save(TypUctu kUlozeni);

    @Query("DELETE FROM TypUctu typ WHERE typ.typUctu_ID =:id")
    @Transactional
    @Modifying
    void deleteById(@Param("id") Integer id);
}
