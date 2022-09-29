package com.cognixia.jump.jdbc.model;

import java.io.Serializable;

public class Purchases implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long purchase_id;
	private long invoice_no;
	private String item;
	private String item_code;
	private int item_price;

	public Purchases(long purchase_id, long invoice_no, String item, String item_code, int item_price) {
		super();
		this.purchase_id = purchase_id;
		this.invoice_no = invoice_no;
		this.item = item;
		this.item_code = item_code;
		this.item_price = item_price;
	}

	public Purchases() {
		// TODO Auto-generated constructor stub
	}

	public long getPurchase_id() {
		return purchase_id;
	}

	public void setPurchase_id(long purchase_id) {
		this.purchase_id = purchase_id;
	}

	public long getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(long invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public int getItem_price() {
		return item_price;
	}

	public void setItem_price(int item_price) {
		this.item_price = item_price;
	}

	@Override
	public String toString() {
		return "purchases [purchase_id=" + purchase_id + ", invoice_no=" + invoice_no + ", item=" + item
				+ ", item_code=" + item_code + ", item_price=" + item_price + "]";
	}

}
