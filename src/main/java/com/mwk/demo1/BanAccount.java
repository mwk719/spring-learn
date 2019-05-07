package com.mwk.demo1;

/**
 * 
 * 
 * @author 闵渭凯
 *
 *         2019年3月18日
 */
public class BanAccount {

	private double balance = 1000;

	public BanAccount() {
		super();
	}

	public BanAccount(double balance) {
		super();
		this.balance = balance;
	}
	
	/**
	 * 方法不能修改所传入的变量的内容 从这个账户转帐，并试图增加到一个余额中
	 * 
	 * @param amount
	 *            转账的金额
	 * @param otherBalance
	 *            要转入的余额
	 */
	public void transfer(double amount, double otherBalance) {
		balance = balance - amount;
		otherBalance = otherBalance + amount;
	}

	/**
	 * 对象引用
	 * 
	 * @param amount
	 * @param harrySavings
	 */
	private void transfer(double amount, BanAccount otherBalance) {
		//balance = balance - amount;
		otherBalance.deposit(amount);

	}

	/**
	 * 存款
	 * 
	 * @param amount
	 */
	private void deposit(double amount) {
		balance = balance + amount;
	}

	
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public static void main(String[] args) {
		//double saveingsBalance = 1000;
		BanAccount harrySavings = new BanAccount(1000);

		harrySavings.transfer(500, harrySavings);
		System.out.println(harrySavings.getBalance());

	}
	
	

}
