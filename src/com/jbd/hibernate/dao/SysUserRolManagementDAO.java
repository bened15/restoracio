package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	@Transactional
	@Override
	public SysUserRol updateSysUserRol(SysUserRol o) {
		try {
			em.merge(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSysUserRol(SysUserRol o) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean findSysUserRol(String uName, String uPass) {

		Query q = em.createQuery(
				"SELECT ur from SysUserRol ur where ur.sysUser.userName=:uName and ur.sysUser.userPassword=:uPass",
				SysUserRol.class);

		q.setParameter("uName", uName);
		q.setParameter("uPass", uPass);
		try {
			q.getSingleResult();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
