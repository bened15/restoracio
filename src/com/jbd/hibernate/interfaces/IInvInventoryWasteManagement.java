package com.jbd.hibernate.interfaces;

import com.jbd.model.InvInventoryWaste;

public interface IInvInventoryWasteManagement {

	public InvInventoryWaste insertInvInventoryWaste(InvInventoryWaste o);
	public void updateInvInventoryWaste(InvInventoryWaste o);
	public void deleteInvInventoryWaste(InvInventoryWaste o);
	public InvInventoryWaste findInvInventoryWaste(Integer oId);

}
