package Racer;

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
		this.setTurningRadius(100.0f);
		this.setDrag(0.05f);
	}
	
	private void CalculateBaseAcceleration() {
		float xAcc = this.getxAcceleration();
		float yAcc = this.getyAcceleration();
		float direction = this.getDirection();
		
		if ( upPressed ) {
			xAcc = (float)Math.cos(direction) * this.MAX_ACCELERATION * this.getStateSpeedEffect();
			yAcc = (float)Math.sin(direction) * this.MAX_ACCELERATION * this.getStateSpeedEffect();
		}
		if ( downPressed ) {
			xAcc = (float)Math.cos(direction) * this.MAX_DECELERATION * this.getStateSpeedEffect();
			yAcc = (float)Math.sin(direction) * this.MAX_DECELERATION * this.getStateSpeedEffect();
		}
		if ( !upPressed && !downPressed ) {
			xAcc = 0.0f;
			yAcc = 0.0f;
			/*if( this.getxVelocity() > 0 )
			{
				xAcc = -0.1f;
			}
			else if (this.getxVelocity() < 0 )
			{
				xAcc = 0.1f;
			}
			if( this.getyVelocity() > 0 )
			{
				yAcc = -0.1f;
			}
			else if ( this.getyVelocity() < 0 )
			{
				yAcc = 0.1f;
			}*/
		}
		this.setxAcceleration(xAcc);
		this.setyAcceleration(yAcc);
	}
	
	private void CalculateAndSetNewDirection() {
		float direction = this.getDirection();
		if ( leftPressed ) {
			direction -= 0.1f;
		}
		if ( rightPressed ) {
			direction += 0.1f;
		}
		this.setDirection(direction);
	}
	
	private void ModifyAccelerationVectorsForTurning() {
		float xAcc = this.getxAcceleration();
		float yAcc = this.getyAcceleration();
		float direction = this.getDirection();
		
		float turningAccelerationMagnitude = ( this.getxVelocity() * this.getxVelocity() + this.getyVelocity() * this.getyVelocity() ) / this.getTurningRadius();
		
		System.out.println("Turning Acceleration: " + turningAccelerationMagnitude);
		
		if ( leftPressed ) {
			xAcc = (float) (turningAccelerationMagnitude * Math.cos(direction - Math.PI/4));
			yAcc = (float) (turningAccelerationMagnitude * Math.sin(direction - Math.PI/4));
		}
		if ( rightPressed ) {
			xAcc = (float) (turningAccelerationMagnitude * Math.cos(direction + Math.PI/4));
			yAcc = (float) (turningAccelerationMagnitude * Math.sin(direction + Math.PI/4));
		}
		
		System.out.println("After recal: " + Math.sqrt(xAcc*xAcc + yAcc*yAcc));
		
		this.setxAcceleration(xAcc);
		this.setyAcceleration(yAcc);
	}
	
	@Override
	public void setupAccelerationVectors() {
		
		CalculateAndSetNewDirection();
		
		CalculateBaseAcceleration();
		
		ModifyAccelerationVectorsForTurning();
		
		/*if ( slightRightPressed ) {
			//TODO: Add turning adjustments to the Acceleration Vector.
			direction += 0.05f;
		}
		if ( slightLeftPressed ) {
			//TODO: Add turning adjustments to the Acceleration Vector.
			direction -= 0.05f;
		}*/
		/*if ( upPressed )
		{
			xAcc = (float)Math.cos(direction) * this.MAX_ACCELERATION * this.getStateSpeedEffect() - this.getDrag() * this.getxVelocity() * (float)Math.cos(direction);
			yAcc = (float)Math.sin(direction) * this.MAX_ACCELERATION * this.getStateSpeedEffect() - this.getDrag() * this.getyVelocity() * (float)Math.sin(direction);
		}
		if ( downPressed ) {
			xAcc = (float)Math.cos(direction) * this.MAX_DECELERATION * this.getStateSpeedEffect() + this.getDrag() * this.getxVelocity() * (float)Math.cos(direction);
			yAcc = (float)Math.sin(direction) * this.MAX_DECELERATION * this.getStateSpeedEffect() + this.getDrag() * this.getyVelocity() * (float)Math.sin(direction);
		}
		if ( !upPressed && !downPressed ) {
			xAcc = 0.0f;
			yAcc = 0.0f;
			if( this.getxVelocity() > 0 )
			{
				xAcc = -0.1f;
			}
			else if (this.getxVelocity() < 0 )
			{
				xAcc = 0.1f;
			}
			if( this.getyVelocity() > 0 )
			{
				//TODO: Need to add some slow down
				yAcc = -0.1f;
			}
			else if ( this.getyVelocity() < 0 )
			{
				yAcc = 0.1f;
			}
		}*/
		
		/*float turningAccelerationMagnitude = this.getxVelocity() * this.getxVelocity() + this.getyVelocity() * this.getyVelocity() / this.getTurningRadius();
		if ( leftPressed ) {
			xAcc -= turningAccelerationMagnitude * Math.cos(direction + Math.PI/4);
			yAcc -= turningAccelerationMagnitude * Math.sin(direction + Math.PI/4);
		}
		if ( rightPressed ) {
			xAcc += turningAccelerationMagnitude * Math.cos(direction + Math.PI/4);
			yAcc += turningAccelerationMagnitude * Math.sin(direction + Math.PI/4);
		}
		
		System.out.println("xAcc: " + xAcc);
		System.out.println("yAcc: " + yAcc);
		
		this.setDirection(direction);
		this.setxAcceleration(xAcc);
		this.setyAcceleration(yAcc);*/
		
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
