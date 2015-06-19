import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class MainMenu implements Page {
	
	PageDisplayer displayer = new PageDisplayer();

	public MainMenu(PageDisplayer displayer) {
		this.displayer = new PageDisplayer();
	}
	
	public Page startLoop() {
		init();
		Page nextPage = loop();
		return nextPage;
	}
	
	private void init() {
		// Nothing yet
	}
	
	private Page loop() {
		while(true) {
			displayer.draw(getListOfThingsToDraw());
				
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private List<Drawable> getListOfThingsToDraw() {
		
		List<Drawable> list = new ArrayList<>();
		list.add(new Text("Hello!!", 100, 100));
		list.add(new Text("This", 600, 300));
		list.add(new Text("is a ", 200, 600));
		list.add(new Text("Text graphic demo", 300, 700));
		return list;
		
	}
	
	class Text implements Drawable {
		
		String content;
		int x;
		int y;
		
		Text(String content, int x, int y) {
			this.content = content;
			this.x = x;
			this.y = y;
		}

		@Override
		public void draw(Graphics g) {
			g.drawString(content, x, y);
		}
		
	}

}
