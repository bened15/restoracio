package com.jbd.hibernate.interfaces;

import com.jbd.model.SysUserRol;

public interface ISysUserRolManagement {

	public SysUserRol insertSysUserRol(SysUserRol o);
<<<<<<< HEAD

	public void updateSysUserRol(SysUserRol o);

=======
	public SysUserRol updateSysUserRol(SysUserRol o);
>>>>>>> 980f17d13477260c4d2b155862ba4cf6d1b960f6
	public void deleteSysUserRol(SysUserRol o);

	public boolean findSysUserRol(String uName, String uPass);

}
