package com.jbd.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestOrderDetailLessManagement;
import com.jbd.model.RestOrderDetailLess;
import com.jbd.model.RestTableAccount;

public class RestOrderDetailLessManagementDAO implements IRestOrderDetailLessManagement {

	@PersistenceContext
	public EntityManager em;

	public RestOrderDetailLessManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestOrderDetailLess insertRestOrderDetailLess(RestOrderDetailLess o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestOrderDetailLess(RestOrderDetailLess o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRestOrderDetailLess(RestOrderDetailLess o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestOrderDetailLess findRestOrderDetailLess(Integer oId) {
		return null;

	}
	@Override
	public List<RestOrderDetailLess> findAllRestOrderDetailLesssFromTableAccount(RestTableAccount account) {
		try {
			TypedQuery<RestOrderDetailLess> tq = em.createQuery("select o from RestOrderDetailLess o where o.restTableAccount=:account",
					RestOrderDetailLess.class);
			tq.setParameter("account", account);
			List<RestOrderDetailLess> ordenes = tq.getResultList();
			return ordenes;

		} catch (Exception e) {
			e.printStackTrace();
			List<RestOrderDetailLess> ordenes = null;
			return ordenes;

		}

	}

}
