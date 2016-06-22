package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.ICtgSupplierManagement;
import com.jbd.model.CtgSupplier;


public class CtgSupplierManagementDAO implements ICtgSupplierManagement {


	@PersistenceContext
	public EntityManager em;

	public CtgSupplierManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public CtgSupplier insertCtgSupplier(CtgSupplier o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateCtgSupplier(CtgSupplier o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCtgSupplier(CtgSupplier o) {
		// TODO Auto-generated method stub

	}

	@Override
	public CtgSupplier findCtgSupplier(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
