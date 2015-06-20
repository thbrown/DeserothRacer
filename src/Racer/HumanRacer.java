package Racer;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observer;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class HumanRacer extends Racer {
	
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean rightPressed = false;
	private boolean leftPressed = false;
	private boolean slightRightPressed = false;
	private boolean slightLeftPressed = false;
	
	public HumanRacer( float initialDirection, String imageLocation ) {
		this.setDirection(initialDirection);
		this.setxAcceleration(0.0f);
		this.setyAcceleration(0.0f);
		this.setxPosition(100.0f);
		this.setyPosition(100.0f);
		this.setxVelocity(0.0f);
		this.setyVelocity(0.0f);
		this.loadRacerImage(imageLocation);
	}
	
	@Override
	public void setupAccelerationVectors() {
		float xAcc = 0.0f;
		float yAcc = 0.0f;
		float direction = this.getDirection();
		if ( leftPressed ) {
			//TODO: Add turning adjustments to the Acceleration Vector.
			direction += 0.1f;
		}
		if ( rightPressed ) {
			//TODO: Add turning adjustments to the Acceleration Vector.
			direction -= 0.1f;
		}
		/*if ( slightRightPressed ) {
			//TODO: Add turning adjustments to the Acceleration Vector.
			direction -= 0.05f;
		}
		if ( slightLeftPressed ) {
			//TODO: Add turning adjustments to the Acceleration Vector.
			direction += 0.05f;
		}*/
		if ( upPressed )
		{
			xAcc += (float)Math.cos(direction) * this.MAX_ACCELERATION * this.getStateSpeedEffect();
			yAcc += (float)Math.sin(direction) * this.MAX_ACCELERATION * this.getStateSpeedEffect();
		}
		if ( downPressed ) {
			xAcc += (float)Math.cos(direction) * this.MAX_DECELERATION * this.getStateSpeedEffect();
			yAcc += (float)Math.sin(direction) * this.MAX_DECELERATION * this.getStateSpeedEffect();
		}
		this.setxAcceleration(xAcc);
		this.setyAcceleration(yAcc);
		
		//System.out.println(this.getxAcceleration());
		//System.out.println(this.getyAcceleration());
	}
	

	public boolean isUpPressed() {
		return upPressed;
	}

	public void setUpPressed(boolean upPressed) {
		this.upPressed = upPressed;
	}

	public boolean isDownPressed() {
		return downPressed;
	}

	public void setDownPressed(boolean downPressed) {
		this.downPressed = downPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}

	public boolean isSlightRightPressed() {
		return slightRightPressed;
	}

	public void setSlightRightPressed(boolean slightRightPressed) {
		this.slightRightPressed = slightRightPressed;
	}

	public boolean isSlightLeftPressed() {
		return slightLeftPressed;
	}

	public void setSlightLeftPressed(boolean slightLeftPressed) {
		this.slightLeftPressed = slightLeftPressed;
	}
}
