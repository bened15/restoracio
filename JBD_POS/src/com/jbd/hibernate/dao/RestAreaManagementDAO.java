package com.jbd.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestAreaManagement;
import com.jbd.model.RestArea;

public class RestAreaManagementDAO implements IRestAreaManagement {

	@PersistenceContext
	public EntityManager em;

	public RestAreaManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestArea insertRestArea(RestArea o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestArea(RestArea o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRestArea(RestArea o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestArea findRestArea(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public List<RestArea> getAllAreas() {
		try {
			TypedQuery<RestArea> tq = em.createNamedQuery("RestArea.findAll", RestArea.class);
			List<RestArea> areas = tq.getResultList();
			return areas;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

}
