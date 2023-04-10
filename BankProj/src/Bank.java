import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import acc.Account;
import acc.SpecialAccount;

public class Bank {
	HashMap<String, Account> accs = new HashMap<>();

	Scanner sc = new Scanner(System.in);

	int menu() {
		System.out.println("[코스타 은행]");
		System.out.println("0. 종료");
		System.out.println("1. 계좌개설");
		System.out.println("2. 입금");
		System.out.println("3. 출금");
		System.out.println("4. 계좌조회");
		System.out.println("5. 전체계좌조회");
		System.out.println("선택>>");
		int sel = Integer.parseInt(sc.nextLine());
		return sel;
	}

	void makeAccountMenu() {
		System.out.println("[계좌개설]");
		System.out.println("1. 일반계좌");
		System.out.println("2. 특수계좌");
		System.out.print("선택>> ");
		int sel = Integer.parseInt(sc.nextLine());
		switch (sel) {
		case 1:
			makeAccount();
			break;
		case 2:
			makeSpecialAccount();
			break;
		}
	}

	void makeAccount() {
		System.out.println("[계좌 개설]");
		System.out.print("계좌번호 : ");
		String id = sc.nextLine();
		if (accs.containsKey(id)) {
			System.out.println("이미 존재하는 계좌번호입니다.");
			return;
		}

		System.out.println("이름 : ");
		String name = sc.nextLine();
		System.out.print("입금액 : ");
		int money = Integer.parseInt(sc.nextLine());

		accs.put(id, new Account(id, name, money));
	}

	void makeSpecialAccount() {
		System.out.println("[계좌 개설]");
		System.out.print("계좌번호 : ");
		String id = sc.nextLine();
		if (accs.containsKey(id)) {
			System.out.println("이미 존재하는 계좌번호입니다.");
			return;
		}

		System.out.println("이름 : ");
		String name = sc.nextLine();
		System.out.print("입금액 : ");
		int money = Integer.parseInt(sc.nextLine());
		System.out.print("등급 : ");
		String grade = sc.nextLine();

		accs.put(id, new SpecialAccount(id, name, money, grade));
	}

	void deposit() {
		System.out.println("[입금]");
		System.out.println("계좌번호 : ");
		String id = sc.nextLine();
		System.out.println("입금액 : ");
		int money = Integer.parseInt(sc.nextLine());

		if (!accs.containsKey(id)) {
			System.out.println("계좌번호가 틀립니다.");
		} else {
			accs.get(id).deposit(money);
		}
	}

	void withdraw() {

		System.out.println("[출금]");
		System.out.println("계좌번호 : ");
		String id = sc.nextLine();
		System.out.println("출금액 : ");
		int money = Integer.parseInt(sc.nextLine());

		if (!accs.containsKey(id)) {
			System.out.println("계좌번호가 틀립니다.");
		} else {
			accs.get(id).withdraw(money);
		}
	}

	void accountInfo() {
		System.out.println("[계좌조회]");
		System.out.println("계좌번호 : ");
		String id = sc.nextLine();

		if (!accs.containsKey(id)) {
			System.out.println("계좌번호가 틀립니다.");
		} else {
			System.out.println(accs.get(id).info());
		}
	}

	void allAccountInfo() {
		System.out.println("[전체 계좌 조회]");
		for (Account a : accs.values()) {
			System.out.println(a.info());
		}
	}

	void saveAllAccount() {
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		try {
			fos = new FileOutputStream("pers.data");
			dos = new DataOutputStream(fos);
			dos.writeInt(accs.size());
			for (Account acc : accs.values()) {
				if (acc instanceof SpecialAccount) {
					dos.writeChar('S');
					dos.writeUTF(((SpecialAccount) acc).getGrade());
				} else {
					dos.writeChar('N');
				}
				dos.writeUTF(acc.getId()); // 계좌번호
				dos.writeUTF(acc.getName());
				dos.write(acc.getBalance());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(dos!= null) dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	void loadAllAccount() {
		FileInputStream fis = null;
		DataInputStream dis = null;
		try {
			fis = new FileInputStream("accs.data");
			dis = new DataInputStream(fis);
			int cnt = dis.readInt();
			for(int i=0;i<cnt;i++) {
				char sect=dis.readChar();
				if(sect=='S') {
					String grade=dis.readUTF();
					String id=dis.readUTF();
					String name = dis.readUTF();
					int balance=dis.readInt();
					accs.put(id, new SpecialAccount(id, name, balance, grade));
				} else {
					String id=dis.readUTF();
					String name=dis.readUTF();
					int balance = dis.readInt();
					accs.put(id, new Account(id, name, balance));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(dis!=null) dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Bank bank = new Bank();
		bank.loadAllAccount();

		while (true) {
			int sel = bank.menu();
			if (sel == 0) {
				bank.saveAllAccount();
				break;
			}
			switch (sel) {
			case 1:
				bank.makeAccountMenu();
				break;
			case 2:
				bank.deposit();
				break;
			case 3:
				bank.withdraw();
				break;
			case 4:
				bank.accountInfo();
				break;
			case 5:
				bank.allAccountInfo();
			}
		}
	}

}
