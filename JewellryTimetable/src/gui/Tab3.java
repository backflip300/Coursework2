package gui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * The Class Tab3.
 */
@SuppressWarnings("serial")
public class Tab3 extends JComponent {

	/** The image panel holds the scaled image. */
	private JLabel imagePanel;

	/** The image of the timetable. */
	private Image image;

	/** The scaled image of the timetable after being scaled. */
	Image scaledImage;

	/** The tab3 which contains everything required on the tab. */
	private JPanel tab3;

	/**
	 * Instantiates a new tab3.
	 */
	public Tab3() {
		try {
			image = ImageIO.read(new File("Images/Timetable.png"));
			scaledImage = image.getScaledInstance(770, 470, Image.SCALE_AREA_AVERAGING);
		} catch (IOException e) {
		}
	}

	/**
	 * Creates the scaled image for tab3.
	 *
	 * @return tab3
	 */
	public JPanel create() {
		
		tab3 = new JPanel();
		//Scale image.
		imagePanel = new JLabel(new ImageIcon(scaledImage));
		//Add image to tab3
		tab3.add(imagePanel);

		return tab3;
	}

}
