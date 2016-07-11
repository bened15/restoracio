package com.jbd.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestBillDetailManagement;
import com.jbd.model.RestBill;
import com.jbd.model.RestBillDetail;
import com.jbd.model.RestOrder;
import com.jbd.model.RestTableAccount;

public class RestBillDetailManagementDAO implements IRestBillDetailManagement {

	@PersistenceContext
	public EntityManager em;

	public RestBillDetailManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestBillDetail insertRestBillDetail(RestBillDetail o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestBillDetail(RestBillDetail o) {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public void deleteRestBillDetail(RestBillDetail o) {
		try {
			RestBillDetail aeliminar = em.find(RestBillDetail.class, o.getBillDetailId());
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
	public RestBillDetail findRestBillDetail(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public List<RestBillDetail> findAllRestBillDetailFromTableAccount(RestTableAccount Taccount) {
		try {
			TypedQuery<RestBillDetail> tq = em.createQuery(
					"select d from RestBillDetail d where d.restBill.restTableAccount=:Taccount", RestBillDetail.class);
			tq.setParameter("Taccount", Taccount);
			List<RestBillDetail> billDetail = tq.getResultList();
			return billDetail;

		} catch (Exception e) {
			e.printStackTrace();
			List<RestBillDetail> billDetail = null;
			return billDetail;

		}
	}

	@Override
	public List<RestBillDetail> findAllRestBillDetailFromRestBill(RestBill bill) {
		try {
			TypedQuery<RestBillDetail> tq = em.createQuery("select d from RestBillDetail d where d.restBill=:bill",
					RestBillDetail.class);
			tq.setParameter("bill", bill);
			List<RestBillDetail> billDetail = tq.getResultList();
			return billDetail;

		} catch (Exception e) {
			e.printStackTrace();
			List<RestBillDetail> billDetail = null;
			return billDetail;

		}
	}

}
