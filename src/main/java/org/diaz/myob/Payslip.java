package org.diaz.myob;

import java.time.LocalDate;

public class Payslip {
	private final String name;
	private final LocalDate periodStart;
	private final LocalDate periodEnd;
	private final SalaryBreakDown salaryBreakdown;
	
	public Payslip(String name, LocalDate periodStart, LocalDate periodEnd, SalaryBreakDown salaryBreakdown) {
		super();
		this.name = name;
		this.periodStart = periodStart;
		this.periodEnd = periodEnd;
		this.salaryBreakdown = salaryBreakdown;
	}
	public String getName() {
		return name;
	}
	public LocalDate getPeriodStart() {
		return periodStart;
	}
	public LocalDate getPeriodEnd() {
		return periodEnd;
	}
	public SalaryBreakDown getSalaryBreakdown() {
		return salaryBreakdown;
	}
}
