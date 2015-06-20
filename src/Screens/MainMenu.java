package Screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;


public class MainMenu implements Page, MouseMotionListener, MouseListener {

	private static final int TITLE_TEXT_SIZE = 100;
	private static final int TITLE_TEXT_X_OFFSET = 50;
	private static final int TITLE_TEXT_Y_OFFSET = 100;

	private static final int MENU_TEXT_SIZE = 50;
	private static final int MENU_TEXT_SPACING = 50;
	private static final int MENU_TEXT_X_OFFSET = 50;
	private static final int MENU_TEXT_Y_OFFSET = 150;

	private static BufferedImage CAR_IMAGE;
	private static BufferedImage BACKGROUND_MOUNTAIN_IMAGE;
	private static BufferedImage BACKGROUND_TREE_IMAGE;
	
	private int mouseX = 0;
	private int mouseY = 0;
	private boolean click = false;

	MainFrame mainframe;
	List<Entity> menuElements;
	
	// We'll load the pictures once here, so we don't have to re-load them each time we create an object
	static {
		try {
			CAR_IMAGE = ImageIO.read(new File("src/Images/car.png"));
			BACKGROUND_MOUNTAIN_IMAGE = ImageIO.read(new File("src/Images/mainMenuBackgroundMountains.png"));
			BACKGROUND_TREE_IMAGE = ImageIO.read(new File("src/Images/mainMenuBackgroundTrees.png"));
		} catch (IOException e) {
			System.out.println("Error: COuld not find image file.");
			e.printStackTrace();
		}
	}

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
		menuElements.add(new ScrollingImage(BACKGROUND_MOUNTAIN_IMAGE, -10, -BACKGROUND_MOUNTAIN_IMAGE.getWidth()));
		menuElements.add(new ScrollingImage(BACKGROUND_MOUNTAIN_IMAGE, -10, -0));
		menuElements.add(new ScrollingImage(BACKGROUND_TREE_IMAGE, -20, 0));
		menuElements.add(new ScrollingImage(BACKGROUND_TREE_IMAGE, -20, -BACKGROUND_TREE_IMAGE.getWidth()));
		menuElements.add(new Car(CAR_IMAGE, 500, 440, 100, 100));
		menuElements.add(new MenuElement("Deseroth Racer", TITLE_TEXT_X_OFFSET, TITLE_TEXT_Y_OFFSET, TITLE_TEXT_SIZE, false));
		menuElements.add(new MenuElement("Play", MENU_TEXT_X_OFFSET, MENU_TEXT_Y_OFFSET+MENU_TEXT_SPACING*1, MENU_TEXT_SIZE, false));
		menuElements.add(new MenuElement("Options", MENU_TEXT_X_OFFSET, MENU_TEXT_Y_OFFSET+MENU_TEXT_SPACING*2, MENU_TEXT_SIZE, false));
		menuElements.add(new MenuElement("Credits", MENU_TEXT_X_OFFSET, MENU_TEXT_Y_OFFSET+MENU_TEXT_SPACING*3, MENU_TEXT_SIZE, false));
		menuElements.add(new MenuElement("Quit", MENU_TEXT_X_OFFSET, MENU_TEXT_Y_OFFSET+MENU_TEXT_SPACING*4, MENU_TEXT_SIZE, false));
		menuElements.add(new MousePointer());
	}

	private Page loop() {
		while(true) {
			long time = System.nanoTime();
			menuElements.removeAll(toRemove);
			toRemove.clear();
			menuElements.addAll(toAdd);
			toAdd.clear();
			mainframe.drawEntities(menuElements);
			updateAll(menuElements);

			long timeToSleep = 10000000 - (System.nanoTime() - time);
			if(timeToSleep > 0) {
				if(timeToSleep < 0) {
					System.out.println("Warning: System is overloaded.");
				}
				try {
					Thread.sleep(timeToSleep/1000000);
				} catch (InterruptedException e) {
					// Meh
				}
			}
		}
	}

	private void updateAll(List<Entity> elements) {
		for(Entity d : elements) {
			d.update();
		}
	}

	List<Entity> toRemove = new LinkedList<>();
	private void markForRemoval(Entity d) {
		toRemove.add(d);
	}

	List<Entity> toAdd = new LinkedList<>();
	private void markForAdd(Entity pod) {
		toAdd.add(pod);
	}

	class MenuElement implements Entity {

		String content;
		int x;
		int y;
		private final int WIDTH = 200;
		private final int HEIGHT = 75;
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

		@Override
		public int getHeight() {
			return HEIGHT;
		}

		@Override
		public int getWidth() {
			return WIDTH;
		}

	}

	class Car implements Entity {

		String content;
		int x;
		int y;
		int height;
		int width;

		BufferedImage img;

		Car(BufferedImage image, int x, int y, int height, int width) {
			this.img = image;
			this.x = x;
			this.y = y;
		}

		@Override
		public void draw(Graphics g) {
			g.drawImage(img, x,y, null);
		}

		@Override
		public void update() {
			// kick up some dust (Back wheel)
			for(int i = 0; i < 5; i++) {
				Entity pod = new PieceOfDust(x + img.getWidth() - 150, y + img.getHeight() - 25);
				markForAdd(pod);
			}
			// Front wheel
			for(int i = 0; i < 5; i++) {
				Entity pod = new PieceOfDust(x + img.getWidth() - 600, y + img.getHeight() - 25);
				markForAdd(pod);
			}
		}

		@Override
		public int getHeight() {
			return img.getHeight();
		}

		@Override
		public int getWidth() {
			return img.getWidth();
		}

	}

	class ScrollingImage implements Entity {

		String content;
		int x = 0;
		int y = 0;
		int speed;

		BufferedImage img;

		ScrollingImage(String fileName, int speed, int startingX) {
			this.x = startingX;
			this.speed = speed;
		}
		
		ScrollingImage(BufferedImage image, int speed, int startingX) {
			img = image;
			this.x = startingX;
			this.speed = speed;
		}

		@Override
		public void draw(Graphics g) {
			g.drawImage(img, x, y, img.getWidth(), MainFrame.height, null);
		}

		@Override
		public void update() {
			x -= speed;

			// If the image is way off the page, we'll move it back to the left
			if(x > MainFrame.width) {
				this.x = - MainFrame.width - img.getWidth();
			}
		}

		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getWidth() {
			// TODO Auto-generated method stub
			return 0;
		}

	}
	
	class PieceOfDust implements Entity {
		
		int x;
		int y;
		double xVel = 20 + ((3-Math.random()*4));
		double yVel = -10;
		int gravity = 1;
		int lifeCounter = 0;
		int translucency = 255;
		Color c = new Color(139,69,19,translucency);
		int SIZE = (int) (Math.random()*10);
		
		PieceOfDust(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void draw(Graphics g) {
			g.setColor(c);
			g.fillOval(x, y, SIZE, SIZE);
		}

		@Override
		public void update() {
			yVel += ((3-Math.random()*4));
			x += xVel;
			y += yVel;
			SIZE ++;
			lifeCounter++;
			translucency-= 4;
			if( translucency > 0) {
				c = new Color(139,69,19,translucency);
			}
			if(lifeCounter > 300) {
				markForRemoval(this);
			}
		}

		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getWidth() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	// This is just a dot used to detect a mouse collision with a menu element
	class MousePointer implements Entity {
		
		int x;
		int y;
		
		private final int SIZE = 2;

		@Override
		public void draw(Graphics g) {
			g.setColor(Color.RED);
			g.fillOval(x, y, SIZE,SIZE);
		}

		@Override
		public void update() {
			x = mouseX;
			y = mouseY;	
			System.out.println(x + " " + y);
			click = false;
		}

		@Override
		public int getHeight() {
			return SIZE;
		}

		@Override
		public int getWidth() {
			return SIZE;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		click = true;	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// meh
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Meh
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Meh
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
