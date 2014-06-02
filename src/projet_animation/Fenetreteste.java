package projet_animation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
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
import javax.swing.table.AbstractTableModel;
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

	ObjectFrame objf ; 
	private JTabbedPane panelCentre ;
	private JPanel panelbas ;
	private JPanel panelgauche ;

	private JPanel paneldroit;

	JFileChooser chooser = new JFileChooser();

	// les actions 
	Action nouveau ;


	// les ressources 
	private ResourceBundle rz; 
	private MonEcouteurAction typeListener ;

	JButton play; 
	private JButton pause; 
	private JButton avancerapide; 
	private JButton reculerRapide;
	private JButton stop; 
	boolean boolPlayIsPressed = false ; 

	// contructucteurs 

	public Fenetreteste(){
		// TODO Auto-generated constructor stub
		Locale local= new Locale("fr_FR"); 
		rz = ResourceBundle
				.getBundle("projet_animation.messages" ,local);
		setSize(new Dimension(850,450));
		setBackground(Color.white);


		/**
		 * le menu du systeme
		 *  menu 
		 */
		typeListener = new MonEcouteurAction(rz); 
		JMenuBar barPrincipale = new JMenuBar();
		JMenu fichier = new JMenu(rz.getString("Fichier"));  //$NON-NLS-1$
		JMenu edit = new JMenu(rz.getString("edition")); //$NON-NLS-1$
		JMenu affichage = new JMenu(rz.getString("Affichage"));  //$NON-NLS-1$
		JMenu outils = new JMenu(rz.getString("Outils"));  //$NON-NLS-1$
		JMenu aide = new JMenu(rz.getString("Aide"));  //$NON-NLS-1$

		//fichier

		nouveau  = new MonActionItems (rz.getString("Nouveau"),
				KeyStroke.getKeyStroke(KeyEvent.VK_N ,InputEvent.ALT_DOWN_MASK+InputEvent.CTRL_DOWN_MASK) 
				, new ImageIcon("donnees/add.png")); 

		JMenuItem nouv = new JMenuItem(nouveau);
		nouv.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				objf.reinit();
			}
		});
		fichier.add(nouv);

		JMenuItem ouvrir = new JMenuItem(rz.getString("Ouvrir"));

		ouvrir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					
					int returnVal = chooser.showOpenDialog(objf);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						chooser.setCurrentDirectory(new File(chooser.getSelectedFile().getAbsolutePath()));
						objf.open(chooser.getSelectedFile());
					}
			}
		});



		fichier.add(ouvrir);

		fichier.add(new JMenuItem(rz.getString("Ajouter"))); //$NON-NLS-1$

		JMenuItem enregistrer = new JMenuItem(rz.getString("Enregistrer"));

		enregistrer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try{
					int returnVal = chooser.showSaveDialog(objf);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						objf.save(chooser.getSelectedFile());
						chooser.setCurrentDirectory(new File(chooser.getSelectedFile().getAbsolutePath()));
					}

				}catch(Exception e){
					System.out.println("pb enregistrement fichier: "+e.getMessage());
				}
			}
		});

		fichier.add(enregistrer);
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
		barPrincipale.add(aide);
		
		JMenuBar barFichier = new JMenuBar();
		setJMenuBar(barPrincipale);

		/**
		 *  Layout du systeme
		 * le fenetrage du syteme 
		 * 
		 */


		/**
		 *  panel droit 
		 */
		paneldroit = new JPanel() ; 
		paneldroit.setLayout(new FlowLayout()); 
		paneldroit.setBorder(BorderFactory.createLineBorder(Color.black)); 



		/** 
		 * creation de la panel de creation d'objet dessinable
		 * 
		 */
		JPanel paneldroithaut = new JPanel() ;
		paneldroithaut.setLayout(new GridLayout(4,2, 10, 10)); 

		JButton b1 = new JButton("segment") ; 
		b1.setSize(4,4);
		JButton b2 = new JButton("triangleEq") ; 
		JButton b3 = new JButton("carre") ; 
		JButton b4 = new JButton("cercle") ; 
		JButton b5 = new JButton("losange") ; 
		JButton b6 = new JButton("trapeze") ; 
		JButton b7 = new JButton("pentagone") ; 
		JButton b8 = new JButton("parallelogramme") ; 

		b1.addActionListener(this.typeListener); 
		b2.addActionListener(this.typeListener); 
		b3.addActionListener(this.typeListener); 
		b4.addActionListener(this.typeListener); 
		b5.addActionListener(this.typeListener); 
		b6.addActionListener(this.typeListener); 
		b7.addActionListener(this.typeListener); 
		b8.addActionListener(this.typeListener); 

		paneldroithaut.add(b1);
		paneldroithaut.add(b2);
		paneldroithaut.add(b3);
		paneldroithaut.add(b4);
		paneldroithaut.add(b5);
		paneldroithaut.add(b6);
		paneldroithaut.add(b7);
		paneldroithaut.add(b8);


		b1.setPreferredSize(new Dimension(90, 20));
		b2.setPreferredSize(new Dimension(90, 20));
		b3.setPreferredSize(new Dimension(90, 20));
		b4.setPreferredSize(new Dimension(90, 20));
		b5.setPreferredSize(new Dimension(90, 20));
		b6.setPreferredSize(new Dimension(90, 20));
		b7.setPreferredSize(new Dimension(90, 20));
		b8.setPreferredSize(new Dimension(90, 20));

		paneldroit.add( paneldroithaut);
			
		//add(paneldroit , BorderLayout.EAST) ; 

		// panel gauche 
		panelgauche = new JPanel() ;
		panelgauche.setLayout(new BorderLayout());

		/**
		 * l'arboressance du fichier
		 */
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

		play = new JButton(new ImageIcon("donnees/bouton-vert-jouent-icone-9381-48.png"));
		pause = new JButton(new ImageIcon("donnees/bouton-vert-faire-une-pause-icone-9381-48.png"));
		avancerapide = new JButton(new ImageIcon("donnees/bouton-avance-rapide-vert-icone-8002-32.png"));
		reculerRapide = new JButton(new ImageIcon("donnees/bouton-vert-rewind-icone-8505-32.png"));
		stop = new JButton(new ImageIcon("donnees/bouton-vert-stop-icone-9375-32.png")) ; 


		play.addActionListener(	new MonEcouteurAction(rz));  
		pause.addActionListener(	new MonEcouteurAction(rz)); 
		avancerapide.addActionListener(	new MonEcouteurAction(rz));  
		reculerRapide.addActionListener(	new MonEcouteurAction(rz));  
		stop.addActionListener( new MonEcouteurAction(rz));

		panelbas.add(reculerRapide);
		panelbas.add(play);
		panelbas.add(avancerapide);
		panelbas.add(stop);



		//panel central 
		panelCentre = new Tabbedpanel( this);

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
		add(objf.slide, BorderLayout.SOUTH);
		
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


		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new  Fenetreteste() ; 
			}});
	}

	/**
	 * @author Doumbia
	 * une classe interne de render comboibox
	 */

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


			if(e.getSource().equals(play)){

				if(boolPlayIsPressed){

					if(objf.pause==true){
						play.setIcon(new  ImageIcon("donnees/bouton-vert-faire-une-pause-icone-4147-48.png"));
					}else{	
						play.setIcon(new ImageIcon("donnees/bouton-vert-jouent-icone-9381-48.png"));
					}
					objf.pause = !objf.pause ; 

				}
				if(!boolPlayIsPressed){

					play.setIcon(new  ImageIcon("donnees/bouton-vert-faire-une-pause-icone-4147-48.png"));


					System.out.println("lancer animation");
					boolPlayIsPressed = true ;
					objf.play=true ;
					new Thread(new Runnable() {
						public void run() {
							objf.anime();
							play.setIcon(new ImageIcon("donnees/bouton-vert-jouent-icone-9381-48.png"));
							System.out.println("fini");
						}
					}).start() ; 


				}
			}
			if(e.getSource().equals(stop)){

				play.setIcon(new ImageIcon("donnees/bouton-vert-jouent-icone-9381-48.png"));
				
				boolPlayIsPressed = false ;
				objf.play=false ;
				objf.pause = false ;
				objf.setVitesse(10);
				
				objf.paintImmediately(0,0,9000,9000);
				
			}
			if(e.getSource().equals(avancerapide)){
				objf.setVitesse( objf.getVitesse()*2 );
			}
			if(e.getSource().equals(reculerRapide)){
				if(objf.getVitesse()>=20)
				objf.setVitesse( objf.getVitesse()/2) ;
			}
			else
			{

				objf.ajouterFigure(e.getActionCommand());
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
