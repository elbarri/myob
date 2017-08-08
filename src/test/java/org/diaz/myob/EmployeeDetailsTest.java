package org.diaz.myob;

import java.time.LocalDate;

import org.junit.Test;

public class EmployeeDetailsTest {

	@Test(expected=IllegalArgumentException.class)
	public void testParsingChecksPositiveSalary(){
		new EmployeeDetails("David", "Rudd", 0, 9.0, LocalDate.of(2013, 03, 1), LocalDate.of(2013, 3, 31));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParsingPositiveSuper(){
		new EmployeeDetails("David", "Rudd", 60000, -1, LocalDate.of(2013, 03, 1), LocalDate.of(2013, 3, 31));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParsingSuperBelow50(){
		new EmployeeDetails("David", "Rudd", 60000, 50.1, LocalDate.of(2013, 03, 1), LocalDate.of(2013, 3, 31));
	}
	
}
