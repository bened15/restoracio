package com.jbd.hibernate.interfaces;

import com.jbd.model.SysUser;

public interface ISysUserManagement {

	public SysUser insertSysUser(SysUser o);
	public void updateSysUser(SysUser o);
	public void deleteSysUser(SysUser o);
	public SysUser findSysUser(Integer oId);

}
