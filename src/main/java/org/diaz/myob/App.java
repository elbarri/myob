package org.diaz.myob;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Starts the application.<br> 
 * Asks for the csv file path.<br>
 * Calculates salaries <br>
 * Prints output to screen in csv format <br>
 * <u>NOTE:</u> Data will be processed assuming it belongs to fiscal year 2012-2013. <br>
 * It also assumes the csv file contains only valid content and meets the following format: <br>
 * David,Rudd,60050,9%,01/March/2013 – 31/March/2013
 * Ryan,Chen,120000,10%,01/March/2013 – 31/March/2013
 */
public class App {
	private static final String SEPARATOR = ",";
	private static final int FIRST_NAME = 0;
	private static final int LAST_NAME = 1;
	private static final int SALARY = 2;
	private static final int SUPER_RATE = 3;
	private static final int DATE_RANGE = 4;
	private static final String DATE_SEPARATOR = " – ";
	private final DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("dd/MMMM/yyyy").toFormatter();

	public static void main( String[] args ) {
		App app = new App();
		app.runApp(); 
	}

	void runApp() {
		final PayslipGenerator payslipGenerator = new PayslipGenerator(2012);

		List<List<String>> lines = readLinesFromCSV(getFilePath());

		printStartingMessage();
		
		List<Payslip> payslips = lines.stream().map(line->payslipGenerator.generatePayslip(parseEmployeeDetails(line))
				).collect(Collectors.toList());
		
		System.out.println("Processed payslips:");
		
		payslips.forEach(ps->printPayslip(ps));
		
		printClosingMessage();
	}

	protected void printStartingMessage() {
		System.out.println("");
		System.out.println("Processing...");
	}

	protected void printClosingMessage() {
		System.out.println("");
		System.out.println("Press Enter key to exit.");
        try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void printPayslip(Payslip ps) {
		StringBuffer sb = new StringBuffer();
		sb.append(ps.getName());
		sb.append(SEPARATOR);
		sb.append(formatter.format(ps.getPeriodStart()));
		sb.append(DATE_SEPARATOR);
		sb.append(formatter.format(ps.getPeriodEnd()));
		sb.append(SEPARATOR);
		sb.append(ps.getSalaryBreakdown().getGross());
		sb.append(SEPARATOR);
		sb.append(ps.getSalaryBreakdown().getIncomeTax());
		sb.append(SEPARATOR);
		sb.append(ps.getSalaryBreakdown().getNetIncome());
		sb.append(SEPARATOR);
		sb.append(ps.getSalaryBreakdown().getSuperannuation());
		System.out.println(sb.toString());
	}

	protected EmployeeDetails parseEmployeeDetails(List<String> line) {
		try {
			String firstName = line.get(FIRST_NAME);
			String lastName = line.get(LAST_NAME);
			int salary = Integer.valueOf(line.get(SALARY));
			double superRate = Double.valueOf(line.get(SUPER_RATE).replace("%", ""));
			String[] dateRengeSplit = line.get(DATE_RANGE).split(DATE_SEPARATOR);
			LocalDate fromDate = LocalDate.parse(dateRengeSplit[0], formatter);
			LocalDate toDate = LocalDate.parse(dateRengeSplit[1], formatter);
			return new EmployeeDetails(firstName, lastName, salary, superRate, fromDate, toDate);
		} catch (Exception e) {
			System.out.println("An exception occurred while parsing line. "
					+ "Please make sure it meets the formatting criteria: " + line.toString());
			e.printStackTrace();
			
		}
		
		System.out.println("Program will exit.");
		System.exit(-1);
		return null;
	}

	String getFilePath() {
		System.out.println("Please type in full file path:" + System.getProperty("line.separator"));

		String filePath = null;
		try (Scanner sc = new Scanner(System.in)){
			boolean validFilePath = false;
			do {
				filePath = sc.nextLine();
				validFilePath = isValidFile(filePath);
			} while(!validFilePath);
		} 
		return filePath;
	}

	protected boolean isValidFile(String filePath) {
		File file;
		file = new File(filePath);
		if (file.exists() && !file.isDirectory()) {
			return true;
		} 

		System.out.println("Enter a valid filepath");
		return false;
	}

	private List<List<String>> readLinesFromCSV(String filePath) {
		List<List<String>> lines = null;
		try (BufferedReader bufferReader = Files.newBufferedReader(Paths.get(filePath))) {
			Function<String, List<String>> mapper = line -> Arrays.asList(line.split(SEPARATOR));
			lines = bufferReader.lines().map(mapper).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
}
