
package Screens;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Use this class to draw things on the main window's canvas
 */
@SuppressWarnings("serial")
public class MainFrame extends Canvas {
	
	public static void main(String [] args) {
		// Make the thing that does the displaying and user input
		MainFrame frame = new MainFrame();
		
		// First we go to the main menu page
		Page menu = new MainMenu(frame);
		Page nextPage = menu.executePage();
		
		//Page race = new Race(frame);
		//Page nextPage = race.executePage();
		
		frame.setFocusable(true);
		//KeyListener kl = (KeyListener) race;
		//frame.addKeyListener(race);
		//Page nextPage = ((Page) race).executePage();
		
		
		// Then we keep going to whatever page comes next until we get a null
		while(nextPage != null) {
			nextPage = nextPage.executePage();
		}
		
		System.out.println("All done, no more pages left to show.");

	}

	public static int height = 800;
	public static int width = 1500;
	
	BufferStrategy strategy;

	JFrame window;
	
	MainFrame() {
		window = new JFrame("Deseroth Racer");
		
		JPanel panel = (JPanel) window.getContentPane();
		panel.setPreferredSize(new Dimension(width,height));
		panel.setLayout(null);
		
		setBounds(0,0,width,height);
		panel.add(this);
		
		setIgnoreRepaint(true);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
		
		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}

    public void draw(List<Drawable> toDraw) {
    	Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
    	
    	// Clear the screen
		g.setColor(Color.GRAY);
		g.fillRect(0,0,width,height);
		
		for(Drawable a : toDraw) {
			a.draw(g);
		}
		
    	g.dispose();
    	strategy.show();
    }
    
    /**
     * Same as draw, but java but accepts a list of entities instead of Drawables.
     * @param toDraw
     */
    public void drawEntities(List<Entity> toDraw) {
    	Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
    	
    	// Clear the screen with a big black rectangle
		g.setColor(Color.black);
		g.fillRect(0,0,width,height);
		
		for(Entity a : toDraw) {
			a.draw(g);
		}
		
    	g.dispose();
    	strategy.show();
    }
    
    /**
     * Removes all mouseListeners, mouseMotionListeners, and keyListeners from this component. Feel free to expand this.
     */
    public void removeListeners() {
    	MouseListener[] mls = this.getMouseListeners();
    	for(MouseListener ml : mls) {
    		this.removeMouseListener(ml);
    	}
    	
    	MouseMotionListener[] mmls = this.getMouseMotionListeners();
    	for(MouseMotionListener mml : mmls) {
    		this.removeMouseMotionListener(mml);
    	}
    	
    	KeyListener[] kls = this.getKeyListeners();
    	for(KeyListener kl : kls) {
    		this.removeKeyListener(kl);
    	}
    }
}