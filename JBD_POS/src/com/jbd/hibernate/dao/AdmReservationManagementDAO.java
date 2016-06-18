package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IAdmReservationManagement;
import com.jbd.model.AdmReservation;


public class AdmReservationManagementDAO implements IAdmReservationManagement {


	@PersistenceContext
	public EntityManager em;

	public AdmReservationManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public AdmReservation insertAdmReservation(AdmReservation o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateAdmReservation(AdmReservation o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAdmReservation(AdmReservation o) {
		// TODO Auto-generated method stub

	}

	@Override
	public AdmReservation findAdmReservation(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
