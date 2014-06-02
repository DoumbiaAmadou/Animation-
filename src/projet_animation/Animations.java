package projet_animation;

public class Animations {
	private  String type = "" ; 
	private   double angle = 0 ; 
	private  double  multipleHomothetie = 1; 
	private  double distance = 0 ; 
	private  int dur�e= 1 ; 


	/**
	 * @param s
	 * @param a
	 * @param mh
	 * @param ta
	 * @param th
	 */
	public Animations(String  s , double a , double mh ,double di , int du ) {
		// TODO Auto-generated constructor stub	
		assert mh!=0; 
		assert du!=0; 

		this.type = s ; 
		this.multipleHomothetie = mh ; 
		this.angle = a ; 
		this.distance = di ; 
		this.dur�e =du;  
	}

	public String toString(){
		String anim = (type+","+angle+","+distance+","+multipleHomothetie+","+dur�e+";");
		return anim;
	}

	public String getType() {
		return type;
	}

	public int getDuree(){
		return dur�e;
	}

	public double getAngle(){
		return angle;
	}

	public double getDistance(){
		return distance;
	}

	public double getNbHomotesie(){
		return multipleHomothetie;
	}



}