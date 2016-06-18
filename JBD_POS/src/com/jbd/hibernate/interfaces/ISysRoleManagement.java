package com.jbd.hibernate.interfaces;

import com.jbd.model.SysRole;

public interface ISysRoleManagement {

	public SysRole insertSysRole(SysRole o);
	public void updateSysRole(SysRole o);
	public void deleteSysRole(SysRole o);
	public SysRole findSysRole(Integer oId);

}
