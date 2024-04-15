import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Car {
    private int x, y;
    private Image image;
    private boolean isLeft;
    private boolean passedBridge=false;
    private final int speed = 5;
    private String identifier;
    private int crossingTime;
    @SuppressWarnings("unused")
    private int waitingTime;
    private CarManager instance;
    private boolean movingLeft;


    public Car(int x, int y, String identifier, int crossingTime, int waitingTime, boolean isLeft, CarManager instance) {
        this.x = x;
        this.y = y;
        this.isLeft = isLeft;
        this.identifier = identifier;
        this.crossingTime = crossingTime;
        this.waitingTime = waitingTime;
        this.instance = instance;
        this.movingLeft = isLeft;

        try {
            if (isLeft) {
                image = ImageIO.read(new File("caryellow.png")).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            } else {
                image = ImageIO.read(new File("carpink.png")).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(int crossingTime, int waitingTime) {
        if (isLeft) {
            x += speed;
            if (x > 1200) {
                x = -100;
            }
            if (x >= 350 && x < 360) {
                y += 25; 
            }
            if (x>790 && x<=800){
                y-=25;
            }
            if(isOnBridge() && !passedBridge){
                System.out.println("Carro "+identifier+" passando pela ponte");
                instance.addToLog("Carro "+identifier+" passando pela ponte");
                passedBridge = true;
            }
            movingLeft = true;
            
        } else {
            x -= speed;
            if (x < -100){
                x = 1200;
            } 
            if (x <= 800 && x > 790) {
                y -= 30; 
            }
            if (x<=360 && x>350){
                y+=30;
            }
            if(isOnBridge() && !passedBridge){
                System.out.println("Carro "+identifier+" passando pela ponte");
                instance.addToLog("Carro "+identifier+" passando pela ponte");
                passedBridge = true;
            }
            movingLeft = false;

        }
    }
    public boolean isOnBridge() {
        return x >= 350 && x <= 800;
    }
    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, null);
        }
        g.drawString("Carro " + identifier + " - " + crossingTime + "s", x, y - 10);
    }
}

