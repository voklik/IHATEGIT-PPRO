package com.PPROHORAK.Projekt.DAO;

import com.PPROHORAK.Projekt.Model.Platforma;
import com.PPROHORAK.Projekt.Model.Polozka;
import com.PPROHORAK.Projekt.Model.Produkt;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface PlatformyDao extends Repository< Platforma, Integer>

{

    @Transactional(readOnly = true)
    @Cacheable("t_platformy")
    Collection<Platforma> findAll() throws DataAccessException;


    @Transactional(readOnly = true)
    Platforma findById(Integer id);


    @Query("SELECT DISTINCT platforma FROM Platforma platforma WHERE platforma.nazev LIKE :nazev%")
    @Transactional(readOnly = true)
    Platforma findByNazev(@Param("nazev") String nazev);



    void save(Platforma platforma);

    @Query("DELETE FROM Platforma platforma WHERE platforma.platforma_ID =:id")
    @Transactional
    @Modifying
    void deleteById(@Param("id") Integer id);
}
