package com.PPROHORAK.Projekt.DAO;

import com.PPROHORAK.Projekt.Model.Polozka;
import com.PPROHORAK.Projekt.Model.PolozkaKosik;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface PolozkyKosikDao extends Repository<PolozkaKosik, Integer>

{

        @Transactional(readOnly = true)
        @Cacheable("t_PolozkyKosik")
        Collection<PolozkaKosik> findAll() throws DataAccessException;


        @Transactional(readOnly = true)
        PolozkaKosik findById(Integer id);





        void save(PolozkaKosik polozka);


        @Query("DELETE FROM PolozkaKosik polozka WHERE polozka.polozkaKosiks_ID =:id")
        @Transactional
        @Modifying
        void deleteByUserOne(@Param("id") Integer id);
        @Query("DELETE FROM PolozkaKosik polozka WHERE polozka.ucet.ucet_ID =:id")
        @Transactional
        @Modifying
        void deleteByUserAll(@Param("id") Integer id);
}
