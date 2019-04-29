import java.util.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

/**
 * A structure that stores all of the game textures
 */
public class TextureAtlas {
	
	// should be loaded in from a file!
	private HashMap<String, Image> map;
	
	private static final String textureDir = "..\\textures";
	private static final String indexPath = "index.dat";
	
	private static TextureAtlas ta;
	
	/**
	 * Constructs a new texture atlas
	 * All entries are loaded from the index file
	 */
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
	
	/**
	 * Gets a reference to the texture atlas,
	 * instantiating if necessary
	 * @return the texture atlas reference
	 */
	public static TextureAtlas getTextureAtlas() {
		if (ta == null) ta = new TextureAtlas();
		return ta;
	}
	
	/**
	 * Returns the texture corresponding to the given name
	 * @param name the given name
	 * @return the texture corresponding to the given name
	 */
	public Image getTexture(String name) {
		return map.get(name);
	}
}
