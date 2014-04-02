package projet_animation;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class Tabbedpanel  extends JTabbedPane{
	
	Toile t = new Toile(); 
	public Tabbedpanel() {
		// TODO Auto-generated constructor stub
		add("animation1",t);

	}

}
