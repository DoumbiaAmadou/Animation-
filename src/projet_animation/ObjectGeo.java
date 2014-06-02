package projet_animation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.DefaultEditorKit.CopyAction;

import Polygon.PolygonPersonnaliser;
import projet_animation.ButtonListener.PopUpDemo;

public class ObjectGeo {
	private int id;
	private PolygonPersonnaliser figureDeBase, figureCourante;
	private double xc, yc, xcCourant, ycCourant;
	private Point2D centreDeBase, centreCourant;
	private Color color;
	private int instantCTOT, instantCAnim;
	private Animations animationCourant;
	private double bs;


	private ArrayList<Animations>  mesAnimations ;  
	//private double time;
	private String type;
	/**
	 * nouvelle valeur. 
	 *  rapport de mise a jour. 
	 * */
	double Rapport = 1 ; 
	double angle = 0; 
	
	public ObjectGeo(int id, String type){
		this.id = id;
		this.type=type;
		//time=0;
		figureDeBase = new PolygonPersonnaliser();
		figureCourante = new PolygonPersonnaliser();
		animationCourant = null;
		xc = yc = xcCourant = ycCourant = 0;
		mesAnimations = new ArrayList<Animations>();
		color = Color.black;
	}

	public String toString(){
		int i;
		String obj = "";

		obj+=(""+id+";");
		obj+=(""+type+";");
		obj+=(""+color.getRed()+","+color.getGreen()+","+color.getBlue()+";");
		obj+=(""+bs+";");
		obj+=(""+figureDeBase.getNpoint()+";");
		obj+=(""+mesAnimations.size()+";");
		obj+=(""+xc+","+yc+";");
		for(i=0;i<figureDeBase.getNpoint();i++){
			obj+=(""+figureDeBase.getX(i)+","+figureDeBase.getY(i)+";");
		}
		for(i=0;i<mesAnimations.size();i++){
			obj+=(mesAnimations.get(i).toString());
		}
		
		return obj;
	}
	
	public int getId(){
		return id;
	}

	public double getCx(int i){
		return figureDeBase.getX(i);
	}

	public double getCy(int i){
		return figureDeBase.getY(i);
	}

	public void setCenter(double d, double e){
		xc = d;
		yc = e;
		setCenterC(d, e);
	}

	public void setCenterC(double d, double e){
		xcCourant = d;
		ycCourant = e;
	}

	public double getXCenter(){
		return xc;
	}

	public double getYCenter(){
		return yc;
	}

	public double getXCenterCourant(){
		return xcCourant;
	}

	public double getYCenterCourant(){
		return ycCourant;
	}

	public String getType(){
		return type;
	}

	public void setColor(Color c){
		color=c;
	}

	public Color getColor(){
		return color;
	}

	public double getStroke(){
		return bs;
	}
	
	public void setStroke(double value){
		bs = value;
	}
	
	
	public boolean isContentPoint(int x ,int  y ){
		return figureCourante.contains(x, y); 
	}

	public void ajouterPoint(double d, double e){
		figureCourante.addPoint(d,e);
		figureDeBase.addPoint(d,e);
	}

	public int getnbPoint(){
		return figureCourante.getNpoint();
	}

	public PolygonPersonnaliser getPolygon(){
		return figureDeBase;
	}

	public PolygonPersonnaliser getFigureC() {
		return figureCourante;
	}

	public void addAnimation(Animations animations) {
		// TODO Auto-generated method stub
		mesAnimations.add(animations);
	}

	/*public double getTime(){
return time;
}

public void setTime(double ntime){
time=ntime;
}*/

	public PolygonPersonnaliser dupliquePolygon(){
		return (new PolygonPersonnaliser(figureDeBase.getXPoint(),figureDeBase.getYPoint(),figureDeBase.getNpoint()));	
	}

	public PolygonPersonnaliser dupliquePolygon(PolygonPersonnaliser poly){
		return (new PolygonPersonnaliser(poly.getXPoint(),poly.getYPoint(),poly.getNpoint()));	
	}

	public Animations getAnimation(int instant){
		int tmp = 0;
		for(int i=0;i<mesAnimations.size();i++){
			tmp += mesAnimations.get(i).getDuree();
			if(instant<tmp)	return mesAnimations.get(i);
		}
		return null;
	}

	public int getInstantAnimation(int instant){
		int tmpAnimRestant, tmp = 0;
		for(int i=0;i<mesAnimations.size();i++){
			if(instant<=tmp){
				tmpAnimRestant = tmp-instant; 
				if(tmpAnimRestant==0) return 0;
				else return (mesAnimations.get(i-1).getDuree() - tmpAnimRestant);
			}
			tmp += mesAnimations.get(i).getDuree();
		}
		return 0;
	}

	public int getInstantC (){
		return instantCTOT;
	}

	public int getInstantAnim(int instant){
		int tmpAnimRestant, tmp = 0;
		for(int i=0;i<mesAnimations.size();i++){
			if(instant<=tmp){
				tmpAnimRestant = tmp-instant; 
				if(tmpAnimRestant==0) return 0;
				else return (mesAnimations.get(i-1).getDuree() - tmpAnimRestant);
			}
			tmp += mesAnimations.get(i).getDuree();
		}
		return 0;
	}

