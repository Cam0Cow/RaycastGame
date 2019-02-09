

public class Main {
    public static void main(String[] args) {
        System.out.println("Testing LevelMap");
        LevelMap lm = new LevelMap("../maps/Map1.txt");
        System.out.printf("%s (%d x %d)%n", lm.getName(), lm.getWidth(), lm.getHeight());
    }
}