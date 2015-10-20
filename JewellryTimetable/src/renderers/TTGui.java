package renderers;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TTGui extends JPanel{
	int a = 0;
	
	public TTGui() {
		// TODO Auto-generated constructor stub
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		a++;
		for (int i = 0; i < 5 ; i++){
			g.drawRect(a, i*30, 400, 30);
			
		}
		
	}
	public void update(){
		//repaint();
	}
	
	
	
}
