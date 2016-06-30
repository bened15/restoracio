package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ctg_menu_type database table.
 *
 */
@Entity
@Table(name="ctg_menu_type")
@NamedQuery(name="CtgMenuType.findAll", query="SELECT c FROM CtgMenuType c")
public class CtgMenuType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MENU_TYPE_ID")
	private int menuTypeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ENTRY_DATE")
	private Date entryDate;

	@Column(name="ENTRY_USER")
	private String entryUser;

	@Column(name="MENU_TYPE_DESCRIPTION")
	private String menuTypeDescription;

	@Column(name="MENU_TYPE_NAME")
	private String menuTypeName;

	//bi-directional many-to-one association to CtgDiscount
	@OneToMany(mappedBy="ctgMenuType")
	private List<CtgDiscount> ctgDiscounts;

	//bi-directional many-to-one association to RestMenuItem
	@OneToMany(mappedBy="ctgMenuType")
	private List<RestMenuItem> restMenuItems;

	public CtgMenuType() {
	}

	public int getMenuTypeId() {
		return this.menuTypeId;
	}

	public void setMenuTypeId(int menuTypeId) {
		this.menuTypeId = menuTypeId;
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

	public String getMenuTypeDescription() {
		return this.menuTypeDescription;
	}

	public void setMenuTypeDescription(String menuTypeDescription) {
		this.menuTypeDescription = menuTypeDescription;
	}

	public String getMenuTypeName() {
		return this.menuTypeName;
	}

	public void setMenuTypeName(String menuTypeName) {
		this.menuTypeName = menuTypeName;
	}

	public List<CtgDiscount> getCtgDiscounts() {
		return this.ctgDiscounts;
	}

	public void setCtgDiscounts(List<CtgDiscount> ctgDiscounts) {
		this.ctgDiscounts = ctgDiscounts;
	}

	public CtgDiscount addCtgDiscount(CtgDiscount ctgDiscount) {
		getCtgDiscounts().add(ctgDiscount);
		ctgDiscount.setCtgMenuType(this);

		return ctgDiscount;
	}

	public CtgDiscount removeCtgDiscount(CtgDiscount ctgDiscount) {
		getCtgDiscounts().remove(ctgDiscount);
		ctgDiscount.setCtgMenuType(null);

		return ctgDiscount;
	}

	public List<RestMenuItem> getRestMenuItems() {
		return this.restMenuItems;
	}

	public void setRestMenuItems(List<RestMenuItem> restMenuItems) {
		this.restMenuItems = restMenuItems;
	}

	public RestMenuItem addRestMenuItem(RestMenuItem restMenuItem) {
		getRestMenuItems().add(restMenuItem);
		restMenuItem.setCtgMenuType(this);

		return restMenuItem;
	}

	public RestMenuItem removeRestMenuItem(RestMenuItem restMenuItem) {
		getRestMenuItems().remove(restMenuItem);
		restMenuItem.setCtgMenuType(null);

		return restMenuItem;
	}

}