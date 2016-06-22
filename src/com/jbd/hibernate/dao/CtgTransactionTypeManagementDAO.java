package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.ICtgTransactionTypeManagement;
import com.jbd.model.CtgTransactionType;


public class CtgTransactionTypeManagementDAO implements ICtgTransactionTypeManagement {


	@PersistenceContext
	public EntityManager em;

	public CtgTransactionTypeManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public CtgTransactionType insertCtgTransactionType(CtgTransactionType o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateCtgTransactionType(CtgTransactionType o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCtgTransactionType(CtgTransactionType o) {
		// TODO Auto-generated method stub

	}

	@Override
	public CtgTransactionType findCtgTransactionType(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
