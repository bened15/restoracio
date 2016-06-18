package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.ICtgProductManagement;
import com.jbd.model.CtgProduct;


public class CtgProductManagementDAO implements ICtgProductManagement {


	@PersistenceContext
	public EntityManager em;

	public CtgProductManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public CtgProduct insertCtgProduct(CtgProduct o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateCtgProduct(CtgProduct o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCtgProduct(CtgProduct o) {
		// TODO Auto-generated method stub

	}

	@Override
	public CtgProduct findCtgProduct(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
