package org.diaz.myob;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PayslipGeneratorTest {
	PayslipGenerator generator;
	EmployeeDetails employeeDetails;
	
	@Before
	public void prepare() {
		generator = new PayslipGenerator(2012);
		employeeDetails = new EmployeeDetails("David", "Rudd", 60050, 9.0, LocalDate.of(2013, 03, 1), LocalDate.of(2013, 3, 31));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidStartDate() {
		generator.generatePayslip(new EmployeeDetails("David", "Rudd", 60050, 9.0, LocalDate.of(2012, 03, 1), LocalDate.of(2012, 3, 31)));
	}
	
	@Test
	public void testCorrectPayslip() {
		Payslip payslip = generator.generatePayslip(new EmployeeDetails("David", "Rudd", 60050, 9.0, LocalDate.of(2013, 03, 1), LocalDate.of(2013, 3, 31)));
		
		Assert.assertEquals(5004, payslip.getSalaryBreakdown().getGross());
		Assert.assertEquals(922, payslip.getSalaryBreakdown().getIncomeTax());
		Assert.assertEquals(4082, payslip.getSalaryBreakdown().getNetIncome());
		Assert.assertEquals(450, payslip.getSalaryBreakdown().getSuperannuation());
		Assert.assertEquals("David Rudd", payslip.getName());
		Assert.assertEquals(LocalDate.of(2013, 03, 1), payslip.getPeriodStart());
		Assert.assertEquals(LocalDate.of(2013, 03, 31), payslip.getPeriodEnd());
		
	}
	
	@Test
	public void testCalculateNonTaxableSalary() {
		Payslip payslip = generator.generatePayslip(new EmployeeDetails("Facundo", "Diaz", 12000, 10.0, LocalDate.of(2013, 05, 1), LocalDate.of(2013, 5, 31)));
		
		Assert.assertEquals(1000, payslip.getSalaryBreakdown().getGross());
		Assert.assertEquals(0, payslip.getSalaryBreakdown().getIncomeTax());
		Assert.assertEquals(1000, payslip.getSalaryBreakdown().getNetIncome());
		Assert.assertEquals(100, payslip.getSalaryBreakdown().getSuperannuation());
		Assert.assertEquals("Facundo Diaz", payslip.getName());
		Assert.assertEquals(LocalDate.of(2013, 05, 1), payslip.getPeriodStart());
		Assert.assertEquals(LocalDate.of(2013, 05, 31), payslip.getPeriodEnd());
	}
	
	@Test
	public void testHighestIncomePayslip() {
		Payslip payslip = generator.generatePayslip(new EmployeeDetails("Facundo", "Diaz", 200000, 10.0, LocalDate.of(2013, 05, 1), LocalDate.of(2013, 5, 31)));
		
		Assert.assertEquals(16667, payslip.getSalaryBreakdown().getGross());
		Assert.assertEquals(5296, payslip.getSalaryBreakdown().getIncomeTax());
		Assert.assertEquals(11371, payslip.getSalaryBreakdown().getNetIncome());
		Assert.assertEquals(1667, payslip.getSalaryBreakdown().getSuperannuation());
		Assert.assertEquals("Facundo Diaz", payslip.getName());
		Assert.assertEquals(LocalDate.of(2013, 05, 1), payslip.getPeriodStart());
		Assert.assertEquals(LocalDate.of(2013, 05, 31), payslip.getPeriodEnd());
		
	}
	

}
