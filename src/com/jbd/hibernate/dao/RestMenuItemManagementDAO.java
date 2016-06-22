package com.jbd.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestMenuItemManagement;
import com.jbd.model.CtgMenuType;
import com.jbd.model.RestMenuItem;
import com.jbd.model.RestTable;

public class RestMenuItemManagementDAO implements IRestMenuItemManagement {

	@PersistenceContext
	public EntityManager em;

	public RestMenuItemManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestMenuItem insertRestMenuItem(RestMenuItem o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestMenuItem(RestMenuItem o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRestMenuItem(RestMenuItem o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestMenuItem findRestMenuItem(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public List<RestMenuItem> findMenuItemByTypeMenu(CtgMenuType typeMenu) {
		try {

			TypedQuery<RestMenuItem> tq = em.createQuery("select t from RestMenuItem t where t.ctgMenuType=:menuType",
					RestMenuItem.class);
			tq.setParameter("menuType", typeMenu);
			List<RestMenuItem> menuItem = tq.getResultList();
			System.out.println("Tmaño de items" + menuItem.size());
			for (RestMenuItem t : menuItem) {
				System.out.println("Resultados :" + t.getMenuItemId());

			}

			return menuItem;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

}
