package org.unibl.etf.shop.dto;

import java.io.Serializable;

public class DTOItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6910415191947741998L;
	private String name, description;
	private int idItem;
	private double price;

	public DTOItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DTOItem(int idItem, String name, String description, double price) {
		super();
		this.name = name;
		this.description = description;
		this.idItem = idItem;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getIdItem() {
		return idItem;
	}

	public double getPrice() {
		return price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "DTOItem [name=" + name + ", description=" + description + ", idItem=" + idItem + ", price=" + price
				+ "]";
	}


}
