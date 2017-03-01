import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * TODO:
 * 		1. Add to Expression Reader
 * 			a. inverse
 * 			b. power
 * 			c. 
 * 		2. Allow uni-matrix operations in Expression Reader
 * 		3. 
 * 
 * 
 */


public class Calculator extends JFrame {

	/** JPanels */
	private JPanel panelMain;
	private JPanel panelForm;

	/** Matrix Input Spaces */
	private JLabel matrixAlabel;
	private ArrayList<JTextField> matrixAInputFields;
	private int matrixArows;
	private int matrixAcols;
	private JLabel matrixBlabel;
	private ArrayList<JTextField> matrixBInputFields;
	private int matrixBrows;
	private int matrixBcols;

	/** Row Change Components */
	private JLabel rowChangeLabelA;
	private JButton rowIncButtonA;
	private JButton rowDecButtonA;
	private JLabel rowChangeLabelB;
	private JButton rowIncButtonB;
	private JButton rowDecButtonB;

	/** Col Change Components */
	private JLabel colChangeLabelA;
	private JButton colIncButtonA;
	private JButton colDecButtonA;
	private JLabel colChangeLabelB;
	private JButton colIncButtonB;
	private JButton colDecButtonB;

	/** Buttons - Multi-Matrix */
	private JButton addMatrixButton;
	private JButton subMatrixButton;
	private JButton mulMatrixButton;

	/** Buttons - Uni-Matrix */
	private JButton clrAButton;
	private JButton clrBButton;
	private JButton detAButton;
	private JButton detBButton;
	private JButton invAButton;
	private JButton invBButton;
	private JButton traceAButton;
	private JButton traceBButton;
	private JButton transposeAButton;
	private JButton transposeBButton;

	/** Manual Expression Input */
	private JTextField manualExpressionField;
	private JButton manualExpressionEqualsButton;
	
	/** Insets */
	private Insets defaultInsets;
	private Insets multiMatrixInsets;

