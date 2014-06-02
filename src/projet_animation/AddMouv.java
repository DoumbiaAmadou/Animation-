package projet_animation;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

public class AddMouv extends JDialog{

	/**
	 * 
	 */

	JLabel labAngle ;
	JLabel labDistance;
	JLabel labDuée;
	JLabel labHomoK; 
	JTextField angle ; 
	JTextField durée   ; 
	JTextField distance  ; 
	JTextField HomoK;

	JComboBox<String> combo;
	ObjectGeo geo ; 


	public AddMouv(ObjectGeo g) {
		this.geo = g; 
		// TODO Auto-generated constructor stub
		setSize(300, 300);
		setLocation(200, 200);
		setResizable(false);
		angle = new JTextField(20); 
		durée = new JTextField(20); 
		distance = new JTextField(20);
		HomoK = new JTextField(20); 

		setLayout(new GridLayout(3,1));

		labAngle= new JLabel("angle"); 
		labDistance  = new JLabel("Distance"); 
		labDuée= new JLabel("Durée"); 
		labHomoK  = new JLabel(" Rapport Homothesie") ; 

		JPanel centre = new JPanel();
		centre.setLayout(new GridLayout(4,2));
		centre.add(labAngle);
		centre.add(angle);

		/*
		 * distance: 
		 */
		centre.add(labDistance);
		centre.add(distance);
		distance.setEnabled(false );


		/*
		 * HomoK: 
		 */
		centre.add(labHomoK);
		centre.add(HomoK);
		HomoK.setEnabled(false );

		centre.add(labDuée);
		centre.add(durée);


		combo = new JComboBox<String>();


		// combo box $ 
		combo.setPreferredSize(new Dimension(100, 20));
		combo.addItem("rotation");
		combo.addItem("cosinus");
		combo.addItem("sinus");
		combo.addItem("translation");
		combo.addItem("similitude");
		combo.addItem("homothetie"); 
		combo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				double a =  0 ; 
				double mh =  0 ; 
				double di =  0 ; 
				int du =  0 ; 
				/*
				 * angle de rotation
				 */

				String  s = ""; 
				/*
				 * v?rification des angles
				 */

				if(combo.getSelectedItem().equals("rotation")){
					angle.setEnabled(true);
					distance.setEnabled(false);
					HomoK.setEnabled(false) ; 
				}
				if(combo.getSelectedItem().equals("cosinus")){

					angle.setEnabled(true);
					distance.setEnabled(true) ; 
					HomoK.setEnabled(false) ; 

				}
				if(combo.getSelectedItem().equals("sinus")){

					angle.setEnabled(true);
					distance.setEnabled(true) ; 
					HomoK.setEnabled(false) ; 

				}
				if(combo.getSelectedItem().equals("translation")){
					angle.setEnabled(true);
					distance.setEnabled(true);
					HomoK.setEnabled(false) ; 
				}
				if(combo.getSelectedItem().equals("similitude")){
					angle.setEnabled(true);
					distance.setEditable(false);
					HomoK.setEnabled(true) ; 
				}
				if(combo.getSelectedItem().equals("homothetie")){

					angle.setEnabled(false);
					distance.setEnabled(false); 
					HomoK.setEnabled(true); 
				}	
			}
		});



		JPanel p =   new JPanel();
		p.add(combo);
		add(p); 
		add(centre);
		JPanel p1 =   new JPanel();
		JButton valide = new JButton("Valider"); 

		valide.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				double a =  0 ; 
				double mh =  0 ; 
				double di =  0 ; 
				int du =  0 ; 
				/*
				 * angle de rotation
				 */

				String  s = ""; 
				/*
				 * v?rification des angles
				 */
				if(angle.isEnabled()){
					try {
						a= Double.parseDouble(angle.getText()); 
					} catch (Exception e) {
						// TODO: handle exception
						s+="- Votre Anglen'est pas valide \n ";
					}
				}

				/*
				 * verification de Hom
				 * 
				 */
				if(HomoK.isEnabled()){
					try {
						mh= Double.parseDouble(HomoK.getText()); 
					} catch (Exception e) {
						// TODO: handle exception
						s+="- La valeur de votre Homothesie n'est pas valide \n " ; 
					}
				}

				/*
				 * verification de dur?e
				 * 
				 */
				if(durée.isEnabled()){
					try {
						du= Integer.parseInt(durée.getText()); 
					} catch (Exception e) {
						// TODO: handle exception
						s+="- Duré n'est pas Conforme\n " ; 
					}
				}
				/*
				 * verification de distance
				 * 
				 */
				if(distance.isEnabled()){
					try {
						di = Double.parseDouble(distance.getText()); 
					} catch (Exception e) {
						// TODO: handle exception
						s+="- Votre distance n'est pas valide \n " ; 
					}
				}
				if(!s.equals(""))
					JOptionPane.showMessageDialog( null, s, " alors " , JOptionPane.ERROR_MESSAGE);
				else{
					Animations an = new Animations(""+combo.getSelectedItem(), a, mh, di, du);
					geo.addAnimation(an); 
					System.out.println(an.toString());
					dispose();
				}	

			}
		});
		p1.add(valide);
		add(p1);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()) ; 
		} catch (Exception e) {
			// TODO: handle exception
		}
		AddMouv m = new AddMouv(new ObjectGeo(1, "losange")); 
		m.show();

	}

}
