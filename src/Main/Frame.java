package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Frame
{
	private JFrame mFrame;
	private JTextArea mTextArea;
	
	public Frame(KeyListener listener)
	{
		mFrame = new JFrame();
		
		mFrame.setTitle("Let's play BlackJack");
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.setVisible(true);
        
        mFrame.setSize(800, 600);
        mFrame.setLocationRelativeTo(null);
        
        mTextArea = new JTextArea();
        mTextArea.setEditable(false);
        mTextArea.addKeyListener(listener);
        mTextArea.setFont(new Font("Serif", Font.PLAIN, 26));
        
        mFrame.add(mTextArea);        
        mFrame.toFront();
        mFrame.requestFocus();
	}
	
	public void SetText(String val)
	{
		mTextArea.setText(val);
	}
	
	public void AddText(String val)
	{
		mTextArea.append(val);
	}
}
