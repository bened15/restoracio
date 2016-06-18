package com.jbd.hibernate.interfaces;

import com.jbd.model.InvProductItem;

public interface IInvProductItemManagement {

	public InvProductItem insertInvProductItem(InvProductItem o);
	public void updateInvProductItem(InvProductItem o);
	public void deleteInvProductItem(InvProductItem o);
	public InvProductItem findInvProductItem(Integer oId);

}
