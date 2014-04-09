package projet_animation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlacementListener extends MouseAdapter{
	private ObjectFrame zone;
	private ObjectGeo figure;
	private String type;
	private int cpt;
	
	public PlacementListener(ObjectFrame zone, String type){
		this.zone = zone;
		this.type = type;
		cpt = 0;
	}
	
	public void mousePressed(MouseEvent e){
		
		if(cpt==0) figure = new ObjectGeo(zone.getNbFigure(),type);
		
		if(type=="segment"){			
			figure.ajouterPoint(e.getX(), e.getY());
			if(cpt<2){
				cpt++;
			}
			else if(cpt==2){
				zone.ajouterFigure(figure);
				zone.desactiverListener();
			}
			
		}else if(type=="triangleEq"){
			figure.ajouterPoint(e.getX(), e.getY());
			if(cpt<3){
				cpt++;
			}
			else if(cpt==3){
				int xc1,yc1,xc2,yc2;
				xc1= (int) (((figure.getCx(0) + figure.getCx(1))/2) + (Math.sqrt(3)/2)* (-(figure.getCy(1)-figure.getCy(0))));
				yc1= (int) (((figure.getCy(0) + figure.getCy(1))/2) + (Math.sqrt(3)/2)*(figure.getCx(1)-figure.getCx(0)));
				xc2= (int) (((figure.getCx(0) + figure.getCx(1))/2) + (Math.sqrt(3)/2)* (figure.getCy(1)-figure.getCy(0)));
				yc2= (int) (((figure.getCy(0) + figure.getCy(1))/2) + (Math.sqrt(3)/2)*(-(figure.getCx(1)-figure.getCx(0))));
				
				if(Math.sqrt( Math.pow((e.getX()-xc1),2) + Math.pow((e.getY()-yc1),2)) <= Math.sqrt( Math.pow((e.getX()-xc2),2) + Math.pow((e.getY()-yc2),2))     )
					figure.ajouterPoint(xc1, yc1);
				else figure.ajouterPoint(xc2, yc2);
				zone.ajouterFigure(figure);
				zone.desactiverListener();
			}
			
		}else if(type=="carre"){
			figure.setCenter(e.getX(), e.getY());
			figure.ajouterPoint(e.getX()-50, e.getY()-50);
			figure.ajouterPoint(e.getX()+50, e.getY()-50);
			figure.ajouterPoint(e.getX()+50, e.getY()+50);
			figure.ajouterPoint(e.getX()-50, e.getY()+50);
			zone.ajouterFigure(figure);
			zone.desactiverListener();
			
		}else if(type=="cercle"){
			if(cpt==0){
				figure.setCenter(e.getX(), e.getY());
				cpt++;
			}
			else if(cpt==1){
				figure.ajouterPoint(e.getX(), e.getY());
				zone.ajouterFigure(figure);
				zone.desactiverListener();
			}
			
		}else if(type=="losange"){
			figure.setCenter(e.getX(), e.getY());
			figure.ajouterPoint(e.getX(), e.getY()-25);
			figure.ajouterPoint(e.getX()+50, e.getY());
			figure.ajouterPoint(e.getX(), e.getY()+25);
			figure.ajouterPoint(e.getX()-50, e.getY());
			zone.ajouterFigure(figure);
			zone.desactiverListener();
			
		}else if(type=="trapeze"){
			
			
		}else if(type=="etoile"){
			
			
		}else if(type=="polygone"){
			
			
		}
	}
}
