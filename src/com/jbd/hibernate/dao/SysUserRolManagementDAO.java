package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.ISysUserRolManagement;
import com.jbd.model.SysUserRol;


public class SysUserRolManagementDAO implements ISysUserRolManagement {


	@PersistenceContext
	public EntityManager em;

	public SysUserRolManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public SysUserRol insertSysUserRol(SysUserRol o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateSysUserRol(SysUserRol o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSysUserRol(SysUserRol o) {
		// TODO Auto-generated method stub

	}

	@Override
	public SysUserRol findSysUserRol(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
