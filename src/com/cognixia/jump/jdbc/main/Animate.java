package com.cognixia.jump.jdbc.main;

public class Animate extends Thread{

	public static void load() {
		
		for(int i = 0; i < 30; i++) {
			System.out.print("#");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("\n");
	}
	
}
