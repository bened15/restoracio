package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestBillPaymentManagement;
import com.jbd.model.RestBillPayment;


public class RestBillPaymentManagementDAO implements IRestBillPaymentManagement {


	@PersistenceContext
	public EntityManager em;

	public RestBillPaymentManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestBillPayment insertRestBillPayment(RestBillPayment o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestBillPayment(RestBillPayment o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRestBillPayment(RestBillPayment o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestBillPayment findRestBillPayment(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
