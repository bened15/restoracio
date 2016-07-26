package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rest_menu_item database table.
 *
 */
@Entity
@Table(name="rest_menu_item")
@NamedQuery(name="RestMenuItem.findAll", query="SELECT r FROM RestMenuItem r")
public class RestMenuItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="MENU_ITEM_ID")
	private int menuItemId;

	private int available;

	@Column(name="MENU_ITEM_DESCRIPTION")
	private String menuItemDescription;

	@Column(name="MENU_ITEM_NAME")
	private String menuItemName;

	@Column(name="MENU_ITEM_PRICE")
	private float menuItemPrice;

	@Column(name="MENU_IMAGE")
	private byte[] menuImage;

	//bi-directional many-to-one association to CtgMenuType
	@ManyToOne
	@JoinColumn(name="MENU_TYPE_ID")
	private CtgMenuType ctgMenuType;

	//bi-directional many-to-one association to RestMenuItemProduct
	@OneToMany(mappedBy="restMenuItem")
	private List<RestMenuItemProduct> restMenuItemProducts;

	//bi-directional many-to-one association to RestMenuItemProduct
	@OneToMany(mappedBy="restMenuItem")
	private List<RestMenuItemComment> restMenuItemComments;

	//bi-directional many-to-one association to RestOrder
	@OneToMany(mappedBy="restMenuItem")
	private List<RestOrder> restOrders;

	//bi-directional many-to-one association to CtgMenuType
	@ManyToOne
	@JoinColumn(name="KITCHEN_ID")
	private RestKitchen restKitchen;

	public RestMenuItem() {
	}

	public int getMenuItemId() {
		return this.menuItemId;
	}

	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}

	public int getAvailable() {
		return this.available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public String getMenuItemDescription() {
		return this.menuItemDescription;
	}

	public void setMenuItemDescription(String menuItemDescription) {
		this.menuItemDescription = menuItemDescription;
	}

	public String getMenuItemName() {
		return this.menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public float getMenuItemPrice() {
		return this.menuItemPrice;
	}

	public void setMenuItemPrice(float menuItemPrice) {
		this.menuItemPrice = menuItemPrice;
	}

	
	public byte[] getMenuImage() {
		return menuImage;
	}

	public void setMenuImage(byte[] menuImage) {
		this.menuImage = menuImage;
	}

	public CtgMenuType getCtgMenuType() {
		return this.ctgMenuType;
	}

	public void setCtgMenuType(CtgMenuType ctgMenuType) {
		this.ctgMenuType = ctgMenuType;
	}

	public List<RestMenuItemProduct> getRestMenuItemProducts() {
		return this.restMenuItemProducts;
	}

	public void setRestMenuItemProducts(List<RestMenuItemProduct> restMenuItemProducts) {
		this.restMenuItemProducts = restMenuItemProducts;
	}

	public RestMenuItemProduct addRestMenuItemProduct(RestMenuItemProduct restMenuItemProduct) {
		getRestMenuItemProducts().add(restMenuItemProduct);
		restMenuItemProduct.setRestMenuItem(this);

		return restMenuItemProduct;
	}

	public RestMenuItemProduct removeRestMenuItemProduct(RestMenuItemProduct restMenuItemProduct) {
		getRestMenuItemProducts().remove(restMenuItemProduct);
		restMenuItemProduct.setRestMenuItem(null);

		return restMenuItemProduct;
	}

	public List<RestOrder> getRestOrders() {
		return this.restOrders;
	}

	public void setRestOrders(List<RestOrder> restOrders) {
		this.restOrders = restOrders;
	}

	public RestOrder addRestOrder(RestOrder restOrder) {
		getRestOrders().add(restOrder);
		restOrder.setRestMenuItem(this);

		return restOrder;
	}

	public RestOrder removeRestOrder(RestOrder restOrder) {
		getRestOrders().remove(restOrder);
		restOrder.setRestMenuItem(null);

		return restOrder;
	}

	

	
	public RestKitchen getRestKitchen() {
		return restKitchen;
	}

	public void setRestKitchen(RestKitchen restKitchen) {
		this.restKitchen = restKitchen;
	}


	public List<RestMenuItemComment> getRestMenuItemComments() {
		return this.restMenuItemComments;
	}

	public void setRestMenuItemComments(List<RestMenuItemComment> restMenuItemComments) {
		this.restMenuItemComments = restMenuItemComments;
	}

	public RestMenuItemComment addRestMenuItemProduct(RestMenuItemComment restMenuItemComment) {
		getRestMenuItemComments().add(restMenuItemComment);
		restMenuItemComment.setRestMenuItem(this);

		return restMenuItemComment;
	}

	public RestMenuItemComment removeRestMenuItemComment(RestMenuItemComment restMenuItemComment) {
		getRestMenuItemComments().remove(restMenuItemComment);
		restMenuItemComment.setRestMenuItem(null);

		return restMenuItemComment;
	}

	

	@Transient
	private String menuItemTypeText;

	public String getMenuItemTypeText() {
		return menuItemTypeText;
	}

	public void setMenuItemTypeText(String menuItemTypeName) {
		this.menuItemTypeText = menuItemTypeName;
	}
	
	
}