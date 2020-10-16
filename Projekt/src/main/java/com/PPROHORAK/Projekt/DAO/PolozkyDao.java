package com.PPROHORAK.Projekt.DAO;

import com.PPROHORAK.Projekt.Model.Adresa;
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

public interface PolozkyDao extends Repository<Polozka, Integer>

{

        @Transactional(readOnly = true)
        @Cacheable("t_polozky")
        Collection<Polozka> findAll() throws DataAccessException;


        @Transactional(readOnly = true)
        Polozka findById(Integer id);





        void save(Polozka polozka);

        @Query("DELETE FROM Polozka polozka WHERE polozka.polozka_ID =:id")
        @Transactional
        @Modifying
        void deleteById(@Param("id") Integer id);
}