	public void  animate(int miliseconde , int nbappelle/*,int vitesse*/) {
		if(animationCourant!=null &&!animationCourant.equals(getAnimation(miliseconde/nbappelle)));
		{
			animationCourant=getAnimation(miliseconde/nbappelle) ;
		}
		 
		move(miliseconde , nbappelle);
		
	}
	private void move(int animationMiliseconde , int nbappelle) {
		// TODO Auto-generated method stub
		if(animationCourant!=null){
			if( animationCourant.getType().equals("rotation")){
				rotation(animationCourant.getAngle()/(animationCourant.getDuree()*nbappelle));
				System.out.println("move rotation");
			}
			
			if( animationCourant.getType().equals("translation")){
				translation(animationCourant.getDistance()/(animationCourant.getDuree()*nbappelle),
						animationCourant.getAngle());
				System.out.println("translation");
			}
			if( animationCourant.getType().equals("homothetie")){
				homothetie(animationCourant.getNbHomotesie(),
						animationCourant.getDuree()*nbappelle);
				System.out.println("homothetie");
			}
			if( animationCourant.getType().equals("similitude")){
				similitude(animationCourant.getAngle()/(animationCourant.getDuree()*nbappelle),
						animationCourant.getNbHomotesie(),
						animationCourant.getDuree()*nbappelle);
				System.out.println("similitude");
			}
			if( animationCourant.getType().equals("cosinus")){
				cosinus(animationCourant.getDistance()/(animationCourant.getDuree()*nbappelle),
						animationCourant.getAngle(), animationMiliseconde);
				System.out.println("cosinus");
			}
			if( animationCourant.getType().equals("sinus")){
				sinus(animationCourant.getDistance()/(animationCourant.getDuree()*nbappelle),
						animationCourant.getAngle(), animationMiliseconde);
				System.out.println("sinus");
			}
		}
	}

	public void init() {
		figureCourante = dupliquePolygon();
		setCenterC(xc, yc);
	}

	public void deplacerFigureCourante(double vxd, double vyd){
		
		
		setCenterC(xcCourant+vxd, ycCourant+vyd);
		
		for(int i=0; i<figureCourante.getNpoint();i++){
			
			figureCourante.setX(i, figureCourante.getX(i)+vxd);
			figureCourante.setY(i, figureCourante.getY(i)+vyd);
		}
	}

	private void rotation(double angle) {
		System.out.println(angle);
		
		if(!type.equals("cercle")){
			
			double vx, vy, vxd, vyd;
			double angleRad = Math.toRadians(-angle);
				
			for(int i=0;i<figureCourante.getNpoint();i++){
				
				vx =  figureCourante.getX(i) - xcCourant;
				vy =  figureCourante.getY(i) - ycCourant;
						
				vxd = vx*Math.cos(angleRad) - vy*Math.sin(angleRad);
				vyd = vx*Math.sin(angleRad) + vy*Math.cos(angleRad);
				
				figureCourante.setX(i, xcCourant + vxd) ; 
				figureCourante.setY(i, ycCourant + vyd) ; 
			}
		}
			
	}
	
	private void translation(double distance, double angle) {

		double vx = distance; 
		double vy = 0;

		double angleRad = Math.toRadians(-angle);

		double vxd = vx*Math.cos(angleRad) - vy*Math.sin(angleRad);
		double vyd = vx*Math.sin(angleRad) + vy*Math.cos(angleRad); 

		
		deplacerFigureCourante(vxd, vyd);

	}

	private void homothetie(double rapport, double duree) {
		double vx, vy, vxd, vyd;

		for(int i=0;i<figureCourante.getNpoint();i++){

			vx = figureCourante.getX(i) - xcCourant;
			vy = figureCourante.getY(i) - ycCourant;
			
			vxd = vx*rapport;
			vyd = vy*rapport;

			vxd = (vxd - vx)/duree;
			vyd = (vyd - vy)/duree;

			figureCourante.setX(i, xcCourant+vx+vxd);
			figureCourante.setY(i, ycCourant+vy+vyd);
			
		}
	}

	private void similitude(double angle, double rapport, double duree){
		rotation(angle);
		homothetie(rapport, duree);
	}

	private void cosinus(double distance, double angle, int mouv) {
		double vx = distance; 
		double vy = 0;

		double angleRad = Math.toRadians(-angle);

		double vxd = vx*Math.cos(angleRad) - vy*Math.sin(angleRad);
		double vyd = vx*Math.sin(angleRad) + vy*Math.cos(angleRad); 

		vxd+=Math.cos(mouv/10);
		
		deplacerFigureCourante(vxd, vyd);
	}
	
	private void sinus(double distance, double angle, int mouv) {
		double vx = distance; 
		double vy = 0;

		double angleRad = Math.toRadians(-angle);

		double vxd = vx*Math.cos(angleRad) - vy*Math.sin(angleRad);
		double vyd = vx*Math.sin(angleRad) + vy*Math.cos(angleRad); 

		vyd+=Math.cos(mouv/10);
		
		deplacerFigureCourante(vxd, vyd);
	}

	public int getmaxAnimation() {
		// TODO Auto-generated method stub
		int ret =0; 
		for (Animations a : mesAnimations) {
			ret +=a.getDuree();
		}
		return ret; 
	}


}