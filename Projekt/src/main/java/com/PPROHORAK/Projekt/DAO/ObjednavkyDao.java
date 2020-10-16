package com.PPROHORAK.Projekt.DAO;

import com.PPROHORAK.Projekt.Model.Adresa;
import com.PPROHORAK.Projekt.Model.Objednavka;
import com.PPROHORAK.Projekt.Model.Platforma;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface ObjednavkyDao extends Repository<Objednavka, Integer>
{


        @Transactional(readOnly = true)
        @Cacheable("t_objednavky")
        Collection<Objednavka> findAll() throws DataAccessException;


        @Transactional(readOnly = true)
        Objednavka findById(Integer id);


        void save(Objednavka objednavka);

        @Query("DELETE FROM Objednavka objednavka WHERE objednavka.objednavka_ID =:id")
        @Transactional
        @Modifying
        void deleteById(@Param("id") Integer id);
}