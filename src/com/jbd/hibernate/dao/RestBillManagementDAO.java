package com.jbd.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestBillManagement;
import com.jbd.model.RestBill;
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

	@Transactional
	@Override
	public void deleteRestBill(RestBill o) {
		try {
			RestBill aeliminar = em.find(RestBill.class, o.getBillId());
			if (aeliminar != null) {
				em.remove(aeliminar);
				em.flush();
				em.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public RestBill findRestBill(Integer oId) {
		try {
			return em.find(RestBill.class, oId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<RestBill> findBillsWithRestTableAccount(RestTableAccount account) {
		try {

			TypedQuery<RestBill> tq = em.createQuery(
					"select b from RestBill b where b.restTableAccount=:tableAccount and b not in(select p.restBill from RestBillPayment p where p.restBill=b and p.amount=p.restBill.billTotal)",
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

	@Override
	public Double getTotalAccountFromTable(RestTableAccount ta) {
		try {
			Query q = em.createQuery(
					"select sum(b.billTotal) from RestBill b where b.restTableAccount=:ta and b.restTableAccount.closedDatetime is null",
					Double.class);
			q.setParameter("ta", ta);
			return (Double) q.getSingleResult();
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

}
