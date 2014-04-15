package projet_animation;
import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  
   
public class ButtonListener extends JFrame {  
    public static void main( String[] args ) {  
        new ButtonListener();  
    }  
   
    public ButtonListener() {  
        JButton b = new JButton( "Right click me!" );  
        b.addMouseListener(new PopClickListener());
      //  b.addMouseListener( new RightClicker() );  
        JPanel p = new JPanel();  
        p.add( b );  
        getContentPane().add( p );  
        addWindowListener( new WindowAdapter() {  
            public void windowClosing( WindowEvent e ) {  
                System.exit( 0 );  
            }  
        } );  
        setSize( 300, 300 );  
        setVisible( true );  
    }  
   
    private class RightClicker extends MouseAdapter {  
        public void mousePressed( MouseEvent e ) {  
            if ( e.isMetaDown() ) {  
                System.out.println( "Thanks!" );  
            }  
        }  
    } 
    class PopUpDemo extends JPopupMenu {
        JMenuItem anItem;
        public PopUpDemo(){
            anItem = new JMenuItem("Click Me!");
            add(anItem);
        }
    }
    class PopClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e){
            if (e.isPopupTrigger())
                doPop(e);
        }

        public void mouseReleased(MouseEvent e){
           // if (e.isPopupTrigger())
           //    doPop(e);
        }

        private void doPop(MouseEvent e){
            PopUpDemo menu = new PopUpDemo();
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
