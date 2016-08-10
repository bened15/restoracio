package com.jbd.hibernate.interfaces;

import com.jbd.model.AdmReservation;

public interface IAdmReservationManagement {

	public AdmReservation insertAdmReservation(AdmReservation o);
	public void updateAdmReservation(AdmReservation o);
	public void deleteAdmReservation(AdmReservation o);
	public AdmReservation findAdmReservation(Integer oId);

}
