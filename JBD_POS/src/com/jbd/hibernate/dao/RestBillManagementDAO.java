package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestBillManagement;
import com.jbd.model.RestBill;


public class RestBillManagementDAO implements IRestBillManagement {


	@PersistenceContext
	public EntityManager em;

	public RestBillManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestBill insertRestBill(RestBill o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestBill(RestBill o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRestBill(RestBill o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestBill findRestBill(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
