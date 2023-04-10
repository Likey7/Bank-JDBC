package acc;

public class SpecialAccount extends Account {
	String grade;
	private double rate;
	
	public SpecialAccount(String id, String name, int balance, String grade) {
		super(id, name, balance);
		setGrade(grade);
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
		switch(grade) {
		case "VIP": rate=0.04; break;
		case "GOLD": rate=0.03; break;
		case "SILVER": rate=0.02; break;
		case "Normal": rate=0.01; break;
		}
	}

	@Override
	public void deposit(int money) {
		super.deposit(money+(int)(money*rate));
	}

	@Override
	public String info() {
		return super.info() + ", 등급:"+getGrade();
	}
	
}
