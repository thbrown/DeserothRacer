import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class MainMenu implements Page {
	
	MainFrame mainframe;
	List<Drawable> toDraw;

	public MainMenu(MainFrame mainframe) {
		this.mainframe = mainframe;
	}
	
	public Page executePage() {
		init();
		Page nextPage = loop();
		return nextPage;
	}
	
	private void init() {
		toDraw = new ArrayList<>();
		toDraw.add(new Text("Deseroth Racer", 100, 100));
	}
	
	private Page loop() {
		while(true) {
			mainframe.draw(getListOfThingsToDraw());
				
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private List<Drawable> getListOfThingsToDraw() {
		return toDraw;
	}
	
	class Text implements Drawable {
		
		String content;
		Font font = new Font(Font.MONOSPACED, Font.ITALIC, 100);
		int x;
		int y;
		
		Text(String content, int x, int y) {
			this.content = content;
			this.x = x;
			this.y = y;
		}

		@Override
		public void draw(Graphics g) {
			Font old = g.getFont();
			g.setFont(font);
			g.drawString(content, x, y);
			g.setFont(old);
		}
		
	}

}
