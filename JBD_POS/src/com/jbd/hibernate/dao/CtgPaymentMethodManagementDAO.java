package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.ICtgPaymentMethodManagement;
import com.jbd.model.CtgPaymentMethod;


public class CtgPaymentMethodManagementDAO implements ICtgPaymentMethodManagement {


	@PersistenceContext
	public EntityManager em;

	public CtgPaymentMethodManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public CtgPaymentMethod insertCtgPaymentMethod(CtgPaymentMethod o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateCtgPaymentMethod(CtgPaymentMethod o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCtgPaymentMethod(CtgPaymentMethod o) {
		// TODO Auto-generated method stub

	}

	@Override
	public CtgPaymentMethod findCtgPaymentMethod(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
