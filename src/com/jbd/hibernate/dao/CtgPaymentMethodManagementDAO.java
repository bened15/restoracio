package com.jbd.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.ICtgPaymentMethodManagement;
import com.jbd.model.CtgPaymentMethod;
import com.jbd.model.RestMenuItem;

public class CtgPaymentMethodManagementDAO implements ICtgPaymentMethodManagement {

	@PersistenceContext
	public EntityManager em;

	public CtgPaymentMethodManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public CtgPaymentMethod insertCtgPaymentMethod(CtgPaymentMethod o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateCtgPaymentMethod(CtgPaymentMethod o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCtgPaymentMethod(CtgPaymentMethod o) {
		// TODO Auto-generated method stub

	}

	@Override
	public CtgPaymentMethod findCtgPaymentMethod(String name) {
		try {

			TypedQuery<CtgPaymentMethod> tq = em.createQuery(
					"select p from CtgPaymentMethod p where UPPER(p.name) like '%" + name.toUpperCase() + "%'",
					CtgPaymentMethod.class);

			List<CtgPaymentMethod> payment = tq.getResultList();
			// System.out.println("Tmaño de items" + menuItem.size());
			for (CtgPaymentMethod t : payment) {
				System.out.println("Resultados :" + t.getName());

			}

			return payment.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
		// TODO Auto-generated method stub

	}

}
