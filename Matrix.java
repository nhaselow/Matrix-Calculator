/**
 * A 2D Array of doubles representing a matrix with certain operations.
 */
public class Matrix {

	private double[][] matrix;
	private int numRows;
	private int numCols;

	/**
	 * Constructs matrix object with given 2D Array of values
	 * @param matrix 2D Array of values
	 */
	public Matrix(double[][] matrix) {
		this.matrix = matrix;
		numRows = matrix[0].length;
		numCols = matrix.length;
	}

	/**
	 * Returns number of elements in the matrix
	 */
	public int size() {
		return numRows*numCols;
	}

	/**
	 * Returns the number or rows in the matrix
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * Returns the number of columns in the matrix
	 */
	public int getNumCols() {
		return numCols;
	}

	/**
	 * Returns the 2D Array representation of the matrix
	 */
	public double[][] getMatrix() {
		return matrix;
	}
	
	/**
	 * Returns string representation of the matrix
	 */
	public String toString() {
		String str = "";
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				str += ((matrix[col][row]+0.0) + "  ");
			}
			str += "\n";
		}
		return str;
	}

	/**
	 * Adds two matrices and returns the sum.
	 */
	public static Matrix add(Matrix m1, Matrix m2) {
		if(!(m1.getNumCols() == m2.getNumCols() && m1.getNumRows() == m2.getNumRows())) return null;

		double[][] m = new double[m1.getNumCols()][m1.getNumRows()];
		for(int row = 0; row < m1.getNumRows(); row++) {
			for(int col = 0; col < m1.getNumCols(); col++) {
				m[col][row] = m1.getMatrix()[col][row] + m2.getMatrix()[col][row];
			}
		}

		return new Matrix(m);
	}

	/**
	 * Subtracts two matrices and returns the difference
	 */
	public static Matrix subtract(Matrix m1, Matrix m2) {
		return add(m1, multiply(m2, -1));
	}

	/**
	 * Multiplies two matrices and returns the product
	 */
	public static Matrix multiply(Matrix m1, Matrix m2) {
		if(m1.getNumCols() != m2.getNumRows()) return null;

		double[][] m = new double[m1.getNumCols()][m2.getNumRows()];
		for(int col = 0; col < m1.getNumCols(); col++) {
			for(int row = 0; row < m2.getNumRows(); row++) {
				for(int indx = 0; indx < m1.getNumRows(); indx++) {
					m[col][row] += m1.getMatrix()[col][indx] * m2.getMatrix()[indx][row];
				}
			}
		}
		
		return new Matrix(m);
	}

	/**
	 * Multiplies a matrix and a constant and returns the result.
	 */
	public static Matrix multiply(Matrix m1, double c) {
		
		double[][] m = new double[m1.getNumCols()][m1.getNumRows()];
		for(int row = 0; row < m1.getNumRows(); row++) {
			for(int col = 0; col < m1.getNumCols(); col++) {
				m[col][row] = m1.getMatrix()[col][row] * c;
			}
		}

		return new Matrix(m);
	}
	
	/**
	 * Divides a matrix by a constant and returns the result
	 */
	public static Matrix divide(Matrix m1, double c) {
		return multiply(m1, 1/c);
	}
	
	/**
	 * Multiplies a matrix by itself n times and returns the result
	 */
	public static Matrix pow(Matrix m1, int n) {
		if(m1.getNumRows() != m1.getNumCols()) return null;
		
		Matrix m = m1;
		for(int reps = 1; reps < n; reps++) {
			m = multiply(m, m1);
		}
		
		return m;
	}

	/**
	 * Calculates the matrix determinant and returns the result
	 */
	public static Double determinant(Matrix m) {
		if(m.getNumCols() != m.getNumRows()) return null;
		
		double det = 0;
		
		if(m.getNumCols() == 1) {
			return new Double(m.getMatrix()[0][0]);
		}
		
		for(int col = 0; col < m.getNumCols(); col++) {
			
			double[][] sm = new double[m.getNumCols() - 1][m.getNumCols() - 1];
			for(int c = 0; c < m.getNumCols(); c++) {
				
				if(c == col) continue;
				
				for(int r = 1; r < m.getNumRows(); r++) {
					if(c < col) sm[c][r-1] = m.getMatrix()[c][r];
					else sm[c-1][r-1] = m.getMatrix()[c][r];
				}
			}
			
			if(col%2 == 0) det += m.getMatrix()[col][0]*determinant(new Matrix(sm));
			else det -= m.getMatrix()[col][0]*determinant(new Matrix(sm));
			
		}
		
		return new Double(det);
	}
	
	/**
	 * Calculates the trace of a matrix and returns the result
	 */
	public static Double trace(Matrix m) {
		if(m.getNumCols() != m.getNumRows()) return null;
		
		double tr = 0;
		for(int i = 0; i < m.getNumCols(); i++) {
			tr += m.getMatrix()[i][i];
		}
		return new Double(tr);
	}
	
	/**
	 * Calculates the transpose of the matrix and returns the result
	 */
	public static Matrix transpose(Matrix m) {
		double[][] tp = new double[m.getNumRows()][m.getNumCols()];
		for(int c = 0; c < m.getNumCols(); c++) {
			for(int r = 0; r < m.getNumRows(); r++) {
				tp[r][c] = m.getMatrix()[c][r];
			}
		}
		return new Matrix(tp);
	}
	
	/**
	 * Calculates the minor of a matrix given a particular row and column (used
	 * to calculate the inverse) and returns the result
	 */
	private static double minor(Matrix m, int col, int row) {
		if(m.getNumCols() != m.getNumRows()) throw new IllegalArgumentException("matrix must be square");
		
		double[][] sm = new double[m.getNumCols() - 1][m.getNumCols() - 1];
		for(int c = 0; c < m.getNumCols(); c++) {
			
			if(c == col) continue;
			
			for(int r = 0; r < m.getNumRows(); r++) {
				
				if(r == row) continue;

				if(c < col && r < row) sm[c][r] = m.getMatrix()[c][r];
				else if(c < col && r > row) sm[c][r-1] = m.getMatrix()[c][r];
				else if(c > col && r < row) sm[c-1][r] = m.getMatrix()[c][r];
				else sm[c-1][r-1] = m.getMatrix()[c][r];
			}
		}
		return determinant(new Matrix(sm)).doubleValue();
	}
	
	/**
	 * Calculates the matrix of minors (used to calculate the inverse) and returns
	 * the result
	 */
	private static Matrix matrix_of_minors(Matrix m) {
		if(m.getNumCols() != m.getNumRows()) throw new IllegalArgumentException();
		
		double[][] minors = new double[m.getNumCols()][m.getNumRows()];
		for(int c = 0; c < m.getNumCols(); c++) {
			for(int r = 0; r < m.getNumRows(); r++) {
				minors[c][r] = minor(m, c, r);
			}
		}
		
		return new Matrix(minors);
	}
	
	/**
	 * Calculates the matrix of cofactors (used to calculate the inverse) and 
	 * returns the result
	 */
	private static Matrix matrix_of_cofactors(Matrix m) {
		Matrix minors = matrix_of_minors(m);
		for(int c = 0; c < m.getNumCols(); c++) {
			for(int r = 0; r < m.getNumRows(); r++) {
				if((c+r) % 2 != 0) minors.getMatrix()[c][r] *= -1;
			}
		}
		return minors;
	}
	
	/**
	 * Calculates the adjoint matrix (used to calculate the inverse) and returns
	 * the result
	 */
	private static Matrix adjoint(Matrix m) {
		return transpose(matrix_of_cofactors(m));
	}
	
	/**
	 * Calculate the inverse of a matrix and returns the result
	 */
	public static Matrix inverse(Matrix m) {
		if(determinant(m) == 0) return null;
		return multiply(adjoint(m), 1/determinant(m));
	}
}
