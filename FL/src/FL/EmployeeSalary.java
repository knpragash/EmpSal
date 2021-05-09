package FL;

import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class EmployeeSalary {

	public static void main(String[] args) {
		
		Fixed      f1 = new Fixed("ABC", 3000);
		Commission c1 = new Commission("GHI", 10, 150);
		Mix        m1 = new Mix("AAA", 1200, 10, 230);
		
		SalaryType[] sal = new SalaryType[] {f1, c1, m1};

		// Total salary
		double totalSalary = totalSalary(sal);
		DecimalFormat df = new DecimalFormat("####0.00");
		System.out.println("Total Salary: " + df.format(totalSalary));
		
		
		// I'm not sure of the 2nd Visitor implementation, so I developed the below code instead
		// Employee Attributes in JSON
		ObjectMapper om = new ObjectMapper();
		try {
			String jstr = om.writeValueAsString(sal);
			System.out.println(jstr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}

	private static double totalSalary(SalaryType[] sal) {
		
		Visitor visitor = new EmpVisitor();
		double  total   = 0;
		
		for(SalaryType salType : sal) {
			total = total + salType.accept(visitor);
		}
		
		return total;
	}
}


interface SalaryType {	
	public double accept(Visitor visitor);
}

interface Visitor {
	
	double visit(Fixed      fixed);
	double visit(Commission commission);
	double visit(Mix        mix);
}

class Fixed implements SalaryType {
	
	private String employeeName;
	private double salary;

	public Fixed(String employeeName, double salary) {
		
		this.employeeName = employeeName;
		this.salary       = salary;
	}

	public String getEmployeeName() {
		
		return employeeName;
	}

	public double getSalary() {
		
		return salary;
	}

	@Override
	public double accept(Visitor visitor) {
		
		return visitor.visit(this);
	}
}

class Commission implements SalaryType  {
	
	private String employeeName;
	private double rate;
	private int    volume;

	public Commission(String employeeName, double rate, int volume) {
		
		this.employeeName = employeeName;
		this.rate         = rate;
		this.volume       = volume;
	}

	public String getEmployeeName() {
		
		return employeeName;
	}

	public double getRate() {
		
		return rate;
	}

	public int getVolume() {
		
		return volume;
	}

	@Override
	public double accept(Visitor visitor) {
		
		return visitor.visit(this);
	}
}


class Mix implements SalaryType {
	
	private String employeeName;
	private double rate;
	private double fixed;
	private int    volume;

	public Mix(String employeeName, double fixed, double rate, int volume) {
		
		this.employeeName = employeeName;
		this.fixed        = fixed;
		this.rate         = rate;
		this.volume       = volume;
	}

	public String getEmployeeName() {
		
		return employeeName;
	}

	public double getFixed() {
		
		return fixed;
	}

	public double getRate() {
		
		return rate;
	}

	public int getVolume() {
		
		return volume;
	}

	@Override
	public double accept(Visitor visitor) {
		
		return visitor.visit(this);
	}
}

class EmpVisitor implements Visitor {
	
	@Override
	public double visit(Fixed fixed) {
		
		double fixedSalary = fixed.getSalary();		
			
		return fixedSalary;
	}

	@Override
	public double visit(Commission commission) {
		
		double commissionSalary = commission.getRate() * commission.getVolume();		
			
		return commissionSalary;
	}

	@Override
	public double visit(Mix mix) {
		
		double mixSalary = mix.getFixed() + (mix.getRate() * mix.getVolume());		
			
		return mixSalary;
	}
}

