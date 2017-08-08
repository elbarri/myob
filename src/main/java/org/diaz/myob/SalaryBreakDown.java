package org.diaz.myob;

public class SalaryBreakDown {
	private final long gross;
	private final long incomeTax;
	private final long netIncome;
	private final long superannuation;
	
	public SalaryBreakDown(long gross, long incomeTax, long netIncome, long superannuation) {
		super();
		this.gross = gross;
		this.incomeTax = incomeTax;
		this.netIncome = netIncome;
		this.superannuation = superannuation;
	}
	
	public long getGross() {
		return gross;
	}
	public long getIncomeTax() {
		return incomeTax;
	}
	public long getNetIncome() {
		return netIncome;
	}
	public long getSuperannuation() {
		return superannuation;
	}

}
