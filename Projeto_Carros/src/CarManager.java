import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CarManager {
    private Set<String> usedIds = new HashSet<>();
    private RoadPanel roadPanel;
    public JTextArea logTextArea;
    private ArrayList<Car> leftCars;
    private ArrayList<Car> rightCars;
    private final int maxCars = 10;
    public final int leftCarX = 2;
    public final int rightCarX = 1080;
    public final int leftCarY = 300;
    public final int rightCarY = 410;

    
    


    public CarManager(RoadPanel roadPanel, ArrayList<Car> leftCars, ArrayList<Car> rightCars) {
        this.roadPanel = roadPanel;
        this.leftCars = leftCars;
        this.rightCars = rightCars;
        
        
    }
    public void setLogTextArea(JTextArea logTextArea){
        this.logTextArea = logTextArea;
    }
    public void addToLog(String message){
        logTextArea.append(message + "\n");
    }

    public void createCar(boolean left, int id, int crossingTime, int waitingTime) {
        String idLabel = left ? id + "A" : id + "B";
        String sideString = left ? "esquerda" : "direita";

        if (!usedIds.contains(idLabel)){
            if (left) {
                if (leftCars.size() < maxCars) {
                    Car newCar = new Car(leftCarX, leftCarY, idLabel, crossingTime, waitingTime, true, this);
                    leftCars.add(newCar);
                    addToLog("Carro "+idLabel+" criado a "+sideString);
                    usedIds.add(idLabel);
                    roadPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Limite de carros a esquerda atingido!");
                }
            } else {
                if (rightCars.size() < maxCars) {
                    Car newCar = new Car(rightCarX, rightCarY, idLabel, crossingTime, waitingTime, false, this);
                    rightCars.add(newCar);
                    addToLog("Carro "+idLabel+" criado a "+sideString);
                    usedIds.add(idLabel);
                    roadPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Limite de carros a direita atingido!");
                }
            }
            
        } else{
            JOptionPane.showMessageDialog(null, "Id "+id+" indisponivel.");
        }
    }
    
}



