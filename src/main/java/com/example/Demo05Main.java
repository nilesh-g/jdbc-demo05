package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Demo05Main {
	public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "nilesh";
	
	static {
		try {
			Class.forName(DB_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection con = null;
		try {
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			con.setAutoCommit(false);

			System.out.print("Enter amount: ");
			double amount = sc.nextDouble();
			System.out.print("Enter from account: ");
			int id1 = sc.nextInt();
			System.out.print("Enter to account: ");
			int id2 = sc.nextInt();
			
			String sql1 = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
			PreparedStatement stmt1 = con.prepareStatement(sql1);
			stmt1.setDouble(1, amount);
			stmt1.setInt(2, id1);
			int count1 = stmt1.executeUpdate();
			
			String sql2 = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
			PreparedStatement stmt2 = con.prepareStatement(sql2);
			stmt2.setDouble(1, amount);
			stmt2.setInt(2, id2);
			int count2 = stmt2.executeUpdate();
			
			if(count1 != 1 || count2 != 1)
				throw new RuntimeException("Invalid transfer.");
			else
				System.out.println("Transfer successful.");

			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (con != null)
					con.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
