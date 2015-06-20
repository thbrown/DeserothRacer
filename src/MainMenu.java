import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;


public class MainMenu implements Page {
	
	private static final int TITLE_TEXT_SIZE = 100;
	private static final int TITLE_TEXT_X_OFFSET = 50;
	private static final int TITLE_TEXT_Y_OFFSET = 100;
	
	private static final int MENU_TEXT_SIZE = 50;
	private static final int MENU_TEXT_SPACING = 50;
	private static final int MENU_TEXT_X_OFFSET = 50;
	private static final int MENU_TEXT_Y_OFFSET = 150;
	
	private static final String CAR_IMAGE = "src/img/car.png";
	private static final String BACKGROUND_MOUNTAIN_IMAGE = "src/img/mainMenuBackgroundMountains.png";
	private static final String BACKGROUND_TREE_IMAGE = "src/img/mainMenuBackgroundTrees.png";
	
	MainFrame mainframe;
	List<Drawable> menuElements;

	public MainMenu(MainFrame mainframe) {
		this.mainframe = mainframe;
	}
	
	public Page executePage() {
		init();
		Page nextPage = loop();
		return nextPage;
	}
	
	private void init() {
		menuElements = new LinkedList<>();
		menuElements.add(new ScrollingImage(BACKGROUND_MOUNTAIN_IMAGE, -10));
		menuElements.add(new ScrollingImage(BACKGROUND_TREE_IMAGE, -20));
		menuElements.add(new StationaryImage(CAR_IMAGE, 500, 440, 100, 100));
		menuElements.add(new MenuElement("Deseroth Racer", TITLE_TEXT_X_OFFSET, TITLE_TEXT_Y_OFFSET, TITLE_TEXT_SIZE, false));
		menuElements.add(new MenuElement("Play", MENU_TEXT_X_OFFSET, MENU_TEXT_Y_OFFSET+MENU_TEXT_SPACING*1, MENU_TEXT_SIZE, false));
		menuElements.add(new MenuElement("Options", MENU_TEXT_X_OFFSET, MENU_TEXT_Y_OFFSET+MENU_TEXT_SPACING*2, MENU_TEXT_SIZE, false));
		menuElements.add(new MenuElement("Credits", MENU_TEXT_X_OFFSET, MENU_TEXT_Y_OFFSET+MENU_TEXT_SPACING*3, MENU_TEXT_SIZE, false));
		menuElements.add(new MenuElement("Quit", MENU_TEXT_X_OFFSET, MENU_TEXT_Y_OFFSET+MENU_TEXT_SPACING*4, MENU_TEXT_SIZE, false));
	}
	
	private Page loop() {
		while(true) {
			long time = System.nanoTime();
			menuElements.removeAll(toRemove);
			toRemove.clear();
			menuElements.addAll(toAdd);
			toAdd.clear();
			mainframe.draw(menuElements);
			updateAll(menuElements);
			
			long timeToSleep = 10000000 - (System.nanoTime() - time);
			if(timeToSleep > 0) {
				try {
					System.out.println(timeToSleep/1000000);
					Thread.sleep(timeToSleep/1000000);
				} catch (InterruptedException e) {
					System.out.println("System is overoaded");
				}
			}
		}
	}
	
	private void updateAll(List<Drawable> elements) {
		for(Drawable d : elements) {
			d.update();
		}
	}
	
	List<Drawable> toRemove = new LinkedList<>();
	private void markForRemoval(Drawable d) {
		toRemove.add(d);
	}
	
	List<Drawable> toAdd = new LinkedList<>();
	private void markForAdd(Drawable d) {
		toAdd.add(d);
	}

	class MenuElement implements Drawable {
		
		String content;
		int x;
		int y;
		Font font;
		
		MenuElement(String content, int x, int y, int fontSize, boolean clickable) {
			this.content = content;
			this.x = x;
			this.y = y;
			this.font = new Font(Font.MONOSPACED, Font.ITALIC, fontSize);
		}

		@Override
		public void draw(Graphics g) {
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString(content, x, y);
		}

		@Override
		public void update() {
			// These don't move
		}
		
	}
	
	class StationaryImage implements Drawable {
		
		String content;
		int x;
		int y;
		int height;
		int width;
		
		BufferedImage img;
		
		StationaryImage(String fileName, int x, int y, int height, int width) {
			File f = new File(fileName);
			try {
				img = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.x = x;
			this.y = y;
		}

		@Override
		public void draw(Graphics g) {
			g.drawImage(img, x,y, null);
		}

		@Override
		public void update() {
			// These don't move either
		}
		
	}
	
	class ScrollingImage implements Drawable {
		
		String content;
		int x = 0;
		int y = 0;
		int speed;
		
		BufferedImage img;
		
		ScrollingImage(String fileName, int speed) {
			File f = new File(fileName);
			try {
				img = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.x = - img.getWidth();
			this.speed = speed;
		}
		
		ScrollingImage(BufferedImage img, int speed) {
			this.img = img;
			this.x = - img.getWidth();
			this.speed = speed;
		}

		@Override
		public void draw(Graphics g) {
			g.drawImage(img, x, y, img.getWidth(), MainFrame.height, null);
		}

		@Override
		public void update() {
			x -= speed;
			
			// We need to replace the scrolling image with another image
			if(x == 0) {
				markForAdd(new ScrollingImage(img, speed));
			}
				
			// If the image is way off the page, then get rid of it
			if(x > MainFrame.width) {
				markForRemoval(this);
			}
		}

	}
}
