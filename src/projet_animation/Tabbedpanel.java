package projet_animation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class Tabbedpanel  extends JTabbedPane{
	
	 ObjectFrame t ;
	public Tabbedpanel(Fenetreteste fenetreteste) {
		// TODO Auto-generated constructor stub
		t = new ObjectFrame(fenetreteste); 
		fenetreteste.objf= t ;
		add("animation1",t);
	}
	
	public ObjectFrame getObjetFrame() {
		return t ; 
	}

}
