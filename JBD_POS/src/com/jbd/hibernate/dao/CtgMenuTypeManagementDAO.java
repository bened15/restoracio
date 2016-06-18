package com.jbd.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.ICtgMenuTypeManagement;
import com.jbd.model.CtgMenuType;
import com.jbd.model.RestTable;

public class CtgMenuTypeManagementDAO implements ICtgMenuTypeManagement {

	@PersistenceContext
	public EntityManager em;

	public CtgMenuTypeManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public CtgMenuType insertCtgMenuType(CtgMenuType o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateCtgMenuType(CtgMenuType o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCtgMenuType(CtgMenuType o) {
		// TODO Auto-generated method stub

	}

	@Override
	public CtgMenuType findCtgMenuType(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public List<CtgMenuType> loadMenuType() {
		try {

			TypedQuery<CtgMenuType> tq = em.createNamedQuery("CtgMenuType.findAll", CtgMenuType.class);

			List<CtgMenuType> mtype = tq.getResultList();
//			System.out.println("Tmaño de menutype" + mtype.size());
//			for (CtgMenuType t : mtype) {
//				System.out.println("Resultados :" + t.getMenuTypeId());

//			}

			return mtype;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

}
