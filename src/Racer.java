
public abstract class Racer {
	
	private float xPosition, yPosition;
	private float xVelocity, yVelocity;
	private float xAcceleration, yAcceleration;
	private RacerState state;
	
	public static void main (String[] args) {
		System.out.println("Booom racing game!!!");
	}
	
	public abstract void gatherInputs( );

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

}
