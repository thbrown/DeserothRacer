package Racer;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;


public abstract class Racer {
	
	private float xPosition, yPosition;
	private float xVelocity, yVelocity;
	private float xAcceleration, yAcceleration;
	private float direction; //In radians
	private RacerState state;
	private BufferedImage racerImage;
	
	static final float MAX_ACCELERATION = 4.0f;
	static final float MAX_DECELERATION = -4.0f;
	
	public void loadRacerImage( String racerImageLocation ) {
		File f = new File(racerImageLocation);

	    //Find a suitable ImageReader
	    Iterator readers = ImageIO.getImageReadersByFormatName("JPEG");
	    ImageReader reader = null;
	    while(readers.hasNext()) {
	        reader = (ImageReader)readers.next();
	        if(reader.canReadRaster()) {
	            break;
	        }
	    }

	    //Stream the image file (the original CMYK image)
	    ImageInputStream input;
		try {
			input = ImageIO.createImageInputStream(f);
			reader.setInput(input); 

	    	//Read the image raster
	    	Raster raster = reader.readRaster(0, null); 

	    	//Create a new RGB image
	    	BufferedImage racerImage = new BufferedImage(raster.getWidth(), raster.getHeight(), 
	    										 BufferedImage.TYPE_4BYTE_ABGR); 
	    
	    	//Fill the new image with the old raster
	    	racerImage.getRaster().setRect(raster);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
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

}
