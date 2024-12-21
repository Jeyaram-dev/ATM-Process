package com.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



public class Transaction {

public Connection con;
	
	private Transaction() {
		con=Connections.getConnection();
		
	}
	public void dept(int num,int dept) {
		try {
			int b=0;
			PreparedStatement p=con.prepareStatement("select * from transaction where accnum=?");
			p.setInt(1, num);
			ResultSet r=p.executeQuery();
			if(r.next()) {
				 b=r.getInt("accbalance");
			}
			 PreparedStatement s=con.prepareStatement("update transaction set accbalance=? where accnum=?");
			b=b+dept;
			s.setInt(1,b);
			s.setInt(2,num);
			s.executeUpdate();
			System.out.println("Amount Deposited Sucessfully");
			System.out.println();
			System.out.println("Thanks for Using ATM ");

		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public void withdraw(int acnum, int withdraw,int pin) {
		try {
			int b=0;
			int l=0;
			int pn=0;
			PreparedStatement w=con.prepareStatement("select * from transaction where accnum=?");
			w.setInt(1,acnum);
			ResultSet r=w.executeQuery();
			if(r.next()){
				b=r.getInt("accbalance");
				l=r.getInt("withdraw_limit");
				pn=r.getInt("pin");
			}else {
				System.out.println("Invalid Account details");
			}
			PreparedStatement p=con.prepareStatement("update transaction set accbalance=? where accnum=? and pin=?");
			
			if(b>=withdraw && withdraw<l && pn==pin) {
			b=b-withdraw;
			p.setInt(1, b);
			p.setInt(2,acnum);
			p.setInt(3, pin);
			p.executeUpdate();
			System.out.println("Withdraw done");
			System.out.println();
			System.out.println("Thanks for Using ATM ");
			}
			else {
				System.out.println("Check withdraw Limit or Available balance");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void check(int anum) {
		try {
			String str="select * from transaction where accnum=?";
			PreparedStatement p=con.prepareStatement(str);
			p.setInt(1, anum);
			ResultSet r=p.executeQuery();
			if(r.next()) {
				//System.out.println("Your account number is" + r.getInt("accnum"));
				//System.out.println("Account holder name is " + r.getString("acc_holder_name").toUpperCase());
				System.out.println("Your account balance is " + r.getInt("accbalance"));
				//System.out.println("Your Withdrawl limit is " + r.getInt("withdraw_limit"));
				System.out.println();
				System.out.println("Thanks for Using ATM ");
			}else {
				System.out.println("Enter valid Account number");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void change(int anum,int pin,int np,int nnp) {
		try {
			int op=0;
			int newpin=np;
			PreparedStatement p=con.prepareStatement("select * from transaction where accnum=?");
			p.setInt(1, anum);
			ResultSet r=p.executeQuery();
			if(r.next()) {
				op=r.getInt("pin");
			}
			PreparedStatement pp=con.prepareStatement("update transaction set pin=? where accnum=?");
			pp.setInt(2, anum);
			if(op==pin&&nnp==newpin) {
				pp.setInt(1,nnp);
				pp.executeUpdate();
				System.out.println("Pin changed Sucessfully");
				System.out.println();
				System.out.println("Thanks for Using ATM ");
			}else {
				
				System.out.println("Please provide correct information");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		Transaction d= new Transaction();
		Scanner s= new Scanner(System.in);
		System.out.println("Welcome to Atm");
		System.out.println("Kindly choose below Option");
		System.out.println();
		System.out.println("Press 1 to Deposit Amount");
		System.out.println("Press 2 to Withdraw Amount");
		System.out.println("Press 3 to Check Balance");
		System.out.println("Press 4 to Change Atm Pin");
		System.out.println("Press 5 to Exit");
		int choice=s.nextInt();
		switch(choice){
		case 1:
			System.out.println("Enter Your Account number");
			int num=s.nextInt();
			System.out.println("Enter the Amount to Deposit");
			int dep=s.nextInt();
			d.dept(num, dep);
			break;
		case 2:
			System.out.println("Enter Your Account number");
			int n=s.nextInt();
			System.out.println("Enter the Withdrawl Amount");
			int amt=s.nextInt();
			System.out.println("Enter Your Pin number");
			int pin=s.nextInt();
			d.withdraw(n, amt, pin);
			break;
		case 3:
			System.out.println("Enter your Account Number");
			int nu=s.nextInt();
			d.check(nu);
			break;
		case 4:
			System.out.println("Enter your Account Number");
			int a=s.nextInt();
			System.out.println("Enter your Old Pin");
			int op=s.nextInt();
			System.out.println("Enter your New Pin");
			int np=s.nextInt();
			System.out.println("Re-enter new Pin");
			int nnp=s.nextInt();
			d.change(a,op, np, nnp);
			break;
		case 5:
			System.exit(0);
			break;
		default:
		System.out.println("Invalid Selection");
		break;
		}
		
		
	}

}
