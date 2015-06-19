import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Use this class to draw a list of drawables on the main JPanel
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	final int height = 800;
	final int width = 1500;

	ExtraMagicalJPanel graphicsPanel;

	public static void main(String [] args) {
		// Make the thing that does the displaying
		MainFrame frame = new MainFrame();
		
		// First we go to the menu page
		Page menu = new MainMenu(frame);
		Page nextPage = menu.startLoop();
		
		// Then we keep going to whatever page comes next until we get a null
		while(nextPage != null) {
			nextPage = nextPage.startLoop();
		}
		
		System.out.println("All done, no more pages left to show.");

		
	}

	MainFrame() {
		super("Deseroth Racer");
		this.setSize(new Dimension(width,height));
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.graphicsPanel = new ExtraMagicalJPanel();
		this.add(graphicsPanel);
	}
	
	public void draw(List<Drawable> listOfThingsToDraw) {
		graphicsPanel.setThingsToDraw(listOfThingsToDraw);
		graphicsPanel.repaint();
	}

    private class ExtraMagicalJPanel extends JPanel {

    	List<Drawable> toDraw = new ArrayList<>();
    	
    	ExtraMagicalJPanel() {
    		super();
        }
        
        public void setThingsToDraw(List<Drawable> listOfThingsToDraw) {
        	toDraw = listOfThingsToDraw;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
    		for(Drawable a : toDraw) {
    			a.draw(g);
    		}
        }
    }

	
}