package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestMenuItemTypeManagement;
import com.jbd.model.RestMenuItemType;


public class RestMenuItemTypeManagementDAO implements IRestMenuItemTypeManagement {


	@PersistenceContext
	public EntityManager em;

	public RestMenuItemTypeManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestMenuItemType insertRestMenuItemType(RestMenuItemType o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestMenuItemType(RestMenuItemType o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRestMenuItemType(RestMenuItemType o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestMenuItemType findRestMenuItemType(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
