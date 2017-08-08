package org.diaz.myob;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Generates the payslips for a given a fiscal year.
 * It assumes the fiscal year begins July 1st of each calendar year.
 * For excercise purposes only, it loads taxable values programatically.
 * @author Facundo
 *
 */
public class PayslipGenerator {
	private final ArrayList<TaxableIncomeCalculator> calculators = new ArrayList<>();
	private final int[] ranges;
	private final int year;
	
	public PayslipGenerator(int fiscalYearStart) {
		ranges = new int[6];
		loadCalculators();
		this.year = fiscalYearStart;
	}

	/**
	 * For excercise purposes only, it loads taxable values programmatically. 
	 */
	void loadCalculators() {
		
		ranges[0] = 0;
		ranges[1] = 18200;
		ranges[2] = 37000;
		ranges[3] = 80000;
		ranges[4] = 180000;
		ranges[5] = Integer.MAX_VALUE;
		
		calculators.add(new TaxableIncomeCalculator(0, 0.0, 0));
		calculators.add(new TaxableIncomeCalculator(0, 19.0, 18200));
		calculators.add(new TaxableIncomeCalculator(3572, 32.5, 37000));
		calculators.add(new TaxableIncomeCalculator(17547, 37.0, 80000));
		calculators.add(new TaxableIncomeCalculator(54547, 45.0, 180000));
	}
	
	/**
	 * Utilizes the employee details to calculate its salary and create a payslip. 
	 * It uses the values loaded at object creation time.
	 * @param registry
	 * @return
	 */
	public Payslip generatePayslip(EmployeeDetails registry) {
		validateStartDate(registry);
		TaxableIncomeCalculator calc = getCalculatorFromSalary(registry.getSalary());
		
		SalaryBreakDown salaryBreakDown = calc.calculate(registry.getFromDate(), registry.getToDate(), 
				registry.getSalary(), registry.getSuperRate());
		
		return new Payslip(registry.getFirstName() + " " + registry.getLastName(), 
				registry.getFromDate(), registry.getToDate(), salaryBreakDown);
		
	}

	private TaxableIncomeCalculator getCalculatorFromSalary(int salary) {
		TaxableIncomeCalculator calc = null;
		for (int i = 0; i < ranges.length; i++) {
			if (isCalculatorForSalary(salary, i)) {
				calc = calculators.get(i);
				break;
			}
		}
		return calc;
	}

	private boolean isCalculatorForSalary(int salary, int index) {
		return ranges[index] < salary && salary <= ranges[index+1];
	}

	private void validateStartDate(EmployeeDetails registry) {
		if (registry.getFromDate().isBefore(LocalDate.of(year, 7, 1))) {
			throw new IllegalArgumentException("The date range should be within the fiscar year of " + year + "-" + year+1);
		}
	}
}