	public Calculator() {
		initComponents();
		updateGUI();

		setTitle("Matrix Calculator");
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initComponents() {
		panelMain = new JPanel();
		getContentPane().add(panelMain);

		panelForm = new JPanel(new GridBagLayout());
		panelMain.add(panelForm);

		matrixAlabel = new JLabel("[A]");
		matrixAInputFields = new ArrayList<JTextField>();
		for(int i = 0; i < 9; i++) matrixAInputFields.add(new JTextField(2));
		matrixArows = 3;
		matrixAcols = 3;
		matrixBlabel = new JLabel("[B]");
		matrixBInputFields = new ArrayList<JTextField>();
		for(int i = 0; i < 9; i++) matrixBInputFields.add(new JTextField(2));
		matrixBrows = 3;
		matrixBcols = 3;

		rowChangeLabelA = new JLabel("Rows");
		colChangeLabelA = new JLabel("Cols");
		rowChangeLabelB = new JLabel("Rows");
		colChangeLabelB = new JLabel("Cols");

		rowIncButtonA = new JButton("+");
		rowIncButtonA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				matrixArows ++;
				for(int i = 0; i < matrixAcols; i++) matrixAInputFields.add(new JTextField(2));
				updateGUI();
			}
		});
		setButtonSize(rowIncButtonA, 25, 25);

		rowIncButtonB = new JButton("+");
		rowIncButtonB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				matrixBrows ++;
				for(int i = 0; i < matrixBcols; i++) matrixBInputFields.add(new JTextField(2));
				updateGUI();
			}
		});
		setButtonSize(rowIncButtonB, 25, 25);

		rowDecButtonA = new JButton("-");
		rowDecButtonA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(matrixArows == 1) return;
				matrixArows --;
				for(int i = 0; i < matrixAcols; i++) matrixAInputFields.remove(0);
				updateGUI();
			}
		});
		setButtonSize(rowDecButtonA, 25, 25);

		rowDecButtonB = new JButton("-");
		rowDecButtonB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(matrixBrows == 1) return;
				matrixBrows --;
				for(int i = 0; i < matrixBcols; i++) matrixBInputFields.remove(0);
				updateGUI();
			}
		});
		setButtonSize(rowDecButtonB, 25, 25);

		colIncButtonA = new JButton("+");
		colIncButtonA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				matrixAcols ++;
				for(int i = 0; i < matrixArows; i++) matrixAInputFields.add(new JTextField(2));
				updateGUI();
			}
		});
		setButtonSize(colIncButtonA, 25, 25);

		colIncButtonB = new JButton("+");
		colIncButtonB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				matrixBcols ++;
				for(int i = 0; i < matrixBrows; i++) matrixBInputFields.add(new JTextField(2));
				updateGUI();
			}
		});
		setButtonSize(colIncButtonB, 25, 25);

		colDecButtonA = new JButton("-");
		colDecButtonA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(matrixAcols == 1) return;
				matrixAcols --;
				for(int i = 0; i < matrixArows; i++) matrixAInputFields.remove(0);
				updateGUI();
			}
		});
		setButtonSize(colDecButtonA, 25, 25);

		colDecButtonB = new JButton("-");
		colDecButtonB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(matrixBcols == 1) return;
				matrixBcols --;
				for(int i = 0; i < matrixBrows; i++) matrixBInputFields.remove(0);
				updateGUI();
			}
		});
		setButtonSize(colDecButtonB, 25, 25);

		detAButton = new JButton("det(A)");
		detAButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!checkMatrixA())
					displayErrorMessage("Please fill all elements\nwith numeric values");
				else if(Matrix.determinant(getMatrixA()) == null)
					displayErrorMessage("A must be square");
				else
					displaySolution(Matrix.determinant(getMatrixA()));
			}

		});

		detBButton = new JButton("det(B)");
		detBButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!checkMatrixB())
					displayErrorMessage("Please fill all elements\nwith numeric values");
				else if(Matrix.determinant(getMatrixB()) == null)
					displayErrorMessage("B must be square");
				else
					displaySolution(Matrix.determinant(getMatrixB()));
			}

		});

		invAButton = new JButton("A^-1");
		invAButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!checkMatrixA())
					displayErrorMessage("Please fill all elements\nwith numeric values");
				else if(Matrix.inverse(getMatrixA()) == null)
					displayErrorMessage("A^-1 does not exist");
				else
					displaySolution(Matrix.inverse(getMatrixA()));
			}
		});

		invBButton = new JButton("B^-1");
		invBButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!checkMatrixB())
					displayErrorMessage("Please fill all elements\nwith numeric values");
				else if(Matrix.inverse(getMatrixB()) == null)
					displayErrorMessage("B^-1 does not exist");
				else
					displaySolution(Matrix.inverse(getMatrixB()));
			}
		});

		traceAButton = new JButton("trace(A)");
		traceAButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!checkMatrixA())
					displayErrorMessage("Please fill all elements\nwith numeric values");
				else if(Matrix.trace(getMatrixA()) == null)
					displayErrorMessage("A must be square");
				else
					displaySolution(Matrix.trace(getMatrixA()));
			}
		});

		traceBButton = new JButton("trace(B)");
		traceBButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!checkMatrixB())
					displayErrorMessage("Please fill all elements\nwith numeric values");
				else if(Matrix.trace(getMatrixB()) == null)
					displayErrorMessage("B must be square");
				else
					displaySolution(Matrix.trace(getMatrixB()));
			}
		});

		transposeAButton = new JButton("A^T");
		transposeAButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!checkMatrixA())
					displayErrorMessage("Please fill all elements\nwith numeric values");
				else
					displaySolution(Matrix.transpose(getMatrixA()));
			}
		});

		transposeBButton = new JButton("B^T");
		transposeBButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!checkMatrixB())
					displayErrorMessage("Please fill all elements\nwith numeric values");
				else
					displaySolution(Matrix.transpose(getMatrixB()));
			}
		});

		/**
		 * Multi-Matrix Buttons
		 */

		addMatrixButton = new JButton("A + B");
		addMatrixButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(checkMatrixA() && checkMatrixB()))
					displayErrorMessage("Please fill all elements\nwith numeric values");
				else if(Matrix.add(getMatrixA(), getMatrixB()) == null)
					displayErrorMessage("A and B must be\nthe same size");
				else
					displaySolution(Matrix.add(getMatrixA(), getMatrixB()));
			}
		});

		subMatrixButton = new JButton("A - B");
		subMatrixButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(checkMatrixA() && checkMatrixB()))
					displayErrorMessage("Please fill all elements\nwith numeric values");
				else if(Matrix.subtract(getMatrixA(), getMatrixB()) == null)
					displayErrorMessage("A and B must be\nthe same size");
				else
					displaySolution(Matrix.subtract(getMatrixA(), getMatrixB()));
			}
		});

		mulMatrixButton = new JButton("A * B");
		mulMatrixButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(checkMatrixA() && checkMatrixB()))
					displayErrorMessage("Please fill all elements\nwith numeric values");
				else if(Matrix.add(getMatrixA(), getMatrixB()) == null)
					displayErrorMessage("The number of columns in A must be\n"
							+ "equal to the number of rows in B");
				else
					displaySolution(Matrix.multiply(getMatrixA(), getMatrixB()));
			}
		});

		clrAButton = new JButton("reset(A)");
		clrAButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(JTextField t : matrixAInputFields) t.setText("");
				updateGUI();
			}
		});
		
		clrBButton = new JButton("reset(B)");
		clrBButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(JTextField t : matrixBInputFields) t.setText("");
				updateGUI();
			}
		});
		
		/**
		 * Manual Expression
		 */
		manualExpressionField = new JTextField(19);
		manualExpressionField.setText("2*(A+B)");
		manualExpressionEqualsButton = new JButton("=");
		manualExpressionEqualsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readManualExpression(manualExpressionField.getText());
				updateGUI();
			}
		});
		setButtonSize(manualExpressionEqualsButton, 25, 25);
		
		/**
		 * Insets
		 */
		defaultInsets = new Insets(0, 0, 0, 0);
		multiMatrixInsets = new Insets(0, 8, 0, 8);
	}

	private void updateGUI() {
		panelForm.removeAll();
		if(matrixArows*matrixAcols != matrixAInputFields.size()) throw new IllegalArgumentException();

		GridBagConstraints c = new GridBagConstraints();
		int tempAcols = matrixAcols;
		int tempBcols = matrixBcols;
		if(tempAcols < 3) tempAcols = 3;
		if(tempBcols < 3) tempBcols = 3;

		/** Row Change Interface */
		c.gridx = ((tempAcols-1)/2)-1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = defaultInsets;
		panelForm.add(rowDecButtonA, c);
		c.gridx ++;
		c.gridwidth = ((tempAcols+1)%2)+1;
		panelForm.add(rowChangeLabelA, c);
		c.gridx += c.gridwidth;
		c.gridwidth = 1;
		panelForm.add(rowIncButtonA, c);
		c.gridx += ((tempBcols-1)/2) + (tempAcols-1)/2;
		panelForm.add(rowDecButtonB, c);
		c.gridx ++;
		c.gridwidth = ((tempBcols+1)%2)+1;
		panelForm.add(rowChangeLabelB, c);
		c.gridx += c.gridwidth;
		c.gridwidth = 1;
		panelForm.add(rowIncButtonB, c);

		/** Col Change Interface */
		c.gridx = ((tempAcols-1)/2)-1;
		c.gridy ++;
		c.gridwidth = 1;
		panelForm.add(colDecButtonA, c);
		c.gridx ++;
		c.gridwidth = ((tempAcols+1)%2)+1;
		panelForm.add(colChangeLabelA, c);
		c.gridx += c.gridwidth;
		c.gridwidth = 1;
		panelForm.add(colIncButtonA, c);
		c.gridx += ((tempBcols-1)/2) + (tempAcols-1)/2;
		panelForm.add(colDecButtonB, c);
		c.gridx ++;
		c.gridwidth = ((tempBcols+1)%2)+1;
		panelForm.add(colChangeLabelB, c);
		c.gridx += c.gridwidth;
		c.gridwidth = 1;
		panelForm.add(colIncButtonB, c);

		/** Matrix Labels */
		c.gridx = ((tempAcols-1)/2);
		c.gridy ++;
		c.gridwidth = ((tempAcols+1)%2)+1;
		panelForm.add(matrixAlabel, c);
		c.gridx += ((tempAcols + 1) - c.gridx + ((tempBcols-1)/2));
		c.gridwidth = ((tempBcols+1)%2)+1;
		panelForm.add(matrixBlabel, c);

		/** Matrix A */
		c.gridx = 0;
		c.gridy ++;
		c.gridwidth = 1;
		for(int row = 0; row < matrixArows; row++) {
			for(int col = 0; col < matrixAcols; col++) {
				panelForm.add(matrixAInputFields.get(row*matrixAcols + col), c);
				c.gridx ++;
			}
			c.gridx = 0;
			c.gridy ++;
		}

		/** Multi-Matrix Buttons */
		c.gridx = tempAcols;
		c.gridy = 3;
		c.gridwidth = 1;
		c.insets = multiMatrixInsets;
		panelForm.add(addMatrixButton, c);
		c.gridy ++;
		panelForm.add(subMatrixButton, c);
		c.gridy ++;
		panelForm.add(mulMatrixButton, c);

		/** Matrix B */
		c.gridx = tempAcols +1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.insets = defaultInsets;
		for(int row = 0; row < matrixBrows; row++) {
			for(int col = 0; col < matrixBcols; col++) {
				panelForm.add(matrixBInputFields.get(row*matrixBcols + col), c);
				c.gridx ++;
			}
			c.gridx = tempAcols +1;
			c.gridy ++;
		}

		/** Uni-Matrix Buttons */
		c.gridx = ((tempAcols-3)/2);
		c.gridy = 3 + matrixArows;
		c.gridwidth = 3 + ((tempAcols+1)%2);
		panelForm.add(clrAButton, c);
		c.gridy ++;
		panelForm.add(detAButton, c);
		c.gridy ++;
		panelForm.add(traceAButton, c);
		c.gridy ++;
		panelForm.add(transposeAButton, c);
		c.gridy ++;
		panelForm.add(invAButton, c);

		c.gridx += (tempAcols - c.gridx) + ((tempBcols-1)/2);
		c.gridy = 3 + matrixBrows;
		c.gridwidth = 3 + ((tempBcols+1)%2);
		panelForm.add(clrBButton, c);
		c.gridy ++;
		panelForm.add(detBButton, c);
		c.gridy ++;
		panelForm.add(traceBButton, c);
		c.gridy ++;
		panelForm.add(transposeBButton, c);
		c.gridy ++;
		panelForm.add(invBButton, c);
		
		/** Manual Expression */
		String currExpression = manualExpressionField.getText();
		manualExpressionField = new JTextField(1 + tempAcols*3 + tempBcols*3);
		manualExpressionField.setText(currExpression);
		c.gridy ++;
		c.gridx = 0;
		c.gridwidth = tempAcols + tempBcols;
		panelForm.add(manualExpressionField, c);
		c.gridx += c.gridwidth;
		c.gridwidth = 1;
		panelForm.add(manualExpressionEqualsButton, c);
		
		/** Update Changes */
		panelForm.revalidate();
		pack();
	}

	public Matrix getMatrixA() {
		double[][] m = new double[matrixAcols][matrixArows];
		for(int row = 0; row < matrixArows; row++) {
			for(int col = 0; col < matrixAcols; col++) {
				try{
					m[col][row] = Double.parseDouble(matrixAInputFields.get(row*matrixAcols + col).getText());
				} catch(NumberFormatException nfe) {
					displayErrorMessage("Please Fill Matrix A");
					return null;
				}
			}
		}
		return new Matrix(m);
	}

	public Matrix getMatrixB() {
		double[][] m = new double[matrixBcols][matrixBrows];
		for(int row = 0; row < matrixBrows; row++) {
			for(int col = 0; col < matrixBcols; col++) {
				try{
					m[col][row] = Double.parseDouble(matrixBInputFields.get(row*matrixBcols + col).getText());
				} catch(NumberFormatException nfe) {
					displayErrorMessage("Please Fill Matrix B");
					return null;
				}
			}
		}
		return new Matrix(m);
	}

	public boolean checkMatrixA() {
		for(JTextField f : matrixAInputFields) {
			try {
				Double.parseDouble(f.getText());
			} catch(NumberFormatException nfe) {
				return false;
			}
		}
		return true;
	}

	public boolean checkMatrixB() {
		for(JTextField f : matrixBInputFields) {
			try {
				Double.parseDouble(f.getText());
			} catch(NumberFormatException nfe) {
				return false;
			}
		}
		return true;
	}

	public void addHorizontalGap(int n, GridBagConstraints c) {
		if(n < 1) return;
		for(int i = 1; i < n; i++) {
			panelForm.add(new JLabel(" "));
			c.gridx ++;
		}
		panelForm.add(new JLabel(" "));
	}

	public void addVerticalGap(int n, GridBagConstraints c) {
		if(n < 1) return;
		for(int i = 0; i < n; i++) {
			panelForm.add(new JLabel(" "));
			c.gridy ++;
		}
		panelForm.add(new JLabel(" "));
	}
	
	public void readManualExpression(String expression) {
		if(expression == null || expression.trim().equals("")) {
			displayErrorMessage("Please input a valid expression");
			return;
		}
		else if(!(expression.contains("A") || expression.contains("B"))) {
			displayErrorMessage("The expression must involve\neither matrix A or matrix B");
			return;
		}
		
		ExpressionReader er;
		try{
			er = new ExpressionReader(expression, getMatrixA(), getMatrixB());
			Object soln = er.getSolution();
			if(soln instanceof Double) displaySolution((Double) soln);
			else if(soln instanceof Matrix) displaySolution((Matrix) soln);
		} catch(IllegalArgumentException e) {
			displayErrorMessage(e.getMessage());
		}
	}
	
	public void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public void displaySolution(Matrix solution) {
		JOptionPane.showMessageDialog(null, solution.toString());
	}

	public void displaySolution(double solution) {
		JOptionPane.showMessageDialog(null, solution);
	}

	public void setButtonSize(JButton b, int h, int w) {
		b.setMaximumSize(new Dimension(h, w));
		b.setMinimumSize(new Dimension(h, w));
		b.setPreferredSize(new Dimension(h, w));
	}

	public static void main(String[] args) {
		Calculator calc = new Calculator();
		calc.pack();
		calc.setVisible(true);
	}

	public static final int SCREEN_WIDTH = 700;
	public static final int SCREEN_HEIGHT = 700;
}
