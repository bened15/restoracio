package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.ISysRoleManagement;
import com.jbd.model.SysRole;


public class SysRoleManagementDAO implements ISysRoleManagement {


	@PersistenceContext
	public EntityManager em;

	public SysRoleManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public SysRole insertSysRole(SysRole o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateSysRole(SysRole o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSysRole(SysRole o) {
		// TODO Auto-generated method stub

	}

	@Override
	public SysRole findSysRole(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
