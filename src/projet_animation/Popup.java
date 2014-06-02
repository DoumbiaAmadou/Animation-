package projet_animation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

class PopUp extends JPopupMenu {
	
    JMenuItem ajoutAnimation , supprimerAnnilation, couleur, supprimer;
    ObjectGeo geo ;
    AddMouv jdial ; 

    
    public PopUp(final ObjectGeo geo, final ObjectFrame objf){
    	this.geo = geo ;
    	jdial =	new AddMouv(geo);
    	JMenuItem	titre = new JMenuItem(geo.getType().toUpperCase());
    	titre.setEnabled(false);
    	titre.addActionListener(new PopEcouteur(geo , jdial) );
    	
    	couleur = new JMenuItem("couleur");
		
		couleur.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				geo.setColor(JColorChooser.showDialog(objf, "Choisir une couleur", Color.black));
				objf.paintNow();
			}
		});
    	
    	
    	ajoutAnimation = new JMenuItem("ajouter Animation?");
    	ajoutAnimation.addActionListener(new PopEcouteur(geo , jdial) );
    	
    	supprimerAnnilation = new JMenuItem("supprimerAnnilation?");
    	supprimerAnnilation.addActionListener(new  PopEcouteur(geo , jdial));
    	
    	supprimer = new JMenuItem("supprimer figure");
    	
    	supprimer.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			objf.getFigures().remove(geo.getId());
    			objf.paintNow();
    		}
    		
    	});
    	
    	
    	add(titre);
        add( new Separator());
    	add(couleur);
    	add(supprimer);
        add(ajoutAnimation);
        add(supprimerAnnilation);
    }
    class PopEcouteur implements ActionListener {
    	
    	ObjectGeo geo ; 
    	public PopEcouteur(ObjectGeo g ,AddMouv m  ) {
			// TODO Auto-generated constructor stub
		
    	geo = g ; 
    	}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
		
			if(arg0.getActionCommand().equals("ajouter Animation?"))
			{	System.out.println( arg0.getActionCommand());
				jdial.show();
			}
		}
    	
    }
    


}