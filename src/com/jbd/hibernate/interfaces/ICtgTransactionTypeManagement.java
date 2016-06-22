package com.jbd.hibernate.interfaces;

import com.jbd.model.CtgTransactionType;

public interface ICtgTransactionTypeManagement {

	public CtgTransactionType insertCtgTransactionType(CtgTransactionType o);
	public void updateCtgTransactionType(CtgTransactionType o);
	public void deleteCtgTransactionType(CtgTransactionType o);
	public CtgTransactionType findCtgTransactionType(Integer oId);

}
