package projet_animation;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;

public class ObjectFrame extends JPanel{
	private BufferedImage bf;
	private Graphics2D bg;
	private Color c = new Color(173,185,188);
	public JSlider slide;
	private BasicStroke bs;
	
	private ArrayList<ObjectGeo> figures;
	private PlacementListener cListener;
	private boolean listenerActif;
	private MouseAdapter mouseAddRemoveAnnimation ; 
	private ObjectGeo selected;
	public boolean isListenerActif() {
		return listenerActif;
	}

	public ObjectFrame(){
		super();
		figures = new ArrayList<ObjectGeo>();
		cListener = null;
		listenerActif = false;
		addMouseListener(cListener);
		bf = new BufferedImage(900,900,BufferedImage.TYPE_4BYTE_ABGR);
		bg = bf.createGraphics();
		slide = new JSlider(10,60);
		bs = new BasicStroke(5.6f);
		bg.setStroke(bs);
		
		slide.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent ev){
				float fl = slide.getValue()/10;
				bs = new BasicStroke(fl);
				bg.setStroke(bs);
			}
		});		
		mouseAddRemoveAnnimation = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			};
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				  //doPop(e,selected);
				 if (e.isPopupTrigger() && getObjetSelected(e.getX() , e.getY()) )
				 {   doPop(e,selected);
				 	 
				 }
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if(selected != null )
				selected.setColor(Color.black) ; 
				repaint() ; 
				selected= null ; 
			}
		}; 
	}
	
	public BufferedImage getBImage(){
		return bf;
	}
	
	public void setColor(){
		bg.setColor(c);
	}
	
	public void setColor(Color color){
		bg.setColor(color);
	}
	public void placerFigure(int i){
		setColor(figures.get(i).getColor());
		if(figures.get(i).getType()!="cercle"){
			bg.drawPolygon(figures.get(i).getPolygon());
		}
		else {
			double vectX = figures.get(i).getCx(0) - figures.get(i).getXCenter();
			double vectY = figures.get(i).getCy(0) - figures.get(i).getYCenter();
			double rayon = Math.sqrt(vectX*vectX + vectY*vectY);
			bg.fillOval((int)figures.get(i).getXCenter()-(int)rayon, (int)figures.get(i).getYCenter()-(int)rayon, (int)rayon*2, (int)rayon*2);
			setColor(c);
			bg.fillOval((int)figures.get(i).getXCenter()-(int)rayon+(int)bs.getLineWidth(), (int)figures.get(i).getYCenter()-(int)rayon+(int)bs.getLineWidth(), (int)(rayon-bs.getLineWidth())*2, (int)(rayon-bs.getLineWidth())*2);
			setColor(Color.black);
		}
	}
	
	protected void paintComponent(Graphics g){
		setColor();
		bg.fillRect(0,0,getMaximumSize().width,getMaximumSize().height);
		setColor(Color.black);
		
		for(int i=0; i<figures.size(); i++){
			placerFigure(i);	
		}
		
		g.drawImage(bf,0,0,null);
	}
	
	public ObjectGeo getFigure(int i){
		return figures.get(i);
	}
	
	public int getNbFigure(){
		return figures.size();
	}
	
	public void ajouterFigure(String type){
		if(listenerActif==true) desactiverListener();
		cListener = new PlacementListener(this, type);
		activerListener();
	}
	
	public void ajouterFigure(ObjectGeo figure){
		figures.add(figure);
		repaint();
	}
	
	public void activerListener(){
		removeMouseListener(mouseAddRemoveAnnimation);
		addMouseListener(cListener);
		listenerActif = true;
		
	}
	
	public void desactiverListener(){
		 removeMouseListener(cListener);
		listenerActif = false;
		addMouseListener(this.mouseAddRemoveAnnimation);
	}
	private boolean  getObjetSelected(int x, int y) {
		// TODO Auto-generated method stub

		for(ObjectGeo  ob : figures ){
			if(ob.getfiguire().inside(x, y)|| 
					Line2D.ptLineDistSq( ob.getfiguire().xpoints[0] ,ob.getfiguire().ypoints[0] ,
							ob.getfiguire().xpoints[1] ,ob.getfiguire().ypoints[1] ,
							x , y )<5
			){	
						selected = ob ; 
						ob.setColor(Color.yellow); 
						repaint();
						System.out.println("objettrouver");
						return true ;
			}
		}
		System.out.println("aucun  objet geo trouver");
	return false ;
	}
	private void doPop(MouseEvent e, ObjectGeo selected2) {
		// TODO Auto-generated method stub
		PopUp  menu = new PopUp(selected2) ; 
		 menu.show(e.getComponent(), e.getX(), e.getY());
	}
	
	
		
	class PopUp extends JPopupMenu {
	
        JMenuItem ajoutAnimation , supprimerAnnilation;
        public PopUp(ObjectGeo geo){
        	ajoutAnimation = new JMenuItem("ajouter Animation?");
        	
        	supprimerAnnilation = new JMenuItem("supprimerAnnilation?"); 
        	 
            add(ajoutAnimation);
            add(supprimerAnnilation);
        }
    }
}
