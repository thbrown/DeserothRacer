package Screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class Credits implements Page, MouseMotionListener, MouseListener{

	MainFrame window;
	
	List<Entity> allPageEntities = new LinkedList<>();

	private int mouseX;
	private int mouseY;

	private boolean click;

	private Page nextPage;

	public Credits(MainFrame mainframe) {
		this.window = mainframe;
	}

	@Override
	public Page executePage() {
		init();
		return loop();
	}
	
	private void init() {
		window.removeListeners();
		window.addMouseListener(this);
		window.addMouseMotionListener(this);
		
		allPageEntities.add(new TextElement("Credits", 100, 100, 100, Color.WHITE));
		allPageEntities.add(new TextElement("Page is under construction.", 100, 400, 50, Color.WHITE));
		allPageEntities.add(new TextElement("Click to return to main menu.", 100, 600, 50, Color.WHITE));
	}
	
	private Page loop() {
		while(true) {
			for(Entity e : allPageEntities) {
				e.update(Collections.EMPTY_LIST);
			}
			window.drawEntities(allPageEntities);
			
			// Should we go to the next page???
			if(nextPage != null) {
				return nextPage;
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	class TextElement implements Entity {

		String content;
		int x;
		int y;
		Font font;
		Color c = Color.WHITE;

		TextElement(String content, int x, int y, int fontSize, Color c) {
			this.content = content;
			this.x = x;
			this.y = y;
			this.c = c;
			this.font = new Font(Font.MONOSPACED, Font.ITALIC, fontSize);
		}

		@Override
		public void draw(Graphics g) {
			g.setColor(c);
			g.setFont(font);
			g.drawString(content, x, y);
		}

		@Override
		public void update(List<Screens.Entity> collisions) {
			// TODO
		}

		@Override
		public Rectangle getRectangle() {
			return null;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		click = true;
		nextPage = new MainMenu(window); // We may want to avoid
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

}
