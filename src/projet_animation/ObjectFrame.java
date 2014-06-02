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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.ImageIcon;
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

import com.sun.corba.se.impl.orbutil.RepIdDelegator;
import com.sun.org.apache.xml.internal.security.Init;

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
	private Color color = Color.black;
	boolean play  = false ; 
	boolean pause = false ; 
	Fenetreteste fenetrePrincipale ; 

	/* nouvel valeur*/
	int animationMiliseconde =50;
	private  int vitesse = 10 ; 
	public boolean isListenerActif() {
		return listenerActif;
	}

	public ObjectFrame(Fenetreteste fenetre ){
		super();
		this.fenetrePrincipale = fenetre; 
		figures = new ArrayList<ObjectGeo>();
		cListener = null;
		listenerActif = false;
		addMouseListener(cListener);
		bf = new BufferedImage(900,900,BufferedImage.TYPE_4BYTE_ABGR);
		bg = bf.createGraphics();
		slide = new JSlider(10,60);
		bs = new BasicStroke(3.5f);
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
				if (e.isPopupTrigger() && getObjetSelected(e.getX() , e.getY()) )
				{   

				}
			};
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				// doPop(e,selected);	
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.isPopupTrigger() && getObjetSelected(e.getX() , e.getY()) )
				{  

					try {
						Thread.sleep(200);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					selected.setColor(color);
					color = Color.black;
					setColor(Color.black);
					
					paintImmediately(0, 0, 9000, 9000);


					doPop(e,selected);
				}
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
		bg.setColor(figures.get(i).getColor());
		bg.setStroke(new BasicStroke((float) figures.get(i).getStroke()));
		
		if(figures.get(i).getType()!="cercle"){
			bg.drawPolygon(figures.get(i).getFigureC());
		}
		else {
			double vectX = figures.get(i).getCx(0) - figures.get(i).getXCenter();
			double vectY = figures.get(i).getCy(0) - figures.get(i).getYCenter();
			double rayon = Math.sqrt(vectX*vectX + vectY*vectY);
			bg.drawOval((int)figures.get(i).getXCenter()-(int)rayon, (int)figures.get(i).getYCenter()-(int)rayon, (int)rayon*2, (int)rayon*2);
			
		}
	}

	public void reinit(){
		figures = new ArrayList<ObjectGeo>();
		repaint();
	}
	
	public void paintNow(){
		paintImmediately(0, 0, 9000, 9000);
	}
	
	protected void paintComponent(Graphics g){
	//	super.paintComponent(g);
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
	
	public ArrayList<ObjectGeo> getFigures(){
		return figures;
	}
	
	public void ajouterFigure(String type){
		if(listenerActif==true) desactiverListener();
		cListener = new PlacementListener(this, type, bs);
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
			if(ob.getFigureC().inside(x, y)|| 
					Line2D.ptLineDistSq( ob.getFigureC().getX(0) ,ob.getFigureC().getY(0) ,
							ob.getFigureC().getX(1) ,ob.getFigureC().getY(1) ,
							x , y )<30 ||
					(
						Math.abs(ob.getFigureC().getX(0) -x)<Math.abs(ob.getFigureC().getX(1)-ob.getFigureC().getX(0)) &&
						Math.abs(ob.getFigureC().getY(0) -y)<Math.abs(ob.getFigureC().getY(1)-ob.getFigureC().getY(0))	

					)
					){
				selected = ob ; 
				color = selected.getColor();
				selected.setColor(Color.yellow);
				paintImmediately(0, 0, 9000, 9000);
				System.out.println("objettrouver");
				return true ;
			}
		}
		System.out.println("aucun  objet geo trouver");
		return false ;
	}
	private void doPop(MouseEvent e, ObjectGeo selected2) {
		// TODO Auto-generated method stub
		PopUp  menu = new PopUp(selected2, this) ; 
		menu.show(e.getComponent(), e.getX(), e.getY());
		selected = null;
		//selected2.setColor(Color.black);
		// repaint();
	}

	public void anime() {
		// TODO Auto-generated method stub
		int max = 0 ; 
		for (ObjectGeo element : figures) {
			int v =element.getmaxAnimation();
			if(max<v)max =v ;
		}
		System.out.println(" max temps animatoion = "+max ) ;
		for (int i = 0; i < max*animationMiliseconde; i++) {

			if(play==false){
				for (ObjectGeo element : figures) {
					//	System.out.println( element.getType()+"->"+element.getAnimation(i).getType());
					element.init();

				}
				return ;
			}
			while(pause){
				System.out.println("pause ");
				if(play==false){
					for (ObjectGeo element : figures) {
						//	System.out.println( element.getType()+"->"+element.getAnimation(i).getType());
						element.init();

					}
					return ; 
				}
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}


			for (ObjectGeo element : figures) {
				//	System.out.println( element.getType()+"->"+element.getAnimation(i).getType());
				element.animate( i , animationMiliseconde);
				 
			}
			
			try {
				if(vitesse!=0)
				Thread.sleep( 1000/ (animationMiliseconde* (vitesse/10)));
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			setVisible(false);
			
			paintImmediately(0, 0, 9000, 9000);
			setVisible(true );
			

		}

		fenetrePrincipale.boolPlayIsPressed = false ; 
		play = false ; 
		pause = false ; 
		vitesse =10;
		for (ObjectGeo element : figures) 
		{	element.init();
		}
		paintImmediately(0, 0, 9000, 9000);

	}

	public synchronized void setVitesse (int v){
		vitesse = v ;
	}
	public  int getVitesse (){
		return vitesse; 
	}
	
	public void save(File file){
		String data = "";

		for(int i=0;i<figures.size();i++){

			data+=figures.get(i).toString();
			data+="\n";

		}
		System.out.println(data);

		try {

			FileWriter out  = new FileWriter(file);
			PrintWriter writer = new PrintWriter(out);
			writer.write(data);
			writer.close();
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void open(File file){

		//boolean ok = false;
		int i, j, decalage, id, nbPoint, nbAnim, duree;
		double stroke, angle, distance, rapport ,xp, yp;
		String[] colorTab = new String[3];
		Color color;
		String[] point = new String[2];
		String[] animation = new String[5];

		String typeObj, typeAnim;
		ArrayList<ObjectGeo> objs = new ArrayList<ObjectGeo>();
		ObjectGeo objet;
		Animations anim;
		//try{
			
			//BufferedReader br = new BufferedReader(fr);
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String r;
			
			while(scanner.hasNextLine() && (r = scanner.nextLine())!=null && !r.equals("")){
				
				
				ArrayList<String> donnee = new ArrayList<String>( Arrays.asList(r.split(";")) );
				System.out.println(donnee.toString());


				id = Integer.parseInt(donnee.get(0));
				typeObj = donnee.get(1);
				objet = new ObjectGeo(id, typeObj);


				
				
				colorTab = donnee.get(2).split(",");
				
				color = new Color(Integer.parseInt(colorTab[0]),
						Integer.parseInt(colorTab[1]),
						Integer.parseInt(colorTab[2])); 
				
				objet.setColor(color);
				
				stroke = Double.parseDouble(donnee.get(3));

				objet.setStroke(stroke);
				
				nbPoint = Integer.parseInt(donnee.get(4));
				nbAnim = Integer.parseInt(donnee.get(5));
				
				point = donnee.get(6).split(",");

				xp = Double.parseDouble(point[0]);
				yp =  Double.parseDouble(point[1]);

				objet.setCenter((int) xp,(int) yp);

				for(i=0;i<nbPoint;i++){
					j = i+7;

					point = donnee.get(j).split(",");

					xp = Double.parseDouble(point[0]);
					yp = Double.parseDouble(point[1]);

					objet.ajouterPoint(xp, yp);
				}

				decalage = 7 + nbPoint;

				for(i=0;i<nbAnim;i++){
					j = i + decalage;

					animation = donnee.get(j).split(",");

					typeAnim = animation[0];
					angle = Double.parseDouble(animation[1]);
					distance = Double.parseDouble(animation[2]);
					rapport = Double.parseDouble(animation[3]);
					duree = Integer.parseInt(animation[4]);

					anim = new Animations(typeAnim, angle, distance, rapport, duree);
					objet.addAnimation(anim);
				}

				objs.add(objet);
			}
			scanner.close();
		
			
				figures = objs;
				System.out.println(figures.size());
				repaint();
			
	}

}