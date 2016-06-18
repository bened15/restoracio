package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestShiftManagement;
import com.jbd.model.RestShift;


public class RestShiftManagementDAO implements IRestShiftManagement {


	@PersistenceContext
	public EntityManager em;

	public RestShiftManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestShift insertRestShift(RestShift o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestShift(RestShift o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRestShift(RestShift o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestShift findRestShift(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
