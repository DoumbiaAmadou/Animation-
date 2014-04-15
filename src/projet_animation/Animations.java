package projet_animation;

public class Animations {
	private  String type = "" ; 
	private   double angle = 0 ; 
	private  double  multipleHomotesie = 0 ; 
	private  double translationAvant = 0 ; 
	private  double translationHauteur= 0 ; 
	

	/**
	 * @param s
	 * @param a
	 * @param mh
	 * @param ta
	 * @param th
	 */
	public Animations(String  s , int a , int mh ,int ta , int th ) {
		// TODO Auto-generated constructor stub		
		assert s != "segment" && s != "etoile" && s != "losange" && s != "trapeze" && s!= "cercle" && s!= "carre" && s != "triangleEq"; 
		assert a >= 0 & a <=360 ; 
		assert a >= 0 & a <=360 ; 
		assert a >= 0 & a <=360 ; 
		assert a >= 0 & a <=360 ; 
		assert a >= 0 & a <=360 ; 
		
		type = s ; 
		angle = a ; 
		multipleHomotesie = mh ; 
		translationAvant = ta ; 
		translationHauteur = th ; 
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public double getAngle() {
		return angle;
	}


	public void setAngle(double angle) {
		this.angle = angle;
	}


	public double getMultipleHomotesie() {
		return multipleHomotesie;
	}


	public void setMultipleHomotesie(double multipleHomotesie) {
		this.multipleHomotesie = multipleHomotesie;
	}


	public double getTranslationAvant() {
		return translationAvant;
	}


	public void setTranslationAvant(double translationAvant) {
		this.translationAvant = translationAvant;
	}


	public double getTranslationHauteur() {
		return translationHauteur;
	}


	public void setTranslationHauteur(double translationHauteur) {
		this.translationHauteur = translationHauteur;
	}

}
 