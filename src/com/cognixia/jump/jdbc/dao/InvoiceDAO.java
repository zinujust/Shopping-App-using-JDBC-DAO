package com.cognixia.jump.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.jdbc.connection.ConnectionManager;
import com.cognixia.jump.jdbc.model.Invoice;

public class InvoiceDAO implements DAO<Invoice> {

	Connection conn = ConnectionManager.getConnection();

	@Override
	public Invoice findById(long id) {
		PreparedStatement prep = null;
		String query = null;
		Invoice inv = new Invoice();

		try {

			query = "select * from invoice where invoice_no = ?";

			prep = conn.prepareStatement(query);

			prep.setLong(1, id);

			ResultSet rs = prep.executeQuery();

			if (rs != null) {
				rs.next();
				inv.setInvoice_no(rs.getLong(1));

				LocalDate sqlDate = rs.getDate(2).toLocalDate();
				LocalTime sqlTime = rs.getTime(3).toLocalTime();

				inv.setDate(sqlDate);
				inv.setTime(sqlTime);

				inv.setCustomer_id(rs.getLong(4));

			}

		} catch (Exception e) {
			System.err.println("Error in InvoiceDAO.findById()");
		}

		return inv;
	}

	public List<Invoice> findUserPurchases(long id) {

		PreparedStatement prep = null;
		String query = null;
		List<Invoice> invoices = new ArrayList<>();

		try {

			query = "SELECT * from invoice where customer_id = ? order by invoice_date asc, invoice_time asc";

			prep = conn.prepareStatement(query);

			prep.setLong(1, id);

			ResultSet rs = prep.executeQuery();

			while (rs.next() && rs != null) {

				Invoice inv = new Invoice();
				inv.setInvoice_no(rs.getLong(1));

				LocalDate sqlDate = rs.getDate(2).toLocalDate();
				LocalTime sqlTime = rs.getTime(3).toLocalTime();

				inv.setDate(sqlDate);
				inv.setTime(sqlTime);

				inv.setCustomer_id(rs.getLong(4));

				invoices.add(inv);
			}

		} catch (Exception e) {
			System.err.println("Error in InvoiceDAO.findUserPurchase()");
		}

		return invoices;
	}

	@Override
	public boolean update(Invoice entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Invoice entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(Invoice entity) {

		PreparedStatement prep = null;
		String query = null;
		int created = 0;

		try {
			query = "Insert into invoice(invoice_date, invoice_time, customer_id) VALUES (?, ?, ?)";
			prep = conn.prepareStatement(query);

			Date date = Date.valueOf(entity.getDate());
			Time time = Time.valueOf(entity.getTime());

			prep.setDate(1, date);
			prep.setTime(2, time);
			prep.setLong(3, entity.getCustomer_id());

			created = prep.executeUpdate();

			if (created == 1) {
				System.out.println("Invoice created");
				return true;
			}

		} catch (Exception e) {
			System.err.println("Error in InvoiceDAO.create()");
		}

		return false;
	}

	public long returnInvoiceNumber(long id) {
		PreparedStatement prep = null;
		String query = null;
		long invoice_no = 0;

		try {

			query = "select invoice_no from invoice where customer_id = ? ORDER BY invoice_date DESC, invoice_time DESC LIMIT 1";

			prep = conn.prepareStatement(query);

			prep.setLong(1, id);

			ResultSet rs = prep.executeQuery();

			if (rs != null) {
				rs.next();
				invoice_no = rs.getLong(1);
			}

		} catch (Exception e) {
			System.err.println("Invoice not found!");
		}

		return invoice_no;
	}

}
