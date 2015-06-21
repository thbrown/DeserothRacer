package Screens;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import Racer.*;

public class Race implements KeyListener, Page {
	
	MainFrame displayer;
	public List<Racer> racers = new ArrayList<Racer>();
	
	public Race(MainFrame displayer) {
		this.displayer = displayer;
		this.displayer.addKeyListener(this);
	}
	
	private void init( ) {
		racers.add(new HumanRacer(0, "src/Images/RaceCar.jpg"));
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
					System.out.println("W pressed.");
					((HumanRacer) racer).setUpPressed(true);
				}
				if ( e.getKeyChar() == 's' || e.getKeyChar() == 'S' ) {
					System.out.println("S pressed.");
					((HumanRacer) racer).setDownPressed(true);
				}
				if ( e.getKeyChar() == 'a' || e.getKeyChar() == 'A' ) {
					System.out.println("A pressed.");
					((HumanRacer) racer).setLeftPressed(true);
				}
				if ( e.getKeyChar() == 'd' || e.getKeyChar() == 'D' ) {
					System.out.println("D pressed.");
					((HumanRacer) racer).setRightPressed(true);
				}
				if ( e.getKeyChar() == 'q' || e.getKeyChar() == 'Q' ) {
					System.out.println("Q pressed.");
					((HumanRacer) racer).setSlightLeftPressed(true);
				}
				if ( e.getKeyChar() == 'e' || e.getKeyChar() == 'E' ) {
					System.out.println("E pressed.");
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
					System.out.println("W released.");
					((HumanRacer) racer).setUpPressed(false);
				}
				if ( e.getKeyChar() == 's' || e.getKeyChar() == 'S' ) {
					System.out.println("S released.");
					((HumanRacer) racer).setDownPressed(false);
				}
				if ( e.getKeyChar() == 'a' || e.getKeyChar() == 'A' ) {
					System.out.println("A released.");
					((HumanRacer) racer).setLeftPressed(false);
				}
				if ( e.getKeyChar() == 'd' || e.getKeyChar() == 'D' ) {
					System.out.println("D released.");
					((HumanRacer) racer).setRightPressed(false);
				}
				if ( e.getKeyChar() == 'q' || e.getKeyChar() == 'Q' ) {
					System.out.println("Q released.");
					((HumanRacer) racer).setSlightLeftPressed(false);
				}
				if ( e.getKeyChar() == 'e' || e.getKeyChar() == 'E' ) {
					System.out.println("E released.");
					((HumanRacer) racer).setSlightRightPressed(false);
				}
			}
		}
	}	

}
