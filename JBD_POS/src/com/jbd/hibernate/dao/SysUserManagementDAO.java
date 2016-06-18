package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.ISysUserManagement;
import com.jbd.model.SysUser;


public class SysUserManagementDAO implements ISysUserManagement {


	@PersistenceContext
	public EntityManager em;

	public SysUserManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public SysUser insertSysUser(SysUser o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateSysUser(SysUser o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSysUser(SysUser o) {
		// TODO Auto-generated method stub

	}

	@Override
	public SysUser findSysUser(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

}
