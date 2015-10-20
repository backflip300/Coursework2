package gui;

import java.awt.Color;
import java.awt.Dimension;

import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import net.miginfocom.swing.MigLayout;


@SuppressWarnings("serial")
public class ScrollTextArea extends JPanel{
	private JTextPane mCompOutputArea;
	private JScrollPane mPanelOutputScroller;
	private ReentrantLock mLock = new ReentrantLock();
	
	
	
	ScrollTextArea(int width, int height){
		this.setLayout(new MigLayout());
		mCompOutputArea = new JTextPane();
		mCompOutputArea.setEditable(false);
		
		mPanelOutputScroller = new JScrollPane(mCompOutputArea);
		mPanelOutputScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mPanelOutputScroller.setMinimumSize(new Dimension(width, height));
		mPanelOutputScroller.setAutoscrolls(true);
		this.add(mPanelOutputScroller);
	}
	
	public void appendToOutput(String msg, Color c, boolean makeBold){
		mLock.lock();
		//System.out.println("ran");
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet attSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		attSet = sc.addAttribute(attSet, StyleConstants.Bold, makeBold);
		try {
			mCompOutputArea.getDocument().insertString(mCompOutputArea.getDocument().getLength(), msg, attSet);
			
		} catch (BadLocationException e) {
			//should NEVER happen as we are inserting the string at doc.getLength() -> ie, at the end of it and no furthur!
			e.printStackTrace();
		}
		JScrollBar vertical = mPanelOutputScroller.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
		mLock.unlock();
		//System.out.println("stopped");
	}
	
	public void clear() {
		mLock.lock();
		try {
			this.mCompOutputArea.getDocument().remove(0, this.mCompOutputArea.getDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		mLock.unlock();
		
	}
	
	
}