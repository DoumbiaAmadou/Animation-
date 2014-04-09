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
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.imageio.ImageIO;


public class ObjectFrame extends JPanel{
	private BufferedImage bf;
	private Graphics2D bg;
	private Color c = new Color(173,185,188);
	public JSlider slide;
	private BasicStroke bs;
	
	
	private ArrayList<ObjectGeo> figures;
	private PlacementListener cListener;
	private boolean listenerActif;
	
	public ObjectFrame(){
		super();
		figures = new ArrayList<ObjectGeo>();
		cListener = null;
		listenerActif = false;
		
		
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
			double vectX = figures.get(i).getCx(0) - figures.get(i).getCenter().getX();
			double vectY = figures.get(i).getCy(0) - figures.get(i).getCenter().getY();
			double rayon = Math.sqrt(vectX*vectX + vectY*vectY);
			bg.drawArc((int)figures.get(i).getCenter().getX(), (int)figures.get(i).getCenter().getY(), (int)bs.getLineWidth(), (int)rayon, 0, 360);
		}
	}
	
	protected void paintComponent(Graphics g){
		setColor();
	
		bg.fillRect(0,0,getMaximumSize().width,getMaximumSize().width);
			bg.setColor(Color.green);
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
		this.cListener = new PlacementListener(this, type);
		activerListener();
	}
	
	public void ajouterFigure(ObjectGeo figure){
		figures.add(figure);
		repaint();
	}
	
	public void activerListener(){
		this.addMouseListener(cListener);
		listenerActif = true;
	}
	
	public void desactiverListener(){
		removeMouseListener(cListener);
		listenerActif = false;
	}
	
}
