package projet_animation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.EventListener;
import java.util.Vector;

import javax.net.ssl.KeyStoreBuilderParameters;
import javax.swing.*; 
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicBorders.SplitPaneBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;


public class Fenetreteste extends JFrame {

	JTextField ecran ;
	// les panels 
	TreeModel arbre;
	protected JTree Jarbre;
	
	private JTabbedPane panelCentre ;
	private JPanel panelbas ;
	private JPanel panelgauche ;
	private JPanel panelhaut ;
	private JPanel paneldroit;
	private JPanel p1;
	
	
	// les actions 
	Action nouveau ;
	
	//les combo box 
	private JComboBox modele1;
	private JComboBox combo1;
	
	// les ressources 
	private ResourceBundle rz; 

	// contructucteurs 
	
	public Fenetreteste(){
		// TODO Auto-generated constructor stub
	
		Locale local= new Locale("fr_FR"); 
		 rz = ResourceBundle
				.getBundle("projet_animation.messages" ,local);
		setSize(new Dimension(850,450));
		setBackground(Color.white);
		/**le menu du systeme
		 *  menu 
		 */
	
		JMenuBar barPrincipale = new JMenuBar();
		JMenu fichier = new JMenu(rz.getString("Fichier"));  //$NON-NLS-1$
		JMenu edit = new JMenu(rz.getString("edition")); 	 //$NON-NLS-1$
		JMenu affichage = new JMenu(rz.getString("Affichage"));  //$NON-NLS-1$
		JMenu outils = new JMenu(rz.getString("Outils"));  //$NON-NLS-1$
		JMenu aide = new JMenu(rz.getString("Aide"));  //$NON-NLS-1$
		
		//fichier
		
		nouveau  = new MonActionItems (rz.getString("Nouveau"),
				KeyStroke.getKeyStroke(KeyEvent.VK_N ,InputEvent.ALT_DOWN_MASK+InputEvent.CTRL_DOWN_MASK) 
				, new ImageIcon("donnees/add.png")); 

		
		
		fichier.add(new JMenuItem(nouveau)); //$NON-NLS-1$
		
		fichier.add(new JMenuItem(rz.getString("Ouvrir")));  //$NON-NLS-1$
		fichier.add(new JMenuItem(rz.getString("Ajouter"))); //$NON-NLS-1$
		
		fichier.add(new JMenuItem(rz.getString("Enregistrer")));  //$NON-NLS-1$
		fichier.add(new JMenuItem(rz.getString("Fermer")));  //$NON-NLS-1$
		
		//edit
		
		edit.add(new JMenuItem(rz.getString("Retour"))); //$NON-NLS-1$
		edit.add(new JMenuItem(rz.getString("Suivant")));  //$NON-NLS-1$
		edit.add(new JMenuItem(rz.getString("Couper"))); //$NON-NLS-1$
		
		edit.add(new JMenuItem(rz.getString("Copier")));  //$NON-NLS-1$
		edit.add(new JMenuItem(rz.getString("Coller")));  //$NON-NLS-1$
	
		barPrincipale.add(fichier);
		barPrincipale.add(edit);
		barPrincipale.add(affichage);
		barPrincipale.add(outils);
		//barPrincipale.setHelpMenu(aide);
		barPrincipale.add(aide);
		//action principal 
		//Action langue	 = new Action( new MonActionItems('L' , KeyStroke.getKeyStroke(KeyEvent.VK_L ,InputEvent.ALT_DOWN_MASK,InputEvent.CTRL_DOWN_MASK)));

			JMenuBar barFichier = new JMenuBar();
			setJMenuBar(barPrincipale);
		
		/** Layout du systeme
		 * le fenetrage du syteme 
		 * 
		 */
	
		
		 // panel droit 
		 paneldroit = new JPanel() ; 
		 paneldroit.setLayout(new FlowLayout()); 
		paneldroit.setBorder(BorderFactory.createLineBorder(Color.black)); 
		 	/** creation de la panel de creation d'objet dessinable
		 	 * 
		 	 */
		 JPanel paneldoithaut = new JPanel() ;
		 paneldoithaut.setLayout(new GridLayout(4,2, 10, 10)); 
		
			JButton b1 = new JButton("segment") ; 
			b1.setSize(4,4);
			JButton b2 = new JButton("triangleEq") ; 
			JButton b3 = new JButton("carre") ; 
			JButton b4 = new JButton("cercle") ; 
			JButton b5 = new JButton("losange") ; 
			JButton b6 = new JButton("trapeze") ; 
			JButton b7 = new JButton("etoile") ; 
			JButton b8 = new JButton("polygone") ; 
			
			paneldoithaut.add(b1);
			paneldoithaut.add(b2);
			paneldoithaut.add(b3);
			paneldoithaut.add(b4);
			paneldoithaut.add(b5);
			paneldoithaut.add(b6);
			paneldoithaut.add(b7);
			paneldoithaut.add(b8);
			
			b1.setPreferredSize(new Dimension(90, 20));
			b2.setPreferredSize(new Dimension(90, 20));
			b3.setPreferredSize(new Dimension(90, 20));
			b4.setPreferredSize(new Dimension(90, 20));
			b5.setPreferredSize(new Dimension(90, 20));
			b6.setPreferredSize(new Dimension(90, 20));
			b7.setPreferredSize(new Dimension(90, 20));
			b8.setPreferredSize(new Dimension(90, 20));
			paneldroit.add( paneldoithaut);
			 //add(paneldroit , BorderLayout.EAST) ; 
			 
			 // panel gauche 
			 panelgauche = new JPanel() ;
			 panelgauche.setLayout(new BorderLayout());
			 DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Liste des Annimation Courrantes");
			arbre = new DefaultTreeModel(racine);
			racine.add(new DefaultMutableTreeNode("animation1"));
				
			
			Jarbre = new JTree();
			Jarbre.setModel(arbre);	
			
			Jarbre.setModel(arbre);
			TreeSelectionModel modeleSelection = new DefaultTreeSelectionModel();
			modeleSelection.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			Jarbre.setSelectionModel(modeleSelection);
			Jarbre.addTreeSelectionListener( new gestionSelection());
			Jarbre.setPreferredSize(new Dimension(200, 300));
			panelgauche.add(Jarbre, BorderLayout.CENTER);
		 // add(panelgauche , BorderLayout.WEST) ; 
	
		 // panel sud  
		 panelbas = new JPanel() ; 
		panelbas.add(new JButton("click me!"));
		 panelbas.setBorder(BorderFactory.createLineBorder(Color.black)); 
		 
		 
		//panel central 
		 panelCentre = new Tabbedpanel();
	
		
		JSplitPane cd = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT , panelCentre, paneldroit); 
		cd.setDividerLocation(0.3) ;
		cd.setOneTouchExpandable(true);
		cd.setResizeWeight(.7d);
		cd.setDividerLocation(500);
		JSplitPane gc = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT ) ;
		
		gc.add(panelgauche);
		gc.add(cd) ;
		gc.setResizeWeight(.2d);
		gc.setOneTouchExpandable(true);
		gc.setDividerLocation(100);
		
		JSplitPane hb = new JSplitPane(JSplitPane.VERTICAL_SPLIT ,gc , panelbas); 
		//gc.setResizeWeight(.5d);
		hb.setResizeWeight(.9d);
		add(hb);
		setMinimumSize(new Dimension( 700 , 400));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		setVisible(true) ;
	}		
	
	

	class gestionSelection implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
			/*try {
			/*	DefaultMutableTreeNode noeud = (DefaultMutableTreeNode) e
						.getPath().getLastPathComponent();
				Mail m = (Mail) noeud.getUserObject();
				// etiquette.setText(m.toString());

				title.setText("title : " + m.getTitre());
				form.setText("form : " + m.getFrom());
				text.setText("text : " + m.getContent());
			} catch (Exception e1) {
				title.setText("title : ");
				form.setText("form : ");
				text.setText("text : ");
			}
			*/
		}

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()) ; 
		} catch (Exception e) {
			// TODO: handle exception
		}
		new  Fenetreteste() ; 
		
	}
	
	/**
	 * @author Doumbia
	 * une classe interne de render comboibox
	 */
	class MonRender extends JLabel implements ListCellRenderer<Object>{
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();	
	public MonRender () {
			//setOpaque(true);
			//setHorizontalAlignment(200); 
			//setVerticalAlignment(200); 
			
		}
		
	
		 public Component getListCellRendererComponent(JList list, Object value, int index,boolean isSelected, boolean cellHasFocus) {
			    Font theFont = null;
			    Color theForeground = null;
			    Icon theIcon = null;
			    String theText = null;

			    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,isSelected, cellHasFocus);
			    JLabel  val =  (JLabel) value; 
			
			    theFont = val.getFont() ;
			      theForeground = val.getForeground(); 
			      theIcon = val.getIcon();
			      theText = val.getText();
			   
			    
			    if (!isSelected) {
			      renderer.setForeground(theForeground);
			    }
			    if (theIcon != null) {
			      renderer.setIcon(theIcon);
			    }
			    renderer.setText(theText);
			    renderer.setFont(theFont);
			    return renderer;
			  }
			}
	class MonEcouteurAction  implements ActionListener{
		ResourceBundle	rz ;
		int valeur = 0  ;  
		public MonEcouteurAction(ResourceBundle r) {
			// TODO Auto-generated constructor stub
			rz = r ; 
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			 String stringCmd = e.getActionCommand();
			 	System.out.println(stringCmd);
			int i;
			
			i = Integer.parseInt( ecran.getText()) ; 
			if(rz.getString(Messages.getString("Fenetreteste.14")).equals(stringCmd)) //$NON-NLS-1$
				System.exit(0);
			else
			if(stringCmd.equals(Messages.getString("Fenetreteste.15"))){ //$NON-NLS-1$
//				valeur =Integer.getInteger(ecran.getText()) ; 
//				ecran.setText("0"); 
				JButton b = 	new JButton(Messages.getString("Fenetreteste.16")+valeur) ; //$NON-NLS-1$
				 p1.add(b) ;
				 p1.revalidate() ; 
				 p1.repaint() ; 
				 valeur++;
		}
		else 
			if(stringCmd.equals(Messages.getString("Fenetreteste.17"))) //$NON-NLS-1$
				{			valeur =+Integer.getInteger(ecran.getText()) ; 
					ecran.setText(Messages.getString("Fenetreteste.18")+valeur); //$NON-NLS-1$
				}
			else
				{JOptionPane.showMessageDialog(null, Messages.getString("Fenetreteste.19")+i+ Messages.getString("Fenetreteste.20"));  //$NON-NLS-1$ //$NON-NLS-2$
				ecran.setText(Messages.getString("Fenetreteste.21")+i+stringCmd); //$NON-NLS-1$
				}
		}
	} 
	class MonActionItems extends AbstractAction {
	    // This is our sample action. It must have an actionPerformed() method,
	    // which is called when the action should be invoked.
	    public MonActionItems(String text, KeyStroke keyStroke ,  ImageIcon icon ) {
	      super(text);
		      putValue(SMALL_ICON, icon) ; 
		      putValue(SHORT_DESCRIPTION, text);
		      putValue(ACCELERATOR_KEY, keyStroke);
	      }

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			 
	    	
	    	  }

	 }

}

