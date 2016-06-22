package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.ICtgProductTypeManagement;
import com.jbd.model.CtgProductType;


public class CtgProductTypeManagementDAO implements ICtgProductTypeManagement {


	@PersistenceContext
	public EntityManager em;

	public CtgProductTypeManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public CtgProductType insertCtgProductType(CtgProductType o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateCtgProductType(CtgProductType o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCtgProductType(CtgProductType o) {
		// TODO Auto-generated method stub

	}

	@Override
	public CtgProductType findCtgProductType(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
