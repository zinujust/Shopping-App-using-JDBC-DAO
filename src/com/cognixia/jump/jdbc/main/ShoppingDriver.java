package com.cognixia.jump.jdbc.main;

import java.util.Scanner;

public class ShoppingDriver {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		ShoppingAppMenu app = new ShoppingAppMenu();
		app.mainMenu(scan);
		scan.close();
	}

}
