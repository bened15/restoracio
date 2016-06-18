package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestBillDetailManagement;
import com.jbd.model.RestBillDetail;


public class RestBillDetailManagementDAO implements IRestBillDetailManagement {


	@PersistenceContext
	public EntityManager em;

	public RestBillDetailManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestBillDetail insertRestBillDetail(RestBillDetail o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestBillDetail(RestBillDetail o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRestBillDetail(RestBillDetail o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestBillDetail findRestBillDetail(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
