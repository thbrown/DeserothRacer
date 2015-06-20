package Screens;

import java.awt.Graphics;

public interface Entity {

	void draw(Graphics g);
	
	void update();
	
	// For collision detection
	int getHeight();
	int getWidth();
	
}
