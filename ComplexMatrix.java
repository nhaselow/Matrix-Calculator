
public class ComplexMatrix {

	/** Complex Matrix data structure */
	private ComplexNumber[][] matrix;
	/** Number of rows, columns in matrix */
	private int numRows, numCols;

	/**
	 * Creates matrix object given 2D array of complex numbers
	 * @param matrix 2D array of complex numbers
	 */
	public ComplexMatrix(ComplexNumber[][] matrix) {
		this.matrix = matrix;
		numRows = matrix[0].length;
		numCols = matrix.length;
	}

	/**
	 * @return number of elements in matrix
	 */
	public int size() {
		return numRows*numCols;
	}

	/**
	 * @return number of rows in matrix
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * @return number of columns in matrix
	 */
	public int getNumCols() {
		return numCols;
	}

	/**
	 * @return 2D array matrix data structure
	 */
	public ComplexNumber[][] getMatrix() {
		return matrix;
	}
	
	/**
	 * DEBUGGING: returns unformatted string representation of the array 
	 * with elements separated by two spaces
	 */
	public String toString() {
		String str = "";
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				str += ((matrix[col][row].toString()) + "  ");
			}
			str += "\n";
		}
		return str;
	}
	
	/**
	 * Computes the sum of two matrices (m1 + m2) and returns the result.
	 * Returns null if:
	 *   - either matrix is null
	 *   - matrices are different sizes
	 * Any null elements in the matrices are treated as zeros.
	 * 
	 * @param m1 matrix 1
	 * @param m2 matrix 2
	 * @return sum m1+m2, or null in above situations
	 */
	public static ComplexMatrix add(ComplexMatrix m1, ComplexMatrix m2) {
		if(m1 == null || m2 == null) return null;
		if(!(m1.getNumCols() == m2.getNumCols() && m1.getNumRows() == m2.getNumRows())) return null;

		ComplexNumber[][] m = new ComplexNumber[m1.getNumCols()][m1.getNumRows()];
		for(int row = 0; row < m1.getNumRows(); row++) {
			for(int col = 0; col < m1.getNumCols(); col++) {
				m[col][row] = ComplexNumber.add(m1.getMatrix()[col][row], m2.getMatrix()[col][row]);
			}
		}

		return new ComplexMatrix(m);
	}

	/**
	 * Computes the difference between two matrices (m1 - m2) and returns the result.
	 * Function adds m1 and -1 * m2.
	 * Returns null if:
	 *    - either matrix is null
	 *    - matrices are different sizes
	 * Any null elements in the matrices are treated as zeros.
	 * 
	 * @param m1 matrix 1
	 * @param m2 matrix 2
	 * @return difference m1-m2, or null in above situations
	 */
	public static ComplexMatrix subtract(ComplexMatrix m1, ComplexMatrix m2) {
		// Add function returns null if either parameter is null
		return add(m1, multiply(m2, -1));
	}

	public static ComplexMatrix multiply(ComplexMatrix m1, ComplexMatrix m2) {
		if(m1.getNumRows() != m2.getNumCols()) return null;

		ComplexNumber[][] m = new ComplexNumber[m1.getNumCols()][m2.getNumRows()];
		for(int col = 0; col < m1.getNumCols(); col++) {
			for(int row = 0; row < m2.getNumRows(); row++) {
				for(int indx = 0; indx < m1.getNumRows(); indx++) {
					m[col][row] = ComplexNumber.add(m[col][row], 
							ComplexNumber.multiply(m1.getMatrix()[col][indx], m2.getMatrix()[indx][row]));
				}
			}
		}
		
		return new ComplexMatrix(m);
	}

	public static ComplexMatrix multiply(ComplexMatrix m1, double c) {
		
		ComplexNumber[][] m = new ComplexNumber[m1.getNumCols()][m1.getNumRows()];
		for(int row = 0; row < m1.getNumRows(); row++) {
			for(int col = 0; col < m1.getNumCols(); col++) {
				m[col][row] = ComplexNumber.multiply(m1.getMatrix()[col][row], c);
			}
		}

		return new ComplexMatrix(m);
	}
	
	public static ComplexMatrix multiply(ComplexMatrix m1, ComplexNumber c) {
		
		ComplexNumber[][] m = new ComplexNumber[m1.getNumCols()][m1.getNumRows()];
		for(int row = 0; row < m1.getNumRows(); row++) {
			for(int col = 0; col < m1.getNumCols(); col++) {
				m[col][row] = ComplexNumber.multiply(m1.getMatrix()[col][row], c);
			}
		}

		return new ComplexMatrix(m);
	}
	
	public static ComplexMatrix pow(ComplexMatrix m1, int n) {
		if(m1.getNumRows() != m1.getNumCols()) return null;
		
		ComplexMatrix m = m1;
		for(int reps = 1; reps < n; reps++) {
			m = multiply(m, m1);
		}
		
		return m;
	}

	public static ComplexNumber determinant(ComplexMatrix m) {
		if(m.getNumCols() != m.getNumRows()) return null;
		
		ComplexNumber det = new ComplexNumber(0, 0);
		
		if(m.getNumCols() == 1) {
			return new ComplexNumber(m.getMatrix()[0][0]);
		}
		for(int col = 0; col < m.getNumCols(); col++) {
			
			ComplexNumber[][] sm = new ComplexNumber[m.getNumCols() - 1][m.getNumCols() - 1];
			for(int c = 0; c < m.getNumCols(); c++) {
				
				if(c == col) continue;
				
				for(int r = 1; r < m.getNumRows(); r++) {
					if(c < col) sm[c][r-1] = m.getMatrix()[c][r];
					else sm[c-1][r-1] = m.getMatrix()[c][r];
				}
			}
			
			if(col%2 == 0) det = ComplexNumber.add(det, 
					ComplexNumber.multiply(m.getMatrix()[col][0], determinant(new ComplexMatrix(sm))));
			else det = ComplexNumber.subtract(det, 
					ComplexNumber.multiply(m.getMatrix()[col][0], determinant(new ComplexMatrix(sm))));
			
		}
		
		return det;
	}
	
	public static ComplexNumber trace(ComplexMatrix m) {
		if(m.getNumCols() != m.getNumRows()) return null;
		
		ComplexNumber tr = new ComplexNumber(0, 0);
		for(int i = 0; i < m.getNumCols(); i++) {
			tr = ComplexNumber.add(tr, m.getMatrix()[i][i]);
		}
		return tr;
	}
	
	public static ComplexMatrix transpose(ComplexMatrix m) {
		ComplexNumber[][] tp = new ComplexNumber[m.getNumRows()][m.getNumCols()];
		for(int c = 0; c < m.getNumCols(); c++) {
			for(int r = 0; r < m.getNumRows(); r++) {
				tp[r][c] = m.getMatrix()[c][r];
			}
		}
		return new ComplexMatrix(tp);
	}
	
	private static ComplexNumber minor(ComplexMatrix m, int col, int row) {
		if(m.getNumCols() != m.getNumRows()) throw new IllegalArgumentException();
		
		ComplexNumber[][] sm = new ComplexNumber[m.getNumCols() - 1][m.getNumCols() - 1];
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
		return determinant(new ComplexMatrix(sm));
	}
	
	private static ComplexMatrix matrix_of_minors(ComplexMatrix m) {
		if(m.getNumCols() != m.getNumRows()) throw new IllegalArgumentException();
		
		ComplexNumber[][] minors = new ComplexNumber[m.getNumCols()][m.getNumRows()];
		for(int c = 0; c < m.getNumCols(); c++) {
			for(int r = 0; r < m.getNumRows(); r++) {
				minors[c][r] = minor(m, c, r);
			}
		}
		
		return new ComplexMatrix(minors);
	}
	
	private static ComplexMatrix matrix_of_cofactors(ComplexMatrix m) {
		ComplexMatrix minors = matrix_of_minors(m);
		for(int c = 0; c < m.getNumCols(); c++) {
			for(int r = 0; r < m.getNumRows(); r++) {
				if((c+r) % 2 != 0) minors.getMatrix()[c][r] = ComplexNumber.multiply(minors.getMatrix()[c][r], -1);
			}
		}
		return minors;
	}
	
	private static ComplexMatrix adjoint(ComplexMatrix m) {
		return transpose(matrix_of_cofactors(m));
	}
	
	public static ComplexMatrix inverse(ComplexMatrix m) {
		if(determinant(m).equals(new ComplexNumber(0, 0))) return null;
		return multiply(adjoint(m), ComplexNumber.divide(1, determinant(m)));
	}
}
