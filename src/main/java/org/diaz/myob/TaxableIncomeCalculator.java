package org.diaz.myob;

import java.time.LocalDate;
import java.time.Period;

/**
 * Holds the logic to calculate the monthly salary breakdown.  
 * @author Facundo
 */
public class TaxableIncomeCalculator {
	private final int fixedAmount;
	private final double deductiblePercentaje;
	private final int nonTaxable;
	private final static double MONTHS_IN_A_YEAR = 12.0;

	/**
	 * Calculates salary for a given month. 
	 * <b>NOTE</b> There is no check for a negative anual gross nor negative super rate.
	 * @param periodStart must belong the same month as periodEnd. 
	 * @param periodEnd must be equal or greater than periodStart.
	 * @param anualGross
	 * @param superRate
	 * @return
	 */
	public SalaryBreakDown calculate(LocalDate periodStart, LocalDate periodEnd, int anualGross, double superRate) {
		double percentageOfWorkedDays = getPercentajeOfWorkedDays(periodStart, periodEnd);
		long gross = Math.round(anualGross / MONTHS_IN_A_YEAR * percentageOfWorkedDays);
		long incomeTax = calculateIncomeTax(anualGross, percentageOfWorkedDays);
		long netIncome = gross - incomeTax;
		long superAmount = calculateSuper(superRate, gross);
		return new SalaryBreakDown(gross, incomeTax, netIncome, superAmount);
	}

	private double getPercentajeOfWorkedDays(LocalDate periodStart, LocalDate periodEnd) {
		validateDateRange(periodStart, periodEnd);

		long workedDays = Period.between(periodStart, periodEnd).getDays() + 1;
		return (double)workedDays / periodEnd.lengthOfMonth();
	}

	private void validateDateRange(LocalDate periodStart, LocalDate periodEnd) {
		if (isValidRange(periodStart, periodEnd)) {
			throw new IllegalArgumentException("Wrong dates. Salary is calculated monthly: " + periodStart + " - " + periodEnd);
		}
	}

	private boolean isValidRange(LocalDate periodStart, LocalDate periodEnd) {
		return periodStart.getMonth() != periodEnd.getMonth() || 
				periodStart.getYear() != periodEnd.getYear() ||
				periodStart.isAfter(periodEnd);
	}

	private long calculateSuper(double superRate, long gross) {
		return Math.round(gross * (superRate / 100));
	}

	private long calculateIncomeTax(int anualGross, double percentageOfWorkedDays) {
		int deductibleSalary = anualGross - nonTaxable;
		return Math.round((fixedAmount + deductibleSalary * (deductiblePercentaje)) / MONTHS_IN_A_YEAR  * percentageOfWorkedDays);
	}

	/**
	 * Class constructor
	 * @param fixedAmount Fixed ammount of tax that will be deducted from the annual salary.
	 * @param deductiblePercentaje Percentaje of the variable tax that will be deducted from the anual salary.
	 * @param nonTaxable ammount that will not be affected by tax.
	 */
	public TaxableIncomeCalculator(int fixedAmount, double deductiblePercentaje, int nonTaxable) {
		super();
		this.fixedAmount = fixedAmount;
		this.deductiblePercentaje = deductiblePercentaje / 100;
		this.nonTaxable = nonTaxable;
	}
}

