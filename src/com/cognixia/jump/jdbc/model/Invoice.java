package com.cognixia.jump.jdbc.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Invoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long invoice_no;
	private LocalDate date;
	private LocalTime time;
	private long customer_id;

	public Invoice(long invoice_no, LocalDate date, LocalTime time, long customer_id) {
		super();
		this.invoice_no = invoice_no;
		this.date = date;
		this.time = time;
		this.customer_id = customer_id;
	}

	public Invoice() {
		this(-1, LocalDate.now(), LocalTime.now(), -1);
	}

	public long getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(long invoice_no) {
		this.invoice_no = invoice_no;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}

	@Override
	public String toString() {
		return "invoice [invoice_no=" + invoice_no + ", date=" + date + ", time=" + time + ", customer_id="
				+ customer_id + "]";
	}

}
