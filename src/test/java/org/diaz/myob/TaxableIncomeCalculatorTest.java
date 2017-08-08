package org.diaz.myob;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TaxableIncomeCalculatorTest {
	TaxableIncomeCalculator calc; 
	
	@Test(expected=IllegalArgumentException.class)
	public void testPeriodEndsBeforeStarts() {
		calc.calculate(LocalDate.now(), LocalDate.now().minusDays(1), 40000, 10.0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPeriodMonthDiffer() {
		calc.calculate(LocalDate.now(), LocalDate.now().plusMonths(1), 40000, 10.0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPeriodYearDiffer() {
		calc.calculate(LocalDate.now(), LocalDate.now().plusYears(1), 40000, 10.0);
	}
	
	@Test
	public void testCalculatSalaryWithFixedTax() {
		SalaryBreakDown salaryBreakDown = calc.calculate(LocalDate.of(2012, 3, 1), LocalDate.of(2012, 3, 31), 60050, 9.0);
		Assert.assertEquals(5004, salaryBreakDown.getGross());
		Assert.assertEquals(922, salaryBreakDown.getIncomeTax());
		Assert.assertEquals(4082, salaryBreakDown.getNetIncome());
		Assert.assertEquals(450, salaryBreakDown.getSuperannuation());
		
	}
	
	@Test
	public void testCalculatSalaryWithFixedTaxOverHalfWorkedMonth() {
		SalaryBreakDown salaryBreakDown = calc.calculate(LocalDate.of(2012, 4, 1), LocalDate.of(2012, 4, 15), 60050, 9.0);
		Assert.assertEquals(2502, salaryBreakDown.getGross());
		Assert.assertEquals(461, salaryBreakDown.getIncomeTax());
		Assert.assertEquals(2041, salaryBreakDown.getNetIncome());
		Assert.assertEquals(225, salaryBreakDown.getSuperannuation());
	}
	
	@Test
	public void testCalculatSalaryFor1WorkingDay() {
		SalaryBreakDown salaryBreakDown = calc.calculate(LocalDate.of(2012, 4, 2), LocalDate.of(2012, 4, 2), 60050, 9.0);
		Assert.assertEquals(167, salaryBreakDown.getGross());
		Assert.assertEquals(31, salaryBreakDown.getIncomeTax());
		Assert.assertEquals(136, salaryBreakDown.getNetIncome());
		Assert.assertEquals(15, salaryBreakDown.getSuperannuation());
	}
	
	@Test
	public void testCalculatSalaryWithoutFixedTax() {
		calc = new TaxableIncomeCalculator(0, 19.0, 20000);
		SalaryBreakDown salaryBreakDown = calc.calculate(LocalDate.of(2012, 3, 1), LocalDate.of(2012, 3, 31), 24000, 10.0);
		Assert.assertEquals(2000, salaryBreakDown.getGross());
		Assert.assertEquals(63, salaryBreakDown.getIncomeTax());
		Assert.assertEquals(1937, salaryBreakDown.getNetIncome());
		Assert.assertEquals(200, salaryBreakDown.getSuperannuation());
		
	}
	
	@Test
	public void testCalculateNonTaxableSalary() {
		calc = new TaxableIncomeCalculator(0, 0, 0);
		SalaryBreakDown salaryBreakDown = calc.calculate(LocalDate.of(2012, 3, 1), LocalDate.of(2012, 3, 31), 12000, 10.0);
		Assert.assertEquals(1000, salaryBreakDown.getGross());
		Assert.assertEquals(0, salaryBreakDown.getIncomeTax());
		Assert.assertEquals(1000, salaryBreakDown.getNetIncome());
		Assert.assertEquals(100, salaryBreakDown.getSuperannuation());
	}
	
	@Before
	public void prepareBasicCalculator() {
		calc = new TaxableIncomeCalculator(3572, 32.5, 37000);
	}
	
}
