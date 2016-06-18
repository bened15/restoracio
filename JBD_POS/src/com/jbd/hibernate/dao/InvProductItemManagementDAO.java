package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IInvProductItemManagement;
import com.jbd.model.InvProductItem;


public class InvProductItemManagementDAO implements IInvProductItemManagement {


	@PersistenceContext
	public EntityManager em;

	public InvProductItemManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public InvProductItem insertInvProductItem(InvProductItem o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateInvProductItem(InvProductItem o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteInvProductItem(InvProductItem o) {
		// TODO Auto-generated method stub

	}

	@Override
	public InvProductItem findInvProductItem(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
