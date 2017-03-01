
public class ComplexNumber {
	private double a;
	private double b;
	
	public ComplexNumber(double a, double b) {
		this.a = a;
		this.b = b;
	}
	
	public ComplexNumber(ComplexNumber copy) {
		this.a = copy.getA();
		this.b = copy.getB();
	}
	
	public double getA() {
		return a;
	}
	
	public double getB() {
		return b;
	}
	
	public String toString() {
		return (a + " + " + b + "*i");
	}
	
	public boolean equals(ComplexNumber cn) {
		return (cn.getA() == a && cn.getB() == b);
	}
	
	public static ComplexNumber add(ComplexNumber c1, ComplexNumber c2) {
		if (c1 == null) return c2;
		if (c2 == null) return c1;
		return new ComplexNumber(c1.getA() + c2.getA(), c1.getB() + c2.getB());
	}
	
	public static ComplexNumber subtract(ComplexNumber c1, ComplexNumber c2) {
		if (c1 == null) return multiply(c2, -1);
		if (c2 == null) return c1;
		return new ComplexNumber(c1.getA() - c2.getA(), c1.getB() - c2.getB());
	}

	public static ComplexNumber multiply(ComplexNumber c1, ComplexNumber c2) {
		if (c1 == null || c2 == null) return ZERO;
		double a = c1.getA()*c2.getA() - c1.getB()*c2.getB();
		double b = c1.getA()*c2.getB() + c1.getB()*c2.getB();
		return new ComplexNumber(a, b);
	}
	
	public static ComplexNumber multiply(ComplexNumber c1, double c) {
		if (c1 == null) return ZERO;
		return new ComplexNumber(c1.getA() * c, c1.getB() * c);
	}

	private static ComplexNumber conjugate(ComplexNumber c) {
		if (c == null) return ZERO;
		return new ComplexNumber(c.getA(), -1*c.getB());
	}
	
	public static ComplexNumber divide(ComplexNumber c1, ComplexNumber c2) {
		if (c1 == null) return ZERO;
		if (c2 == null) throw new IllegalArgumentException("Divide by zero");
		return divide(multiply(c1, conjugate(c2)), multiply(c2, conjugate(c2)).getA());
	}
	
	public static ComplexNumber divide(ComplexNumber c1, double c) {
		if (c1 == null) return ZERO;
		if (c == 0) throw new IllegalArgumentException("Divide by zero");
		return multiply(c1, 1/c);
	}

	public static ComplexNumber divide(double c, ComplexNumber c1) {
		return divide(new ComplexNumber(c, 0), c1);
	}
	
	public static ComplexNumber ZERO = new ComplexNumber(0,0);
}
