package com.jbd.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestBillManagement;
import com.jbd.model.RestBill;
import com.jbd.model.RestMenuItem;
import com.jbd.model.RestTableAccount;

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

	@Transactional
	@Override
	public void updateRestBill(RestBill o) {
		try {
			em.merge(o);
			System.out.println("Actualizado");
		} catch (Exception e) {
			e.printStackTrace();

		}

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

	@Override
	public List<RestBill> findBillsWithRestTableAccount(RestTableAccount account) {
		try {

			TypedQuery<RestBill> tq = em.createQuery("select b from RestBill b where b.restTableAccount=:tableAccount and b not in(select p.restBill from RestBillPayment p where p.restBill=b)",
					RestBill.class);
			tq.setParameter("tableAccount", account);
			List<RestBill> billItems = tq.getResultList();
			System.out.println("Tmaño de items" + billItems.size());
			// for (RestMenuItem t : menuItem) {
			// System.out.println("Resultados :" + t.getMenuItemId());
			//
			// }

			return billItems;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

}
