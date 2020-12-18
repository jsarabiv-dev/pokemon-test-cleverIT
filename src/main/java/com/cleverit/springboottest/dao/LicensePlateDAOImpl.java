package com.cleverit.springboottest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cleverit.springboottest.model.LicensePlate;

@Repository
public class LicensePlateDAOImpl   {

	public final Logger logger = LoggerFactory.getLogger(LicensePlateDAOImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void saveLicenses(List<LicensePlate> listLP){

		
		listLP.forEach(lp -> {
			try {
				em.persist(lp);
			} catch (Exception  e) {
				logger.error("Se a generado un error al guardar el LicensePlate:"+ lp.toString());
				logger.error("Error:", e.getCause());
			}

		});

	}

}
