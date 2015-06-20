import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Use this class to draw things on the main window's canvas
 */
@SuppressWarnings("serial")
public class MainFrame extends Canvas {
	
	public static void main(String [] args) {
		// First make the frame
		MainFrame frame = new MainFrame();
		
		// Go to the menu page
		Page menu = new MainMenu(frame);
		Page nextPage = menu.executePage();
		
		// Then we keep going to whatever page comes next until we get a null
		while(nextPage != null) {
			nextPage = nextPage.executePage();
		}
		
		System.out.println("All done, no more pages left to show.");
	}

	public static int height = 800;
	public static int width = 1500;
	
	BufferStrategy strategy;

	MainFrame() {
		JFrame window = new JFrame("Deseroth Racer");
		
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
	
	List<Drawable> toDraw = new ArrayList<>();

    public void draw(List<Drawable> toDraw) {
    	Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
    	
    	// Clear the screen
		g.setColor(Color.black);
		g.fillRect(0,0,width,height);
		
		for(Drawable a : toDraw) {
			a.draw(g);
		}
		
    	g.dispose();
    	strategy.show();
    }
}