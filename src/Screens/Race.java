package Screens;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import Racer.*;
import Screens.MainMenu.Text;

public class Race implements KeyListener, Page {
	
	MainFrame displayer = new MainFrame();
	public List<Racer> racers = new ArrayList<Racer>();
	
	public Race(MainFrame displayer) {
		this.displayer = new MainFrame();
		this.displayer.addKeyListener(this);
	}
	
	private void init( ) {
		racers.add(new HumanRacer(0, "C:/Users/Boys/Documents/GitHub/DeserothRacer/src/Images/RaceCar.jpg"));
	}
	
	private void calculateRacerStuffs() {
		for(Racer racer : racers)
		{
			racer.setupAccelerationVectors();
			racer.CalculateVelocity();
			racer.CalculatePosition();
		}
	}
	
	private Page loop() {		
		while(true) {
			calculateRacerStuffs();
			//displayer.draw(tiles);
			displayer.draw(getListOfThingsToDraw());
			
			try {
			Thread.sleep(50);
			} catch (InterruptedException e) {
			e.printStackTrace();
			}
		}
	}
	
	private List<Drawable> getListOfThingsToDraw() {
		//TODO: This should not be needed. Find a workaround.
		List<Drawable> list = new ArrayList<>();
		for (Racer racer : racers) {
			list.add(racer);
		}
		return list;
	}
	
	@Override
	public Page executePage() {
		init();
		Page nextPage = loop();
		return nextPage;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (Racer racer : racers) {
			if(racer.getClass() == HumanRacer.class) {
				if ( e.getKeyChar() == 'w' || e.getKeyChar() == 'W' ) {
					((HumanRacer) racer).setUpPressed(true);
				}
				if ( e.getKeyChar() == 's' || e.getKeyChar() == 'S' ) {
					((HumanRacer) racer).setDownPressed(true);
				}
				if ( e.getKeyChar() == 'a' || e.getKeyChar() == 'A' ) {
					((HumanRacer) racer).setLeftPressed(true);
				}
				if ( e.getKeyChar() == 'd' || e.getKeyChar() == 'D' ) {
					((HumanRacer) racer).setRightPressed(true);
				}
				if ( e.getKeyChar() == 'q' || e.getKeyChar() == 'Q' ) {
					((HumanRacer) racer).setSlightLeftPressed(true);
				}
				if ( e.getKeyChar() == 'e' || e.getKeyChar() == 'E' ) {
					((HumanRacer) racer).setSlightRightPressed(true);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for (Racer racer : racers) {
			if(racer.getClass() == HumanRacer.class) {
				if ( e.getKeyChar() == 'w' || e.getKeyChar() == 'W' ) {
					((HumanRacer) racer).setUpPressed(false);
				}
				if ( e.getKeyChar() == 'a' || e.getKeyChar() == 'A' ) {
					((HumanRacer) racer).setDownPressed(false);
				}
				if ( e.getKeyChar() == 's' || e.getKeyChar() == 'S' ) {
					((HumanRacer) racer).setLeftPressed(false);
				}
				if ( e.getKeyChar() == 'd' || e.getKeyChar() == 'D' ) {
					((HumanRacer) racer).setRightPressed(false);
				}
				if ( e.getKeyChar() == 'q' || e.getKeyChar() == 'Q' ) {
					((HumanRacer) racer).setSlightLeftPressed(false);
				}
				if ( e.getKeyChar() == 'e' || e.getKeyChar() == 'E' ) {
					((HumanRacer) racer).setSlightRightPressed(false);
				}
			}
		}
	}	

}
