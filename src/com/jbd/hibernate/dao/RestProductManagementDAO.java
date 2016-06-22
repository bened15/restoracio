package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestProductManagement;
import com.jbd.model.RestProduct;

public class RestProductManagementDAO implements IRestProductManagement {

	@PersistenceContext
	public EntityManager em;

	public RestProductManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestProduct insertRestProduct(RestProduct o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestProduct(RestProduct o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRestProduct(RestProduct o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestProduct findRestProduct(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
