package projet_animation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

class PopClickListener extends MouseAdapter {
	PopUpDemo menu ;
	public PopClickListener() {
		// TODO Auto-generated constructor stub  
		 menu = new PopUpDemo();
	}
    public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
       // if (e.isPopupTrigger())
       //    doPop(e);
    }

    private void doPop(MouseEvent e){
      
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
    
    
    // internal class pour le JpopupMenu 
    class PopUpDemo extends JPopupMenu {
        JMenuItem ajoutAnimation , supprimerAnnilation;
        public PopUpDemo(){
        	ajoutAnimation = new JMenuItem("ajouter Animation?");
        	ajoutAnimation = new JMenuItem("supprimerAnnilation?"); 
        	 
            add(ajoutAnimation);
        }
    }
}