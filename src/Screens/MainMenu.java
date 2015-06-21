package Screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
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
	
	static Page nextPage = null; // Making this static for now to get the menu working. Should move this to an instance variable.
	
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
		menuElements.add(new MenuElement("Deseroth Racer", TITLE_TEXT_X_OFFSET, TITLE_TEXT_Y_OFFSET, TITLE_TEXT_SIZE, false, null));
		menuElements.add(new MenuElement("Play", MENU_TEXT_X_OFFSET, MENU_TEXT_Y_OFFSET+MENU_TEXT_SPACING*1, MENU_TEXT_SIZE, true, new Race(mainframe)));
		menuElements.add(new MenuElement("Options", MENU_TEXT_X_OFFSET, MENU_TEXT_Y_OFFSET+MENU_TEXT_SPACING*2, MENU_TEXT_SIZE, true, null));
		menuElements.add(new MenuElement("Credits", MENU_TEXT_X_OFFSET, MENU_TEXT_Y_OFFSET+MENU_TEXT_SPACING*3, MENU_TEXT_SIZE, true, null));
		menuElements.add(new MenuElement("Quit", MENU_TEXT_X_OFFSET, MENU_TEXT_Y_OFFSET+MENU_TEXT_SPACING*4, MENU_TEXT_SIZE, true, null));
		menuElements.add(new MousePointer());
	}

	private Page loop() {
		while(true) {
			long time = System.nanoTime();
			
			// Check Collisions (brute force) and update
			for (Entity a : menuElements) {
				Rectangle aRect = a.getRectangle();
				if(aRect == null) { // For some entities we don't care about collisions
					a.update(Collections.EMPTY_LIST);
					continue;
				}
				List<Entity> thingsCollidingWithA = new LinkedList<>();
				for (Entity b : menuElements) {
					if(a == b) {
						continue; // You can't collide with yourself
					}
					Rectangle bRect = b.getRectangle();
					if(bRect == null) {
						continue; // For some entities we don't care about collisions
					}
					
					if (aRect.intersects(bRect)) {
						thingsCollidingWithA.add(b);
					}
				}
				
				// Now that we know everything that is colliding with this entity, we'll have the entity update itself
				a.update(thingsCollidingWithA);
			}
			
			// Add/Remove any Entities from the Page (We do this here to avoid concurrent modification Exceptions).
			menuElements.removeAll(toRemove);
			toRemove.clear();
			menuElements.addAll(toAdd);
			toAdd.clear();
			
			// Draw all the things
			mainframe.drawEntities(menuElements);
			
			// Should we go to the next page???
			if(nextPage != null) {
				return nextPage;
			}

			// Sleep for some amount of time
			long timeToSleep = 10000000 - (System.nanoTime() - time);
			if(timeToSleep > 0) {
				if(timeToSleep < 0) {
					System.out.println("Warning: System is overloaded.");
				}
				try {
					Thread.sleep(timeToSleep/1000000, (int) (timeToSleep%1000000));
				} catch (InterruptedException e) {
					// Meh
				}
			}
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
		private final int WIDTH = 250;
		private final int HEIGHT = 45;
		Font font;
		Color c = Color.WHITE;
		boolean clickable;
		Page nextPage;

		MenuElement(String content, int x, int y, int fontSize, boolean clickable, Page nextPage) {
			this.content = content;
			this.x = x;
			this.y = y;
			this.font = new Font(Font.MONOSPACED, Font.ITALIC, fontSize);
			this.clickable = clickable;
			this.nextPage = nextPage;
		}

		@Override
		public void draw(Graphics g) {
			g.setColor(c);
			g.setFont(font);
			g.drawString(content, x, y);
			//g.drawRect(x, y-35, WIDTH, HEIGHT); Debug to see menu bounding boxes
		}

		@Override
		public void update(List<Screens.Entity> collisions) {
			c = Color.WHITE;
			for(Entity e : collisions) {
				if(e instanceof MousePointer) {
					c = Color.YELLOW;
					if(click) {
						if(this.nextPage != null) {
							MainMenu.nextPage = this.nextPage;
							//System.out.println(this.nextPage);
						} else {
							//System.out.println("Null");
							System.exit(0);
						}
					}
					break;
				}
			}
		}

		@Override
		public Rectangle getRectangle() {
			if(clickable) {
				return new Rectangle(x,y-35,WIDTH,HEIGHT);
			} else {
				return null;
			}
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
		public void update(List<Entity> collisions) {
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
		public Rectangle getRectangle() {
			return null; // We don't want to waste compute power calculating collisions for these particals
			//return new Rectangle(x,y,img.getWidth(),img.getHeight());
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
		public void update(List<Entity> collisions) {
			x -= speed;

			// If the image is way off the page, we'll move it back to the left
			if(x > MainFrame.width) {
				this.x = - MainFrame.width - img.getWidth();
			}
		}

		@Override
		public Rectangle getRectangle() {
			return new Rectangle(x,y,img.getWidth(),img.getHeight());
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
		public void update(List<Screens.Entity> collisions) {
			yVel += ((3-Math.random()*4));
			x += xVel;
			y += yVel;
			SIZE ++;
			lifeCounter++;
			translucency-= 4;
			if( translucency > 0) {
				c = new Color(139,69,19,translucency);
			}
			if(lifeCounter > 30) {
				markForRemoval(this);
			}
		}

		@Override
		public Rectangle getRectangle() {
			return new Rectangle(x,y,SIZE,SIZE);
		}
		
	}
	
	// This is just a dot that follows the mouse pointer. It is used to detect a mouse collision with a menu element
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
		public void update(List<Entity> collisions) {
			x = mouseX;
			y = mouseY;	
			click = false;
		}

		@Override
		public Rectangle getRectangle() {
			return new Rectangle(x,y,SIZE,SIZE);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Meh
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
