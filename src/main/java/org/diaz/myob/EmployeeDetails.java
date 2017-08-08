package org.diaz.myob;

import java.time.LocalDate;

public class EmployeeDetails {
	private final String firstName;
	private final String lastName;
	private final int salary;
	private final double superRate;
	private final LocalDate fromDate;
	private final LocalDate toDate;
	
	public EmployeeDetails(String firstName, String lastName, int salary, double superRate, LocalDate fromDate,
			LocalDate toDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.superRate = superRate;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getSalary() {
		return salary;
	}

	public double getSuperRate() {
		return superRate;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}
	
	
	
}
