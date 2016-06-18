package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestInformationManagement;
import com.jbd.model.RestInformation;


public class RestInformationManagementDAO implements IRestInformationManagement {


	@PersistenceContext
	public EntityManager em;

	public RestInformationManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestInformation insertRestInformation(RestInformation o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestInformation(RestInformation o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRestInformation(RestInformation o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestInformation findRestInformation(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
