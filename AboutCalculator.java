package com.LOIhogeschool.CalculatorJava;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * <p>Shows 'about' information on the application</p>
 * 
 * <p>Copyright: This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.<br>
 * 2013.</p>
 * 
 * @author FJ Mujica
 * @version: 1.0
 *
 */

public class AboutCalculator extends JDialog implements ActionListener {
	JButton jbnOk;

	AboutCalculator(Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		setBackground(Color.black);
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		final String message = "Developer:	FJ Mujica\nVersion:	1.0\nJava Programmieren\nInzendopgaven 6 - 897M6";
		
		
		JTextArea jtAreaAbout = new JTextArea(5, 21);
		jtAreaAbout.setBackground(new Color(238,238,238));
		
		jtAreaAbout.setText(message);
		jtAreaAbout.setFont(new Font("Times New Roman", 1, 13));
		jtAreaAbout.setEditable(false);

		p1.add(jtAreaAbout);		
		getContentPane().add(p1, BorderLayout.CENTER);

		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jbnOk = new JButton(" OK ");
		jbnOk.addActionListener(this);

		p2.add(jbnOk);
		getContentPane().add(p2, BorderLayout.SOUTH);

		setLocation(450, 300);
		setResizable(false);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		pack();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbnOk) {
			this.dispose();
		}
	}
}//end of AboutCalculator class
