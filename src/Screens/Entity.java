package Screens;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public interface Entity {

	void draw(Graphics g);
	
	void update(List<Entity> collisions);
	
	// For collision detection
	Rectangle getRectangle();
	
}
