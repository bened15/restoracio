package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.ICtgMeasureUnitManagement;
import com.jbd.model.CtgMeasureUnit;


public class CtgMeasureUnitManagementDAO implements ICtgMeasureUnitManagement {


	@PersistenceContext
	public EntityManager em;

	public CtgMeasureUnitManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public CtgMeasureUnit insertCtgMeasureUnit(CtgMeasureUnit o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateCtgMeasureUnit(CtgMeasureUnit o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCtgMeasureUnit(CtgMeasureUnit o) {
		// TODO Auto-generated method stub

	}

	@Override
	public CtgMeasureUnit findCtgMeasureUnit(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
