package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IAdmCustomerManagement;
import com.jbd.model.AdmCustomer;


public class AdmCustomerManagementDAO implements IAdmCustomerManagement {


	@PersistenceContext
	public EntityManager em;

	public AdmCustomerManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public AdmCustomer insertAdmCustomer(AdmCustomer o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateAdmCustomer(AdmCustomer o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAdmCustomer(AdmCustomer o) {
		// TODO Auto-generated method stub

	}

	@Override
	public AdmCustomer findAdmCustomer(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
