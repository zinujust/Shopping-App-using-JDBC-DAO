package com.cognixia.jump.jdbc.main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cognixia.jump.jdbc.dao.CustomerDAO;
import com.cognixia.jump.jdbc.dao.InvoiceDAO;
import com.cognixia.jump.jdbc.dao.PurchasesDAO;
import com.cognixia.jump.jdbc.model.Customer;
import com.cognixia.jump.jdbc.model.Invoice;
import com.cognixia.jump.jdbc.model.Purchases;

public class ShoppingAppMenu {

	private static long cust_id = 0;
	private static boolean isLoggedIn = false;
	private static long invoice_no = 0;

	public void mainMenu(Scanner scan) {

		int choice1 = 0, choice2 = 0;

		while (isLoggedIn == false) {

			System.out.println("\n\t\tStandalone e-Commerce App");
			System.out.println("+===========================================================+");
			System.out.println();
			System.out.println("\t\t1. Register" + "\n\t\t2. Login" + "\n\t\t3. Exit");
			System.out.println();
			System.out.println("+===========================================================+");

			choice1 = scan.nextInt();

			switch (choice1) {

			case 1:
				disperseRegisterAction(scan);
				break;
			case 2:
				disperseLoginAction(scan);
				break;
			case 3:
				System.out.print("Exiting: ");
				Animate.load();
				return;
			}
		}

		while (choice2 != 5) {

			System.out.println("\n\t\tStandalone e-Commerce App");
			System.out.println("+===========================================================+");
			System.out.println();
			System.out.println(
					"\n\t\t1. Buy An Item" + "\n\t\t2. View Invoice" + "\n\t\t3. Replace An Item" + "\n\t\t4. Exit");
			System.out.println();
			System.out.println("+===========================================================+");

			choice2 = scan.nextInt();

			switch (choice2) {

			case 1:
				disperseBuyAction(scan);
				break;
			case 2:
				displayInvoice(scan);
				break;
			case 3:
				disperseReturnAction(scan);
				break;
			case 4:
				System.out.print("Exiting: ");
				Animate.load();
				return;

			}

		}

	}

	private void disperseRegisterAction(Scanner scan) {
		String name = null, email = null, password = null;

		System.out.print("Please enter your name: ");
		name = scan.next();

		scan.nextLine();
		System.out.print("Please enter your email: ");
		email = scan.next();

		scan.nextLine();
		System.out.print("Enter your password: ");
		String temp = password = scan.nextLine();

		System.out.print("Confirm your password: ");
		password = scan.nextLine();

		if (temp.equals(password)) {
			Customer customer = new Customer();
			customer.setFullname(name);
			customer.setEmail(email);
			customer.setPassword(password);

			CustomerDAO dao = new CustomerDAO();
			dao.create(customer);
		} else {
			System.out.println("Passwords don't match!");
		}

	}

	private void disperseLoginAction(Scanner scan) {
		String name = null, email = null, password = null;

		scan.nextLine();
		System.out.print("Please enter your email: ");
		email = scan.next();

		scan.nextLine();
		System.out.print("Please enter password: ");
		password = scan.nextLine();

//		email = "mail@mail.com";
//		password = "password";

		CustomerDAO dao = new CustomerDAO();
		Customer customer = new Customer(-1, name, email, password);

		cust_id = dao.verifyCredentials(customer);

		if (cust_id != 0) {
			System.out.print("\nVerifying: ");
			Animate.load();
			System.out.println("Successfully logged in..");
			isLoggedIn = true;
		}

	}

	public void disperseBuyAction(Scanner scan) {

		System.out.println("\n\t\tStandalone e-Commerce App");
		System.out.println("+===========================================================+");
		System.out.printf("\t%-15s %-15s %-10s%n", "Items", "Item Code", "Price");
		System.out.println();
		System.out.printf("\t%-15s %-15s $%-10s%n", "1. Jacket", "Ja1", 20);
		System.out.printf("\t%-15s %-15s $%-10s%n", "2. Jeans", "Je1", 10);
		System.out.printf("\t%-15s %-15s $%-10s%n", "3. Shirt", "Sh1", 5);
		System.out.printf("\t%-15s", "4. Exit");
		System.out.println();
		System.out.println("+===========================================================+");
		System.out.println("Select item: ");

		int choice = 0;

		choice = scan.nextInt();

		InvoiceDAO dao = new InvoiceDAO();
		PurchasesDAO pdao = new PurchasesDAO();

		switch (choice) {
		case 1:
			invoiceGetter(dao);
			Purchases item1 = new Purchases(-1, invoice_no, "Jacket", "Ja1", 20);
			pdao.create(item1);

			break;
		case 2:
			invoiceGetter(dao);
			Purchases item2 = new Purchases(-1, invoice_no, "Jeans", "Je1", 10);
			pdao.create(item2);
			break;
		case 3:
			invoiceGetter(dao);
			Purchases item3 = new Purchases(-1, invoice_no, "Shirt", "Sh1", 5);
			pdao.create(item3);
			break;
		case 4:
			System.out.println("Returning to main menu..");
		}

	}

	public void invoiceGetter(InvoiceDAO dao) {
		if (invoice_no == 0) {
			Invoice invoice = new Invoice(-1, LocalDate.now(), LocalTime.now(), cust_id);
			dao.create(invoice);
		}
		invoice_no = dao.returnInvoiceNumber(cust_id);
	}

	public void displayInvoice(Scanner scan) {

		long num = 0;
		CustomerDAO cdao = new CustomerDAO();
		InvoiceDAO idao = new InvoiceDAO();

		Customer customer = null;
		customer = cdao.findById(cust_id);

		List<Invoice> invoices = new ArrayList<>();
		Invoice i = null;
		invoices = idao.findUserPurchases(cust_id);

		System.out.println("\n\t\tInvoice History");
		System.out.println("+===========================================================+");
		for (Invoice inv : invoices) {

			System.out.printf("\t%-5d %-15s %-10s%n", inv.getInvoice_no(),
					inv.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
					inv.getTime().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
		}
		System.out.println("+===========================================================+");

		System.out.print("\nPlease select invoice to view: ");
		num = scan.nextLong();

		i = idao.findById(num);

		generateInvoice(num, customer, i);
	}

	private void generateInvoice(long num, Customer customer, Invoice invoice) {

		int total = 0;
		PurchasesDAO pdao = new PurchasesDAO();
		List<Purchases> items = new ArrayList<>();
		items = pdao.findUserPurchases(num);

		System.out.println("\n\t\tStandalone e-Commerce App Invoice");
		System.out.println("+===========================================================+");

		System.out.printf("%s %s %s %s %s%n", "Customer: ", customer.getFullname(), "    Date: ",
				invoice.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				invoice.getTime().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
		System.out.println("Invoice No: " + invoice.getInvoice_no());
		System.out.println("\n");

		for (Purchases item : items) {
			System.out.printf("\t%-15s %-10s $%-5d%n", item.getItem(), item.getItem_code(), item.getItem_price());

			total = total + item.getItem_price();
		}
		System.out.println("\n");
		System.out.println("\tTotal: $" + total);
		System.out.println("\n");
		System.out.println("+===========================================================+");
	}

	public void disperseReturnAction(Scanner scan) {

		long number = 0;
		String code = null;

		PurchasesDAO pdao = new PurchasesDAO();

		System.out.println("Enter invoice no: ");
		number = scan.nextLong();

		System.out.println("Enter item code your want removed: ");
		code = scan.next();
		scan.nextLine();

		pdao.removeItem(number, code);

	}

}
