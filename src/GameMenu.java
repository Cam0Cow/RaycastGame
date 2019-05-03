import java.util.*;
import java.awt.*;

public class GameMenu {
	
	private HashMap<String, Dimension> options;
	
	public GameMenu(Dimension d) {
		options = new HashMap<String, Dimension>();
		options.put("Resume", new Dimension(100,100));
		options.put("Quit", new Dimension(100,300));
	}
	
	public HashMap<String, Dimension> getOptions() {
		return options;
	}
}