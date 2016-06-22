package com.jbd.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestMenuItemProductManagement;
import com.jbd.model.RestMenuItem;
import com.jbd.model.RestMenuItemProduct;

public class RestMenuItemProductManagementDAO implements IRestMenuItemProductManagement {

	@PersistenceContext
	public EntityManager em;

	public RestMenuItemProductManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestMenuItemProduct insertRestMenuItemProduct(RestMenuItemProduct o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestMenuItemProduct(RestMenuItemProduct o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRestMenuItemProduct(RestMenuItemProduct o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestMenuItemProduct findRestMenuItemProduct(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public List<RestMenuItemProduct> findIngredientes(RestMenuItem menuItem) {
		try {
			TypedQuery<RestMenuItemProduct> q = em.createQuery(
					"select i from RestMenuItemProduct i where i.restMenuItem=:menuItem ", RestMenuItemProduct.class);
			q.setParameter("menuItem", menuItem);
			List<RestMenuItemProduct> ingredientes = q.getResultList();
			return ingredientes;

		} catch (Exception e) {
			e.printStackTrace();
			List<RestMenuItemProduct> ingredientes = null;
			return ingredientes;
		}

	}

}
