package com.jbd.hibernate.interfaces;

import java.util.List;

import com.jbd.model.CtgMenuType;
import com.jbd.model.RestMenuItem;

public interface IRestMenuItemManagement {

	public RestMenuItem insertRestMenuItem(RestMenuItem o);

	public void updateRestMenuItem(RestMenuItem o);

	public void deleteRestMenuItem(RestMenuItem o);

	public RestMenuItem findRestMenuItem(Integer oId);

	public List<RestMenuItem> findMenuItemByTypeMenu(CtgMenuType typeMenu);

}
