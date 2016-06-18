package com.jbd.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestOrderManagement;
import com.jbd.model.RestOrder;
import com.jbd.model.RestTableAccount;

public class RestOrderManagementDAO implements IRestOrderManagement {

	@PersistenceContext
	public EntityManager em;

	public RestOrderManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestOrder insertRestOrder(RestOrder o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestOrder(RestOrder o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRestOrder(RestOrder o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestOrder findRestOrder(Integer oId) {
		return null;

	}
	@Override
	public List<RestOrder> findAllRestOrdersFromTableAccount(RestTableAccount account) {
		try {
			TypedQuery<RestOrder> tq = em.createQuery("select o from RestOrder o where o.restTableAccount=:account",
					RestOrder.class);
			tq.setParameter("account", account);
			List<RestOrder> ordenes = tq.getResultList();
			return ordenes;

		} catch (Exception e) {
			e.printStackTrace();
			List<RestOrder> ordenes = null;
			return ordenes;

		}

	}

}
