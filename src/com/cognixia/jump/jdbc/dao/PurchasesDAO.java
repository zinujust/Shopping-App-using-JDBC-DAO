package com.cognixia.jump.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.jdbc.connection.ConnectionManager;
import com.cognixia.jump.jdbc.model.Purchases;

public class PurchasesDAO implements DAO<Purchases>{

	Connection conn = ConnectionManager.getConnection();
	
	@Override
	public Purchases findById(long id) {
		return null;
	}

	public List<Purchases> findUserPurchases(long id) {
		
		PreparedStatement prep = null;
		String query = null;
		List<Purchases> items = new ArrayList<>();
		
		try {
			
			query = "select * from item_purchased where invoice_no = ?";
			
			prep = conn.prepareStatement(query);
			
			prep.setLong(1, id);
			
			ResultSet rs = prep.executeQuery();
			
			while(rs.next() && rs != null) {
				Purchases item = new Purchases();
				item.setPurchase_id(rs.getLong(1));
				item.setInvoice_no(rs.getLong(2));
				item.setItem(rs.getString(3));
				item.setItem_code(rs.getString(4));
				item.setItem_price(rs.getInt(5));
				
				items.add(item);
			}
			
		} catch (Exception e) {
			System.err.println("Error in PurchasesDAO.findUserPurchases()");		
		}
		
		return items;
	}

	@Override
	public boolean update(Purchases entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Purchases entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(Purchases entity) {
		PreparedStatement prep = null;
		String query = null;
		boolean created = false;
		
		try {
			
			query = "insert into item_purchased(invoice_no, item, item_code, item_price) values (?, ?, ?, ?)";
			prep = conn.prepareStatement(query);
			
			prep.setLong(1, entity.getInvoice_no());
			prep.setString(2, entity.getItem());
			prep.setString(3, entity.getItem_code());
			prep.setInt(4, entity.getItem_price());
			
			created = prep.execute();
			
			if(created == true) {
				System.out.println("Item added..");
				return true;
			}
			
			
		} catch (Exception e) {
			System.err.println("Failed to add item");
		}
		
		
		return false;
	}

	public void removeItem(long number, String code) {
		PreparedStatement prep = null;
		String query = null;
		long id = 0;
		ResultSet rs = null;
		
		
		try {
			
			query = "select invoice_date from invoice where invoice_no = ?";
			
			prep = conn.prepareStatement(query);
			
			prep.setLong(1, number);
			
			rs = prep.executeQuery();
			
			Date date = null;
			
			if(rs != null) {
				rs.next();
				
				date = rs.getDate(1);
			}
			
			
			LocalDate now = LocalDate.now();
			
			Period period = Period.between(date.toLocalDate(), now);
			
			if(period.getDays() >= 15) {
				System.out.println("\tInvoice has exceeded 15 days return time. \n\tItem was not removed..");
			}
			else {
				
				query = "select purchase_id from item_purchased where invoice_no = ? and item_code = ? limit 1";
				
				prep = conn.prepareStatement(query);
				
				prep.setLong(1, number);
				prep.setString(2, code);
				
				rs = prep.executeQuery();
				
				if(rs != null) {
					rs.next();
					id = rs.getLong(1);
				}
				
				query = null;
				rs = null;
				prep = null;
				
				query = "delete from item_purchased where invoice_no = ? and purchase_id = ?";
				
				prep = conn.prepareStatement(query);
				
				prep.setLong(1, number);
				prep.setLong(2, id);
				
				boolean delete = prep.execute();
				
				if(delete == true) {
					System.out.println("Item code " + code + " was removed");
				}
				
			}
			
		} catch (Exception e) {
			System.err.println("Item could not be removed. Try again!");
		}
	}

}
