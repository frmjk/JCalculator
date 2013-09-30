package com.LOIhogeschool.CalculatorJava;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * <p>Calculator </p>
 * 
 * <p>Copyright: This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.<br>
 * 2013.</p>
 * 
 * @author FJ Mujica
 * @version: 1.0
 *
 */

public class Calculator extends JFrame implements ActionListener, KeyListener{


/**
 *@serial 
 */
	private static final long serialVersionUID = 1L;
	
/**Menu bar */
	MenuBar mb;
/** Menu titles */
	Menu View,Edit, Help;
/** Menu Items Copy, Paste, Help, About. */
	MenuItem Copy,Paste, HelpTopics, About;
/** Checkboxes: Standard or Scientific */
	CheckboxMenuItem Standard, Scientific;
/**The display of the calculator*/	
	JLabel Display;
/**Shows "M" if there is a stored number in the memory*/	
	JButton MemoryDisplay;
/** Panels */
	Panel Middle, Top;
/** Layout used in the buttons */
	GridLayout GL;
/** The Buttons from 0 to 9 and the operator buttons */
	JButton OperatorButton[];
/** Backspace, CE and C */
	JButton Backspace, CE, C;
/** The input is allowed */
	private final int INPUT_MODE = 0;
/** The calculation was done, the result is on */
	private final int RESULT_MODE = 1;
/** An error occurred in calculating */
	private final int ERROR_MODE = 2;
/** May be one of the three INPUT_MODE, RESULT_MODE or ERROR_MODE */
	private int displayMode;
/** True if the display must be clean */
	private boolean clearOnNextDigit;
/** Stores the last number that was in the display before */
	private double lastNumber;
/** Stores the last operator */
	private String lastOperator = "0";
		/*
		 * Give lastOperator a default value of "0" because the object String cannot be null.
		 * (in other words: It MUST be initialized).
		 * If lastOperator == "null", then the method processOperator() will 
		 * throw a runtime exception of type NullPointerException.  
		 */
/**Stores the number in the Memory*/
	private double memoryNumber = 0.0D;
/**Label for empty memory*/
	private String empty = "     ";
/**Label for full memory*/
	private String full = "  M  ";
 	

/**
 * CONSTRUCTOR
 * It Set ups the GUI 
 * @param String 's' - this string is the Calculator title.
 */	
	Calculator(String s){
		super(s);

        	
	        setSize(450, 250);
	        setResizable(false);
	        setLocation(400,250);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		  	/**
		  	 * Sets the fonts
		  	 */
	        Font font = new Font("Dialog", 1, 14);
			Font bFont = new Font("Dialog",1,14);
			Font lFont = new Font("Dialog",3,16);
		
			//Start constructing the MenuBar	
			mb = new MenuBar();
	        setMenuBar(mb);
	
        	Edit = new Menu("Edit");
        	Copy = new MenuItem("Copy", new MenuShortcut(67));
	        Paste = new MenuItem("Paste", new MenuShortcut(86));
	        View = new Menu("View");
        	Standard = new CheckboxMenuItem("Standard", true);
	        Scientific = new CheckboxMenuItem("Scientific", false);
        	Help = new Menu("Help");
        	HelpTopics = new MenuItem("Help Topics");//F1 = 112
	        About = new MenuItem("About Calculator");//F11 = 122

	        Edit.add(Copy);
	        Edit.add(Paste);
        	mb.add(Edit);

	        View.add(Standard);
        	View.add(Scientific);
	        View.addSeparator();
        	
	        Standard.setEnabled(false);
        	Scientific.setEnabled(false);
	        
        	mb.add(View);

	        Help.add(HelpTopics);
        	Help.addSeparator();
	        Help.add(About);
        	mb.add(Help);
        //end Menu
        	
	    Display = new JLabel("0", 4);
	    Display.setOpaque(true);
	    Display.setBackground(Color.WHITE);
        	
		Display.setFont(lFont);
        add(Display, BorderLayout.NORTH);

	        
        JButton space = new JButton("");
        space.setBorder(BorderFactory.createEmptyBorder(0,0,0,120));
        	
        	
        	MemoryDisplay= new JButton(empty);
	        MemoryDisplay.setEnabled(false);
	        MemoryDisplay.setFont(font);
	        
	        Middle = new Panel();
        	add(Middle, BorderLayout.CENTER);
        	
        	Middle.add(MemoryDisplay);
        	Middle.add(space);
	        Backspace = new JButton("Backspace");
        	
		Backspace.setFont(bFont);
        	Middle.add(Backspace);

	        CE = new JButton("CE");
        	
		CE.setFont(bFont);
        	Middle.add(CE);

	        C = new JButton("C");
        	
		C.setFont(bFont);
        	Middle.add(C);

	        Top = new Panel();
	        Top.setSize(450, 250);
        	GL = new GridLayout(4, 6, 5, 5);
        	
	        Top.setLayout(GL);
        	Middle.add(Top);

	      	
	        OperatorButton = new JButton[24];
        	String as[] = {"MC", "7", "8", "9", "/", "sqrt", "MR", "4", "5", "6", "*", "%", "MS", "1", "2", "3", "-", "1/x", "M+", "0", "+/-", ".", "+", "="};
	        
        	     	
        		        	
        	for(int i = 0; i < OperatorButton.length; i++){
        		OperatorButton[i] = new JButton(as[i]);
        		OperatorButton[i].setFont(bFont);
        	    OperatorButton[i].addActionListener(this);
		        OperatorButton[i].addKeyListener(this);
		        Top.add(OperatorButton[i]);
	        }
        	
        	OperatorButton[0].setForeground(Color.red);
        	OperatorButton[6].setForeground(Color.red);
        	OperatorButton[12].setForeground(Color.red);
        	OperatorButton[18].setForeground(Color.red);
	
           	Copy.addActionListener(this);
	        Paste.addActionListener(this);
        	About.addActionListener(this);
        	HelpTopics.setEnabled(false);
	
        	C.addActionListener(this);
	        C.addKeyListener(this);
	
        	CE.addActionListener(this);
        	CE.addKeyListener(this);
	
        	Backspace.addActionListener(this);
        	Backspace.addKeyListener(this);

	        setVisible(true);
	}//end of constructor.        
	

public static void main(String args[]){
	Calculator c = new Calculator("Calculator");
}//end of main



/**
*@see <a href="http://docs.oracle.com/javase/6/docs/api/java/awt/event/KeyListener.html#keyPressed(java.awt.event.KeyEvent)">keyPressed</a>
**/
@Override 
public void keyPressed(KeyEvent keyevent){	
	/**
	 * holds the keyCode from the keyevent object.
	 */
	int i = keyevent.getKeyCode();
   	/*
   	 * getKeyCode from class KeyEvent returns the integer KeyCode 
   	 * associated with the key in 'this' event.
   	 * int i will hold the keyCode from the pressed key, and then 
   	 * after an if test, it will assign a click to the matching button. 
   	 */
   	if(i == 107){// plus
	OperatorButton[22].doClick();
    } 
else if(i == 45 || i == 109){// minus
    OperatorButton[16].doClick();
	} 
else if(i == 106){// multiplication
	OperatorButton[10].doClick();
    } 
else if(i == 111 || i == 47){// division
	OperatorButton[4].doClick();
	} 
else if(i == 10 || i == 61){//Enter
	OperatorButton[23].doClick();
	} 
else if(i == 48 || i == 96){//0
    OperatorButton[19].doClick();
}
else if(i == 49 || i == 97){//1
	OperatorButton[13].doClick();
	}
else if(i == 50 || i == 98){//2
    OperatorButton[14].doClick();
}
else if(i == 51 || i == 99){//3
    OperatorButton[15].doClick();
}
else if(i == 52 || i == 100){//4
    OperatorButton[7].doClick();
}
else if(i == 53 || i == 101){//5
    OperatorButton[8].doClick();
}
else if(i == 54 || i == 102){//6
    OperatorButton[9].doClick();
}
else if(i == 55 || i == 103){//7
    OperatorButton[1].doClick();
}
else if(i == 56 || i == 104){//8
	OperatorButton[2].doClick();
}
else if(i == 57 || i == 105){//9
	OperatorButton[3].doClick();
}
else if(i == 110 || i == KeyEvent.VK_COMMA || i == 46){//, or .
	OperatorButton[21].doClick();
}
else if(i == 8){//Backspace
	Backspace.doClick();
}
else if(i == 27){//Esc
	clearAll();
}
else if(i == 65485){//Ctrl + c
    // Copy method
	} 
else if(i == 65487){//Ctrl + V
    // Paste method.
	}
else if(i == KeyEvent.VK_R){// 1/x
	OperatorButton[17].doClick();
}
else if(i == KeyEvent.VK_F9){// +/-
	OperatorButton[20].doClick();
}
else if(i == KeyEvent.VK_DELETE){// CE
	
}
else if(i == KeyEvent.VK_F11){// About Calculator
	JDialog dlgAbout = new AboutCalculator(this,"About Calculator", true);
			dlgAbout.setVisible(true);;
}
else if(i == KeyEvent.VK_P){// %
	OperatorButton[11].doClick();
}
else if(i == KeyEvent.VK_O){// MS
	OperatorButton[12].doClick();
}
else if(i == KeyEvent.VK_M){// M+
	OperatorButton[18].doClick();
}
else if(i == KeyEvent.VK_A){// MR
	OperatorButton[6].doClick();
}
else if(i == KeyEvent.VK_L){// MC
	OperatorButton[0].doClick();
}
else if(i == KeyEvent.VK_S){//sqrt
	OperatorButton[5].doClick();
}
else if(i == KeyEvent.VK_HOME){//C
	clearAll();
}
else{

}
}//END OF KEY PRESSED

/**
 * @see <a href="http://docs.oracle.com/javase/6/docs/api/java/awt/event/ActionListener.html#actionPerformed(java.awt.event.ActionEvent)">actionPerformed</a>
 */
@Override
public void actionPerformed(ActionEvent actionevent){
	double result = 0;	
    String s = (String) (actionevent.getActionCommand()); 
   /*
    *getActionCommand returns the command string associated with this action.
    *The program will "read" the string in the button text, and will use it to 
    *to compare which key trigged the actionEvent. 
    */		   
     	
    if(s.equals("C")){// C
			clearAll();	
        } 
	else if(s.equals("Copy")){// Ctrl + C
        // Copy code
    	} 
	else if(s.equals("Paste")){// Ctrl + V
    	//Paste code
    	} 
	else if(s.equals("CE")){// CE
		clearExisting();
	} 
	else if(s.equals("Backspace")){//Backspace
		if (displayMode != ERROR_MODE) {
			setDisplayString(getDisplayString().substring(0,
					getDisplayString().length() - 1));

			if (getDisplayString().length() < 1)
				setDisplayString("0");
		}
	}    
	else if(s.equals("About Calculator")){//About Calculator
		JDialog dlgAbout = new AboutCalculator(this,"About Calculator", true);
			dlgAbout.setVisible(true);
	}    	
    else if(s.equals("MS")){//MS
    	if (displayMode != ERROR_MODE){
    		try {
    			memorySave(getNumberInDisplay());
    		}
    		catch (Exception ex){
    			
    		}
    	}
     }    
	else if(s.equals("M+")){//M+
        if(displayMode != ERROR_MODE) {
		memoryPlus(getNumberInDisplay());
        }
    } 
	else if(s.equals("MC")){//MC
        clearMemory();
    } 
	else if(s.equals("MR")){//MR
		memoryRedisplay();
	}
    else if(s.equals("sqrt")){//sqrt
    	if (displayMode != ERROR_MODE) {
    		try {
    			sqrtResult(getNumberInDisplay());
    		}
    		catch (Exception ex) {
    			displayError("Ongeldige invoer voor deze functie!");
    			displayMode = ERROR_MODE;
    			
    		}
    	}
    } 
	else if(s.equals("%")){//%
		if (displayMode != ERROR_MODE) {
			calculatesPercent(getNumberInDisplay());				
			}			
    } 
	else if(s.equals("1/x")){// 1/x
		if (displayMode != ERROR_MODE) {
			try {
				xAsDenominator(getNumberInDisplay());
			}
			catch (Exception ex){
				displayError("Je kunt niet delen door nul!");
				displayMode = ERROR_MODE;
			}
		}
    } 
	else if(s.equals("+")){// plus
		processOperator(s);
    }    	
    else if(s.equals("-")){//minus 
    	processOperator(s);
    } 
	else if(s.equals("*")){// multiplication
    	processOperator(s);
    } 
	else if(s.equals("/")){//division
		processOperator(s);
    } 	
	else if(s.equals("+/-")){// +/-
		processSignChange();
    } 
	else if(s.equals("=")){//Equals
		processEquals();
    }    	
	else if(s.equals(".")){//Decimal Point
		addDecimalPoint();
	}
	else{//numbers
		addDigitToDisplay(Integer.parseInt(s)); 
		/*
		 * To get the Numbers I simply addDigitToDisplay the string from the button title parsed as an int.
		 */
    	}
}//END OF ACTION PERFORMED

/**
* @see <a href="http://docs.oracle.com/javase/6/docs/api/java/awt/event/KeyListener.html#keyReleased(java.awt.event.KeyEvent)">heyReleased</a>
*/
@Override 
public void keyReleased(KeyEvent keyevent){

}
/**
* @see <a href="http://docs.oracle.com/javase/6/docs/api/java/awt/event/KeyListener.html#keyTyped(java.awt.event.KeyEvent)">keyTyped</a>
*/
@Override
public void keyTyped(KeyEvent keyevent){

}


/*Begin Methods for the Calculus operation */

/**
 * Inserts a digit in the display.
 * @param: int digit - this int will add to the display following the last digit in the calculator, unless <code>clearOnNextDigit</code> is <code>true</code>.
 */
private void addDigitToDisplay(int digit) {
	if (clearOnNextDigit)
		setDisplayString("");

	String inputString = getDisplayString();

	if (inputString.indexOf("0") == 0) {
		inputString = inputString.substring(1);
	}

	if ((!inputString.equals("0") || digit > 0)){
			 
			
		setDisplayString(inputString + digit);
	}

	displayMode = INPUT_MODE;
	clearOnNextDigit = false;
}

/**
 * Gets the display text as a double.
 * @return the number in the display as a <code>double</code>
 */
private double getNumberInDisplay() {
	String numberinDisplay = Display.getText();
	return Double.parseDouble(numberinDisplay);
}

/**
 * Puts the string passed to it on the Display
 * @param s - the <code>string</code> that is shown in the Display.
 */
private void setDisplayString(String s) {
	Display.setText(s);
}
/**
 * 
 * Gets the number in the Display.
 * @return the <code>string</code> in the display.
 */
private String getDisplayString() {
	return Display.getText();
}


/**
 * Handles the operation while the user keeps pressing the operation buttons.
 * 	This way, if the user presses a "+" or "-" sign after one operation, the calculator will keep on going.
 *	It Simply passes the number and operation to the <code>processLastOperator</code> method.
 *@param op - the <code>String</code> that corresponds with the operator sign ('+' '-' '/' '*') 
 *
 */
private void processOperator(String op) {
	if (displayMode != ERROR_MODE) {
		double numberInDisplay = getNumberInDisplay();

		if (!lastOperator.equals("0")) {
			try {
				double result = processLastOperator();
				displayResult(result);
				lastNumber = result;
			}
			catch (DivideByZeroException e) {
			}
		}

		else {
			lastNumber = numberInDisplay;
		}

		clearOnNextDigit = true;
		lastOperator = op;
	}
}

/**
 *Does the actual calculation.
 *It's not trigged by any key pressed by the user. It is only called by other methods.
 *@return the result of the calculation.
 *@throws DivideByZeroException
 */
private double processLastOperator() throws DivideByZeroException {
	double result = 0;
	double numberInDisplay = getNumberInDisplay();

	if ((lastOperator=="/")) {
		if (numberInDisplay == 0)
			throw (new DivideByZeroException());

		result = lastNumber / numberInDisplay;
	}

	if ((lastOperator=="*"))
		result = lastNumber * numberInDisplay;

	if ((lastOperator=="-"))
		result = lastNumber - numberInDisplay;

	if ((lastOperator=="+"))
		result = lastNumber + numberInDisplay;

	return result;
}

/**
 * Display the Result of the operation
 * @param result - gets the result of the operation and sets it in the display.   
 */
private void displayResult(double result) {
	setDisplayString(Double.toString(result));
	lastNumber = result;
	displayMode = RESULT_MODE;
	clearOnNextDigit = true;
}

/**
 * Inserts Decimal point in the Display.
 */
private void addDecimalPoint() {
	displayMode = INPUT_MODE;

	if (clearOnNextDigit)
		setDisplayString("");

	String inputString = getDisplayString();

	// If the input string already contains a decimal point, don't
	// do anything to it.
	if (inputString.indexOf(".") < 0)
		setDisplayString(new String(inputString + "."));
}

/**
 * Cleans the display and current calculation.
 */
private void clearAll() {
	setDisplayString("0");
	lastOperator = "0";
	lastNumber = 0;
	displayMode = INPUT_MODE;
	clearOnNextDigit = true;
}

/**
 * Cleans the display.
 */
private void clearExisting() {
	setDisplayString("0");
	clearOnNextDigit = true;
	displayMode = INPUT_MODE;
}

/**
 * Shows an error message in the display and sets the state of the
 * Calculator.
 * @param errorMessage - a <code>String</code> with the error of the message.
 */
private void displayError(String errorMessage) {
	setDisplayString(errorMessage);
	lastNumber = 0;
	displayMode = ERROR_MODE;
	clearOnNextDigit = true;
}

/**
 * Calculates when "=" is pressed
 */
private void processEquals() {
	double result = 0;
	if (displayMode != ERROR_MODE) {
		try {
			result = processLastOperator();
			displayResult(result);
		}

		catch (DivideByZeroException e) {
			displayError("Je kunt niet delen door nul!");
		}

		lastOperator = "0";
	}
}	
	
/**
 * Changes the sign (+/-) on the display.
 */
private void processSignChange() {
	if (displayMode == INPUT_MODE) {
		String input = getDisplayString();

		if (input.length() > 0 && !input.equals("0")) {
			if (input.indexOf("-") == 0)
				setDisplayString(input.substring(1));

			else
				setDisplayString("-" + input);
		}

	}

	else if (displayMode == RESULT_MODE) {
		double numberInDisplay = getNumberInDisplay();

		if (numberInDisplay != 0)
			displayResult(-numberInDisplay);
	}
}

/**
 * Calculates the Square Root of the number in the display.
 * @param d - the <code>double</code> whose square root will be calculated.
 * @throws SqrtOfNegativeException
 */
public void sqrtResult(double d) throws SqrtOfNegativeException {
double result;	
	if (displayMode != ERROR_MODE) {
			if (getDisplayString().indexOf("-") == 0)
				throw (new SqrtOfNegativeException());						

			result = Math.sqrt(getNumberInDisplay());
			displayResult(result);		
	}
}

/**
 * Calculates Percent respective to the last number in the display.
 * @param d - the <code>double</code> whose percent will be calculated.
 */
public void calculatesPercent(double d) {
	double percent;
		percent = (d * lastNumber) / 100;
		setDisplayString(String.valueOf(percent));		
}

/**
 * Handles the operation where the display numbers is the denominator of the 
 * operation.
 * @param the <code>double</code> that will become the denominator.
 * @throws DivideByZeroException
 */
public void xAsDenominator(double d) throws DivideByZeroException {
double result;
	if (displayMode != ERROR_MODE) {
			if (getNumberInDisplay() == 0)
				throw (new DivideByZeroException());
				result = 1 / getNumberInDisplay();
				displayResult(result);
		}		
	}

/**
 * Sets whatever is on the screen to the Memory
 * @param d - the <code>double</code> that will be saved in the memory.
 */
public void memorySave(double d){
	if(displayMode != ERROR_MODE){
	memoryNumber = d;
	
	MemoryDisplay.setText(full);
	}	
	else {
		
	}
}

/**
 * Add whatever in on Memory to whatever is on the screen
 * @param the <code>double</code> that will be summed to the number in the memory.
 */
public void memoryPlus(double d){
double result = 0;	
	if(displayMode != ERROR_MODE){
		if(!MemoryDisplay.getText().equals(empty)){
			result = memoryNumber + d;
			displayResult(result);
		}
	}
}

/**
 * Displays whatever is on the Memory onto the screen
 */
public void memoryRedisplay(){
	if(!MemoryDisplay.getText().equals(empty)){
		setDisplayString(Double.toString(memoryNumber));
		displayMode = INPUT_MODE;
	}
}

/**
 * Clears Whatever is in Memory
 */
public void clearMemory(){
	if(displayMode != ERROR_MODE){
		memoryNumber = 00D;
		MemoryDisplay.setText(empty);
		
	}
}

}//end Calculator Class



