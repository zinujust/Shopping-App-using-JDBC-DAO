package com.cognixia.jump.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cognixia.jump.jdbc.connection.ConnectionManager;
import com.cognixia.jump.jdbc.main.Animate;
import com.cognixia.jump.jdbc.model.Customer;

public class CustomerDAO implements DAO<Customer> {
	private Connection conn = ConnectionManager.getConnection();

	@Override
	public Customer findById(long id) {

		PreparedStatement prep = null;
		String query = null;
		Customer customer = null;

		try {

			query = "select * from customer where customer_id=?";

			prep = conn.prepareStatement(query);

			prep.setLong(1, id);

			ResultSet rs = prep.executeQuery();

			if (rs != null) {
				rs.next();
				customer = new Customer(rs.getLong(1), rs.getString(2), (rs.getString(3)), rs.getString(4));
			}

		} catch (Exception e) {
			System.err.println("Error in CustomerDAO.findById()");
		}

		return customer;
	}


	@Override
	public boolean update(Customer entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Customer entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(Customer entity) {
		PreparedStatement prep = null;
		String query = null;
		int created = 0;

		try {
			query = "INSERT INTO customer(fullname, email, password) VALUES (?, ?, ?)";

			prep = conn.prepareStatement(query);

			prep.setString(1, entity.getFullname());
			prep.setString(2, entity.getEmail());
			prep.setString(3, entity.getPassword());

			created = prep.executeUpdate();

			if (created == 1) {
				
				Animate.load();
				System.out.println("\nUser was created. Please Login to continue..");
				return true;
			}
		} catch (Exception e) {
			System.err.println("Account could not be created with provided information.");
		}

		return false;
	}

	public long verifyCredentials(Customer entity) {
		PreparedStatement prep = null;
		String query = null;
		long id = 0;

		try {

			query = "select customer_id from customer where email = ? and password = ?";
			prep = conn.prepareStatement(query);

			prep.setString(1, entity.getEmail());
			prep.setString(2, entity.getPassword());

			ResultSet rs = prep.executeQuery();

			if (rs != null) {

				rs.next();
				id = rs.getLong("customer_id");
			}
		} catch (Exception e) {
			System.err.println("Could not verify credentials");
		}

		return id;
	}

}
