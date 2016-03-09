package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class Tab3 extends JComponent {
	private JLabel imagePanel;
	private Image image;
	Image scaledImage;
	private JPanel tab3;

	public Tab3() {
		try {
			image = ImageIO.read(new File("Images/Timetable.png"));
			scaledImage = image.getScaledInstance(770, 470, Image.SCALE_SMOOTH);
		} catch (IOException e) {		
		}
	}

	@SuppressWarnings("serial")
	public JPanel create() {
		// tab3.setLayout(new MigLayout());
		// Frame1.setSize(new Dimension);
		tab3 = new JPanel();
		imagePanel = new JLabel(new ImageIcon(scaledImage));
		tab3.add(imagePanel);
		// tab3.repaint();

		return tab3;
	}

}
