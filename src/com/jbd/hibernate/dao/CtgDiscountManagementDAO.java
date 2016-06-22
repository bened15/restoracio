package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.ICtgDiscountManagement;
import com.jbd.model.CtgDiscount;


public class CtgDiscountManagementDAO implements ICtgDiscountManagement {


	@PersistenceContext
	public EntityManager em;

	public CtgDiscountManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public CtgDiscount insertCtgDiscount(CtgDiscount o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateCtgDiscount(CtgDiscount o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCtgDiscount(CtgDiscount o) {
		// TODO Auto-generated method stub

	}

	@Override
	public CtgDiscount findCtgDiscount(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
