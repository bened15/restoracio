package com.jbd.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestTableManagement;
import com.jbd.model.RestArea;
import com.jbd.model.RestTable;

public class RestTableManagementDAO implements IRestTableManagement {

	@PersistenceContext
	public EntityManager em;

	public RestTableManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestTable insertRestTable(RestTable o) {
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
	public void updateRestTable(RestTable o) {
		try {
			em.merge(o);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteRestTable(RestTable o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestTable findRestTable(Integer oId) {
		try {
			return em.find(RestTable.class, oId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<RestTable> findTablesByArea(RestArea area) {
		try {
			System.out.println(area.getAreaId() + "--Nombre primera are");
			TypedQuery<RestTable> tq = em.createQuery("select t from RestTable t where t.restArea=:areas",
					RestTable.class);
			tq.setParameter("areas", area);
			List<RestTable> tables = tq.getResultList();
			System.out.println("Tmaño de tables" + tables.size());
			// for (RestTable t : tables) {
			// System.out.println("Resultados :" + t.getTableId());
			//
			// }

			return tables;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

}
