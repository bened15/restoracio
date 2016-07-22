package com.jbd.hibernate.interfaces;

import com.jbd.model.SysUserRol;

public interface ISysUserRolManagement {

	public SysUserRol insertSysUserRol(SysUserRol o);

	public SysUserRol updateSysUserRol(SysUserRol o);

	public void deleteSysUserRol(SysUserRol o);

	public boolean findSysUserRol(String uName, String uPass);
	public boolean findWaitress(String uName);


}
