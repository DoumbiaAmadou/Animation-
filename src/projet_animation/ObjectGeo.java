package projet_animation;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ObjectGeo {
	private int id;
	private Polygon figure;
	private int xc, yc;
	private Color color;
	
	//private float time;
	private String type;
	
	public ObjectGeo(int id, String type){
		this.id = id;
		this.type=type;
		//time=0;
		figure = new Polygon();
		xc = yc = 0;
	}
	
	public int getId(){
		return id;
	}
	
	public int getCx(int i){
		return figure.xpoints[i];
	}
	
	public int getCy(int i){
		return figure.ypoints[i];
	}
	
	public void setCenter(int x, int y){
		xc = x;
		yc = y;
	}
	
	public int getXCenter(){
			return xc;
	}
	
	public int getYCenter(){
		return yc;
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
	
	public void ajouterPoint(int x, int y){
	//	System.out.println(""+x+" "+y );
		figure.addPoint(x, y);
	}
	public void deplacer(int x, int y){
		setCenter(xc+x, yc-y);
		for(int i=0; i<figure.npoints;i++){
			figure.xpoints[i]+=x;
			figure.ypoints[i]-=y;
		}
	}
	
	public int getnbPoint(){
		return figure.npoints;
	}
	
	public Polygon getPolygon(){
		return figure;
	}
	
	/*public float getTime(){
		return time;
	}

	public void setTime(float ntime){
		time=ntime;
	}*/
}