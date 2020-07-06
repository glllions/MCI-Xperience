package records;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class StatusImages {

    private Image RED;
    private Image GREEN;

    private static StatusImages instance = null;

    private StatusImages() {
        RED = getImage("red");
        GREEN = getImage("green");
    }

    public static StatusImages getInstance() {
        if (instance == null) {
            instance = new StatusImages();
        }
        return instance;
    }

    public ImageView getRED() {
        return new ImageView(RED);
    }

    public ImageView getGREEN() {
        return new ImageView(GREEN);
    }

    private Image getImage(String name) {
        File file = new File(StatusImages.class.getResource("/resource/" + name + ".png").getPath());
        return new Image(StatusImages.class.getResourceAsStream("/resource/" + name + ".png"));
    }
}
