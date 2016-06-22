package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the inv_inventory_waste database table.
 *
 */
@Entity
@Table(name="inv_inventory_waste")
@NamedQuery(name="InvInventoryWaste.findAll", query="SELECT i FROM InvInventoryWaste i")
public class InvInventoryWaste implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_INVENTORY_WASTE")
	private int idInventoryWaste;

	@Column(name="ID_MEASURE_TYPE")
	private int idMeasureType;

	public InvInventoryWaste() {
	}

	public int getIdInventoryWaste() {
		return this.idInventoryWaste;
	}

	public void setIdInventoryWaste(int idInventoryWaste) {
		this.idInventoryWaste = idInventoryWaste;
	}

	public int getIdMeasureType() {
		return this.idMeasureType;
	}

	public void setIdMeasureType(int idMeasureType) {
		this.idMeasureType = idMeasureType;
	}

}