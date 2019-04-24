import java.util.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class TextureAtlas {
	
	// should be loaded in from a file!
	private HashMap<String, Image> map;
	
	private static final String textureDir = "..\\textures";
	private static final String indexPath = "index.dat";
	
	private static TextureAtlas ta;
	
	private TextureAtlas() {
		map = new HashMap<String, Image>();
		try {
			File index = new File(textureDir, indexPath);
			Scanner sc = new Scanner(index);
			while (sc.hasNextLine()) {
				map.put(sc.next().trim(), ImageIO.read(new File(textureDir, sc.next().trim())));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static TextureAtlas getTextureAtlas() {
		if (ta == null) ta = new TextureAtlas();
		return ta;
	}
	
	public Image getTexture(String name) {
		return map.get(name);
	}
}
