package com.PPROHORAK.Projekt.DAO;

import com.PPROHORAK.Projekt.Model.Ucet;



import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;

import java.util.Collection;


public interface UctyDao extends Repository<Ucet, Integer> {

    @Transactional(readOnly = true)
    @Cacheable("t_ucty")
    Collection<Ucet> findAll() throws DataAccessException;


    @Transactional(readOnly = true)
    Ucet findById(Integer id);


    @Query("SELECT DISTINCT ucet FROM Ucet ucet WHERE ucet.prijmeni LIKE :prijmeni%")
    @Transactional(readOnly = true)
    Ucet findByPrijmeni(@Param("prijmeni") String prijmeni);


    @Query("SELECT DISTINCT ucet FROM Ucet ucet WHERE ucet.ucet_login  =:login")
    @Transactional(readOnly = true)
    Ucet findByLogin(@Param("login") String login);

    void save(Ucet ucet);

    @Query("DELETE FROM Ucet ucet WHERE ucet.ucet_ID =:ucet_id")
    @Transactional
    @Modifying
    void deleteById(@Param("ucet_id") Integer ucet_id);

////////////////////////////////////////////////////////////
    @Query("SELECT DISTINCT ucet FROM Ucet ucet")
    @Transactional(readOnly = true)
     Page findAllPagesUcty(Pageable pageable);


}
