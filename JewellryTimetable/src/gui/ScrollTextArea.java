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

/**
 * The Class ScrollTextArea. Creates a scrollable text area with the ability to
 * append in different colours and fonts
 */
@SuppressWarnings("serial")
public class ScrollTextArea extends JPanel {

	/** The text pane where text is outputed. */
	private JTextPane mCompOutputArea;

	/** The scrollable pane that holds the text pane */
	private JScrollPane mPanelOutputScroller;

	/** The makes sure that only one thread attempts to output. */
	private ReentrantLock mLock = new ReentrantLock();

	/**
	 * Instantiates a new scroll text area.
	 *
	 * @param width
	 *            of the scroll area to create
	 * @param height
	 *            of the scroll area to create
	 */
	ScrollTextArea(int width, int height) {
		this.setLayout(new MigLayout());
		mCompOutputArea = new JTextPane();
		mCompOutputArea.setEditable(false);
		mPanelOutputScroller = new JScrollPane(mCompOutputArea);
		mPanelOutputScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mPanelOutputScroller.setMinimumSize(new Dimension(width, height));
		mPanelOutputScroller.setAutoscrolls(true);
		this.add(mPanelOutputScroller);
	}

	/**
	 * Append text to the output area
	 *
	 * @param msg
	 *            text to output
	 * @param c
	 *            Color of text
	 * @param makeBold
	 *            make text bold
	 */
	public void appendToOutput(String msg, Color c, boolean makeBold) {
		mLock.lock();
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet attSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		attSet = sc.addAttribute(attSet, StyleConstants.Bold, makeBold);
		try {
			mCompOutputArea.getDocument().insertString(mCompOutputArea.getDocument().getLength(), msg, attSet);

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		JScrollBar vertical = mPanelOutputScroller.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
		mLock.unlock();
	}

	/**
	 * Clears the text area
	 */
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