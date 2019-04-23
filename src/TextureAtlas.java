import java.util.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class TextureAtlas {
	
	// should be loaded in from a file!
	private HashMap<String, Image> map;
	
	private static TextureAtlas ta;
	
	private TextureAtlas() {
		map = new HashMap<String, Image>();
		try {
			map.put("monster", ImageIO.read(new File("..\\textures\\monster.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static TextureAtlas getTextureAtlas() {
		if (ta == null) ta = new TextureAtlas();
		return ta;
	}
	
	public Image getTexture(String name) {
		return map.get("monster");
	}
}
