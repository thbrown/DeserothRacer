package Racer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HumanRacer extends Racer implements KeyListener {
	
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
		this.setxPosition(0.0f);
		this.setyPosition(0.0f);
		this.setxVelocity(0.0f);
		this.setyVelocity(0.0f);
		this.loadRacerImage(imageLocation);
	}
	
	@Override
	public void setupAccelerationVectors() {
		float xAcc = 0.0f;
		float yAcc = 0.0f;
		if ( upPressed )
		{
			xAcc += (float)Math.cos(this.getDirection()) * this.MAX_ACCELERATION * this.getStateSpeedEffect();
			yAcc += (float)Math.sin(this.getDirection()) * this.MAX_ACCELERATION * this.getStateSpeedEffect();
		}
		if ( downPressed ) {

			xAcc += (float)Math.cos(this.getDirection()) * this.MAX_DECELERATION * this.getStateSpeedEffect();
			yAcc += (float)Math.sin(this.getDirection()) * this.MAX_DECELERATION * this.getStateSpeedEffect();
		}
		if ( leftPressed ) {
			//TODO: Add turning adjustments to the Acceleration Vector.
		}
		if ( rightPressed ) {
			//TODO: Add turning adjustments to the Acceleration Vector.
		}
		if ( slightRightPressed ) {
			//TODO: Add turning adjustments to the Acceleration Vector.
		}
		if ( slightLeftPressed ) {
			//TODO: Add turning adjustments to the Acceleration Vector.
		}
		this.setxAcceleration(xAcc);
		this.setyAcceleration(yAcc);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if ( e.getKeyChar() == 'w' || e.getKeyChar() == 'W' ) {
			upPressed = true;
		}
		if ( e.getKeyChar() == 'a' || e.getKeyChar() == 'A' ) {
			downPressed = true;
		}
		if ( e.getKeyChar() == 's' || e.getKeyChar() == 'S' ) {
			leftPressed = true;
		}
		if ( e.getKeyChar() == 'd' || e.getKeyChar() == 'D' ) {
			rightPressed = true;
		}
		if ( e.getKeyChar() == 'q' || e.getKeyChar() == 'Q' ) {
			slightLeftPressed = true;
		}
		if ( e.getKeyChar() == 'e' || e.getKeyChar() == 'E' ) {
			slightRightPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if ( e.getKeyChar() == 'w' || e.getKeyChar() == 'W' ) {
			upPressed = false;
		}
		if ( e.getKeyChar() == 'a' || e.getKeyChar() == 'A' ) {
			downPressed = false;
		}
		if ( e.getKeyChar() == 's' || e.getKeyChar() == 'S' ) {
			leftPressed = false;
		}
		if ( e.getKeyChar() == 'd' || e.getKeyChar() == 'D' ) {
			rightPressed = false;
		}
		if ( e.getKeyChar() == 'q' || e.getKeyChar() == 'Q' ) {
			slightLeftPressed = false;
		}
		if ( e.getKeyChar() == 'e' || e.getKeyChar() == 'E' ) {
			slightRightPressed = false;
		}
	}

}
