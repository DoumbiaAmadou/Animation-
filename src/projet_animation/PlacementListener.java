package projet_animation;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.BasicStroke;

public class PlacementListener extends MouseAdapter{
	private ObjectFrame zone;
	private ObjectGeo figure;
	private String type;
	private int cpt;
	private BasicStroke bs;
		
	public PlacementListener(ObjectFrame zone, String type, BasicStroke bs){
		this.zone = zone;
		this.type = type;
		cpt = 0;
		this.bs = bs;
		
	}
	
	public void mousePressed(MouseEvent e){
	
		if(cpt==0){
			figure = new ObjectGeo(zone.getNbFigure(),type);
		}
		
		if(type=="segment"){			
			figure.ajouterPoint(e.getX(), e.getY());
			if(cpt<1){
				cpt++;
			}
			else if(cpt==1){
				
				figure.setCenter((e.getX() + figure.getCx(0))/2, (e.getY() + figure.getCy(0))/2);
				
				figure.setStroke(bs.getLineWidth());
				zone.ajouterFigure(figure);
				zone.desactiverListener();
			}
			
		}else if(type=="triangleEq"){
			if(cpt<2){
				cpt++;
				figure.ajouterPoint(e.getX(), e.getY());
			}
			else if(cpt==2){
				int xc1,yc1,xc2,yc2;
				double xg, yg;
				xc1= (int) (((figure.getCx(0) + figure.getCx(1))/2) + (Math.sqrt(3)/2)* (-(figure.getCy(1)-figure.getCy(0))));
				yc1= (int) (((figure.getCy(0) + figure.getCy(1))/2) + (Math.sqrt(3)/2)*(figure.getCx(1)-figure.getCx(0)));
				xc2= (int) (((figure.getCx(0) + figure.getCx(1))/2) + (Math.sqrt(3)/2)* (figure.getCy(1)-figure.getCy(0)));
				yc2= (int) (((figure.getCy(0) + figure.getCy(1))/2) + (Math.sqrt(3)/2)*(-(figure.getCx(1)-figure.getCx(0))));
				
				if(Math.sqrt( Math.pow((e.getX()-xc1),2) + Math.pow((e.getY()-yc1),2)) <= Math.sqrt( Math.pow((e.getX()-xc2),2) + Math.pow((e.getY()-yc2),2))){
					figure.ajouterPoint(xc1, yc1);
					xg = (figure.getCx(0) + figure.getCx(1) + xc1)/3;
					yg = (figure.getCy(0) + figure.getCy(1) + yc1)/3;
					figure.setCenter(xg, yg);
				}
				else{
					figure.ajouterPoint(xc2, yc2);
					xg = (figure.getCx(0) + figure.getCx(1) + xc2)/3;
					yg = (figure.getCy(0) + figure.getCy(1) + yc2)/3;
					figure.setCenter(xg, yg);
				}
				
				figure.setStroke(bs.getLineWidth());
				zone.ajouterFigure(figure);
				zone.desactiverListener();
			}
			
		}else if(type=="carre"){
			
			if(cpt<1){
				
				figure.setCenter(e.getX(), e.getY());
				cpt++;
			}
			else{
				
				figure.ajouterPoint(e.getX(), e.getY());
				
				double vx = e.getX() - figure.getXCenter();
				double vy = e.getY() - figure.getYCenter();
				
				figure.ajouterPoint(figure.getXCenter() - vy, figure.getYCenter() + vx);
				figure.ajouterPoint(figure.getXCenter() - vx, figure.getYCenter() - vy);
				figure.ajouterPoint(figure.getXCenter() + vy, figure.getYCenter() - vx);
				
				figure.setStroke(bs.getLineWidth());
				zone.ajouterFigure(figure);
				cpt++; 
				zone.desactiverListener();
			}
		}else if(type=="cercle"){
			if(cpt==0){
				figure.setCenter(e.getX(), e.getY());
				cpt++;
			}
			else if(cpt==1){
				figure.ajouterPoint(e.getX(), e.getY());
				
				figure.setStroke(bs.getLineWidth());
				zone.ajouterFigure(figure);
				zone.desactiverListener();
			}
			
		}else if(type=="losange"){
			if(cpt==0){
				figure.ajouterPoint(e.getX(), e.getY());
				cpt++;
			}
			else if(cpt==1){
				figure.ajouterPoint(e.getX(), e.getY());
				double cx = figure.getCx(1);
				double cy = figure.getCy(0);
				figure.setCenter(cx, cy);
				
				figure.ajouterPoint(2*figure.getXCenter() - figure.getCx(0),
															figure.getYCenter());
				
				figure.ajouterPoint( figure.getXCenter(),
									2*figure.getYCenter() - figure.getCy(1));
				
				figure.setStroke(bs.getLineWidth());
				zone.ajouterFigure(figure);
				zone.desactiverListener();
			}
			
		}else if(type=="trapeze"){

			if(cpt<2){
				cpt++;
				figure.ajouterPoint(e.getX(), e.getY());
			}
			
			else if(cpt==2){
				figure.ajouterPoint(e.getX(), e.getY());
			
				double vxbase = figure.getCx(1) - figure.getCx(0);
				double vybase = figure.getCy(1) - figure.getCy(0);
				
				double vxcote = figure.getCx(2) - figure.getCx(1);
				double vycote = figure.getCy(2) - figure.getCy(1);
				
				
				int anglebase = (int) (Math.atan2(vybase,vxbase) * (180/Math.PI) );
				int anglecote = (int) (Math.atan2(vycote,vxcote) * (180/Math.PI) );
				int angle = -(anglecote - anglebase);
				
				
				double anglecoteOp = - (180 - 2*(angle))/(180/Math.PI);
				
				double vxd = vxcote*Math.cos(anglecoteOp) - vycote*Math.sin(anglecoteOp);
				double vyd = vxcote*Math.sin(anglecoteOp) + vycote*Math.cos(anglecoteOp);

				
				figure.ajouterPoint(figure.getCx(0) + vxd, figure.getCy(0) + vyd);
				
				double xi1 = (figure.getCx(0) + figure.getCx(1))/2;
				double yi1 = (figure.getCy(0) + figure.getCy(1))/2;
				
				double xi2 = (figure.getCx(2) + figure.getCx(3))/2;
				double yi2 = (figure.getCy(2) + figure.getCy(3))/2;
				
				
				double xg = (xi1+xi2)/2;
				double yg = (yi1+yi2)/2;
				
				figure.setCenter(xg, yg);
				
				figure.setStroke(bs.getLineWidth());
				zone.ajouterFigure(figure);
				zone.desactiverListener();
				
			}
			
			
			
		}else if(type=="pentagone"){
			if(cpt==0){
				figure.setCenter(e.getX(), e.getY());
				cpt++;
			}
			else{

				figure.ajouterPoint(e.getX(),e.getY());
					
				double vx = e.getX() - figure.getXCenter();
				double vy = e.getY() - figure.getYCenter();
				
				for(int i=0;i<4;i++){
				
					int vxp = (int) (vx*Math.cos(2*Math.PI/5) - vy*Math.sin(2*Math.PI/5));
					int vyp = (int) (vx*Math.sin(2*Math.PI/5) + vy*Math.cos(2*Math.PI/5));
				
					figure.ajouterPoint(figure.getXCenter() + vxp, figure.getYCenter() + vyp);
				
					vx = vxp;
					vy = vyp;
				}
				
				figure.setStroke(bs.getLineWidth());
				zone.ajouterFigure(figure);
				zone.desactiverListener();
				
			}
		}else if(type=="parallelogramme"){
			if(cpt<2){
				cpt++;
				figure.ajouterPoint(e.getX(), e.getY());
				}
			else if(cpt==2){
				figure.ajouterPoint(e.getX(), e.getY());
				double vx,vy;
				vx = figure.getCx(2)-figure.getCx(1);
				vy = figure.getCy(2)-figure.getCy(1);
				figure.ajouterPoint(figure.getCx(0)+vx, figure.getCy(0)+vy);
				
				figure.setCenter(  (figure.getCx(0)+figure.getCx(2))/2  , (figure.getCy(0)+figure.getCy(2))/2  );
				
				figure.setStroke(bs.getLineWidth());
				zone.ajouterFigure(figure);
				zone.desactiverListener();
			}
		}
	
	
	}


}
