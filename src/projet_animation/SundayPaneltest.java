package projet_animation;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")

public class SundayPaneltest extends JPanel implements MouseMotionListener, MouseListener {

    /**
	 * 
	 */
	

	public SundayPaneltest() {
        setSize(1000, 150);
        setBackground(Color.white);
        setBorder(BorderFactory.createEtchedBorder(Color.orange, Color.red));
    }

    String[] values = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    public void paint(Graphics g) {
        Font font = new Font("Times New Roman", Font.PLAIN, 72);
        g.setFont(font);
        for (int i = 0; i < 10; i++) {
            int step = 130 * i;
            if ((start != null) && ((int) (end.getX() / 130) == i)) {
                g.setColor(Color.orange);
            } else {
                g.setColor(Color.gray);
            }
            g.fill3DRect(step, 0, 130, 100, true);
            g.setColor(Color.black);
            g.drawString(values[i], 50 + step, 65);
        }
        if ((start != null) && (end != null)) {
            g.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
        }
    }

    Point start;

    Point end;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        end = e.getPoint();
        e.getComponent().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int from = (int) (start.getX() / 130);
        int to = (int) (end.getX() / 130);
        String tmp = values[from];
        values[from] = values[to];
        values[to] = tmp;
        start = end = null;
        e.getComponent().repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}


class SundayFrame extends JFrame {

    private SundayPaneltest sunPanel;

    public SundayFrame(String title) {
        super(title);
        sunPanel = new SundayPaneltest();
        sunPanel.addMouseListener((MouseListener) sunPanel);
        sunPanel.addMouseMotionListener(sunPanel);
        add(sunPanel, BorderLayout.CENTER);

    }

}