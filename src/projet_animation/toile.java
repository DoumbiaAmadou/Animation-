package projet_animation;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.EventListener;
import java.util.Vector;

import javax.net.ssl.KeyStoreBuilderParameters;
import javax.print.attribute.Size2DSyntax;
import javax.swing.*; 
import javax.swing.event.ListSelectionListener;

import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

public class Toile  extends JPanel {

	BufferedImage  bi ;
	Graphics2D  gd ; 
	int x ; 
	int y ; 
	int width , height ; 
	public Toile(){
	

//	setMinimumSize(new Dimension(50 , 50));
	bi= new BufferedImage(1400, 1400, BufferedImage.TYPE_4BYTE_ABGR);
	gd = bi.createGraphics(); 
	addMouseListener(new SouirisEcouteur());
	addMouseMotionListener(new SouirisEcouteur());
	gd.setBackground(Color.black);
	}
	protected void paintComponent (Graphics g){
		
		gd.setColor(Color.RED);
		g.drawImage(bi, 0,0, null);
	
	}
	class SouirisEcouteur extends MouseAdapter  implements MouseMotionListener {

		@Override
		public void mouseDragged(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
		
			gd.drawLine(x, y, e.getX(),e.getY()); 
			x = e.getX() ; 
			y = e.getY() ; 
			setVisible(false); 
			repaint() ; 
			setVisible(true); 
		
			
		}

		@Override
		public void mouseMoved(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			x = e.getX() ; 
			y = e.getY() ; 		
		}
		
	}
	public void clear() {
		// TODO Auto-generated method stub
		gd.setColor(Color.black);
		gd.clearRect(0, 0, width, height) ;
		repaint();
	}

}

