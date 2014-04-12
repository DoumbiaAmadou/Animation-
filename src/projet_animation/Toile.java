package projet_animation;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

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
	@Override
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

