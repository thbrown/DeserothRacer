package Racer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javafx.scene.transform.Rotate;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import Screens.Drawable;


public abstract class Racer implements Drawable{
	
	private float xPosition, yPosition;
	private float xVelocity, yVelocity;
	private float xAcceleration, yAcceleration;
	private float direction; //In radians
	private RacerState state;
	private BufferedImage racerImage;
	
	static final float MAX_ACCELERATION = 4.0f;
	static final float MAX_DECELERATION = -4.0f;
	static final float MAX_VELOCITY = 20.0f;
	
	public void loadRacerImage( String racerImageLocation ) {
	    Image  img = new ImageIcon(racerImageLocation).getImage();

	    racerImage = new BufferedImage(img.getWidth(null), img.getHeight(null),
	        BufferedImage.TYPE_INT_RGB);
	    
	}
	
	public void CalculateVelocity() {
		this.xVelocity += this.xAcceleration;
		if(this.xVelocity > this.MAX_VELOCITY) {
			this.xVelocity = this.MAX_VELOCITY;
		}
		else if (this.xVelocity < (-1)*this.MAX_VELOCITY) {
			this.xVelocity = (-1)*this.MAX_VELOCITY;
		}
		this.yVelocity += this.yAcceleration;
		if(this.yVelocity > this.MAX_VELOCITY) {
			this.yVelocity = this.MAX_VELOCITY;
		}
		else if (this.yVelocity < (-1)*this.MAX_VELOCITY) {
			this.yVelocity = (-1)*this.MAX_VELOCITY;
		}

		//direction = (float) Math.atan2(yVelocity, xVelocity);
		//System.out.println(this.xVelocity);
		//System.out.println(this.yVelocity);
	}
	
	public void CalculatePosition() {
		this.xPosition += this.xVelocity;
		this.yPosition += this.yVelocity;

		//System.out.println(this.xPosition);
		//System.out.println(this.yPosition);
	}
	
	public abstract void setupAccelerationVectors( );
	public float getStateSpeedEffect() {
		if (state == RacerState.SLOW) {
			return 0.8f;
		}
		else if (state == RacerState.NORMAL) {
			return 1.0f;
		}
		else if (state == RacerState.FAST) {
			return 1.2f;
		}
		else {
			return 1.0f;
		}
	}

	public float getxPosition() {
		return xPosition;
	}

	public void setxPosition(float xPosition) {
		this.xPosition = xPosition;
	}

	public float getyPosition() {
		return yPosition;
	}

	public void setyPosition(float yPosition) {
		this.yPosition = yPosition;
	}

	public float getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}

	public float getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(float yVelocity) {
		this.yVelocity = yVelocity;
	}

	public float getxAcceleration() {
		return xAcceleration;
	}

	public void setxAcceleration(float xAcceleration) {
		this.xAcceleration = xAcceleration;
	}

	public float getyAcceleration() {
		return yAcceleration;
	}

	public void setyAcceleration(float yAcceleration) {
		this.yAcceleration = yAcceleration;
	}

	public RacerState getState() {
		return state;
	}

	public void setState(RacerState state) {
		this.state = state;
	}

	public float getDirection() {
		return direction;
	}

	public void setDirection(float direction) {
		this.direction = direction;
	}

	public BufferedImage getRacerImage() {
		return racerImage;
	}

	public void setRacerImage(BufferedImage racerImage) {
		this.racerImage = racerImage;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = racerImage.createGraphics();
		g2.rotate(this.getDirection(),racerImage.getWidth()/2, racerImage.getHeight()/2);
		g.drawImage(racerImage, (int)this.xPosition, (int)this.yPosition, null);
	}

}
