package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ctg_measure_unit database table.
 *
 */
@Entity
@Table(name="ctg_measure_unit")
@NamedQuery(name="CtgMeasureUnit.findAll", query="SELECT c FROM CtgMeasureUnit c")
public class CtgMeasureUnit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="MEASURE_UNIT_ID")
	private int measureUnitId;

	@Temporal(TemporalType.DATE)
	@Column(name="ENTRY_DATE")
	private Date entryDate;

	@Column(name="ENTRY_USER")
	private String entryUser;

	@Column(name="MEASURE_NAME")
	private String measureName;

	@Column(name="MEASURE_UNI")
	private float measureUni;

	//bi-directional many-to-one association to CtgProduct
	@OneToMany(mappedBy="ctgMeasureUnit")
	private List<CtgProduct> ctgProducts;

	//bi-directional many-to-one association to RestMenuItemProduct
	@OneToMany(mappedBy="ctgMeasureUnit")
	private List<RestMenuItemProduct> restMenuItemProducts;

	public CtgMeasureUnit() {
	}

	public int getMeasureUnitId() {
		return this.measureUnitId;
	}

	public void setMeasureUnitId(int measureUnitId) {
		this.measureUnitId = measureUnitId;
	}

	public Date getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryUser() {
		return this.entryUser;
	}

	public void setEntryUser(String entryUser) {
		this.entryUser = entryUser;
	}

	public String getMeasureName() {
		return this.measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}

	public float getMeasureUni() {
		return this.measureUni;
	}

	public void setMeasureUni(float measureUni) {
		this.measureUni = measureUni;
	}

	public List<CtgProduct> getCtgProducts() {
		return this.ctgProducts;
	}

	public void setCtgProducts(List<CtgProduct> ctgProducts) {
		this.ctgProducts = ctgProducts;
	}

	public CtgProduct addCtgProduct(CtgProduct ctgProduct) {
		getCtgProducts().add(ctgProduct);
		ctgProduct.setCtgMeasureUnit(this);

		return ctgProduct;
	}

	public CtgProduct removeCtgProduct(CtgProduct ctgProduct) {
		getCtgProducts().remove(ctgProduct);
		ctgProduct.setCtgMeasureUnit(null);

		return ctgProduct;
	}

	public List<RestMenuItemProduct> getRestMenuItemProducts() {
		return this.restMenuItemProducts;
	}

	public void setRestMenuItemProducts(List<RestMenuItemProduct> restMenuItemProducts) {
		this.restMenuItemProducts = restMenuItemProducts;
	}

	public RestMenuItemProduct addRestMenuItemProduct(RestMenuItemProduct restMenuItemProduct) {
		getRestMenuItemProducts().add(restMenuItemProduct);
		restMenuItemProduct.setCtgMeasureUnit(this);

		return restMenuItemProduct;
	}

	public RestMenuItemProduct removeRestMenuItemProduct(RestMenuItemProduct restMenuItemProduct) {
		getRestMenuItemProducts().remove(restMenuItemProduct);
		restMenuItemProduct.setCtgMeasureUnit(null);

		return restMenuItemProduct;
	}

}