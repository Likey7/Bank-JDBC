package acc;

public class Account {
	String id, name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	int balance;
	
	

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Account(String id, String name, int balance) {
		this.id = id;
		this.name = name;
		this.balance = balance;
	}

	public String info() {
		return "계좌번호:" + id + ", 이름:" + name + ", 잔액:" + balance;
	}

	public void deposit(int money) {
		balance += money;
	}

	public void withdraw(int money) {
		if (0 < balance - money) {
			balance -= money;
		} else {
			System.out.println("잔액이 부족합니다.");
		}
	}

}
