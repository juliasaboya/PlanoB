import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.*;


public class Interface extends JFrame {
    private RoadPanel roadPanel;
    public JTextArea logTextArea;
    public ArrayList<Car> leftCars = new ArrayList<>();
    public ArrayList<Car> rightCars = new ArrayList<>();
    private Threads threads;
    private CarManager manage;
    public final int maxCars = 10;
    final int sizeX = 1200, sizeY = 1000;
    int i;

    public Semaphore bridgeSemaphore = new Semaphore(1);
    public Semaphore leftSemaphore = new Semaphore(1);
    public Semaphore rightSemaphore = new Semaphore(1);
    
    public ArrayList<Car> getLeftCars() {
        return leftCars;
    }

    // Método para obter a lista de carros da direita
    public ArrayList<Car> getRightCars() {
        return rightCars;
    }

    public Interface() {
        setTitle("Car Simulation");
        setSize(sizeX, sizeY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        roadPanel = new RoadPanel(leftCars, rightCars);
        manage = new CarManager(roadPanel, leftCars, rightCars);

        JPanel buttonPanel = new JPanel();
        JButton leftButton = new JButton("Criar Carro Esquerda");
        JButton rightButton = new JButton("Criar Carro Direita");

        JTextField idField = new JTextField(5);
        JTextField crossingTimeField = new JTextField(5);
        JTextField waitingTimeField = new JTextField(5);


        logTextArea = new JTextArea(8,20);
        logTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        
        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                int crossingTime = Integer.parseInt(crossingTimeField.getText());
                int waitingTime = Integer.parseInt(waitingTimeField.getText());
                manage.setLogTextArea(logTextArea);
                manage.createCar(true, id, crossingTime, waitingTime);  

                threads.setCrossingTime(crossingTime);
                threads.setWaitingTime(waitingTime);
            }
        }
        );
        
        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                int crossingTime = Integer.parseInt(crossingTimeField.getText());
                int waitingTime = Integer.parseInt(waitingTimeField.getText());
                manage.setLogTextArea(logTextArea);
                manage.createCar(false, id, crossingTime, waitingTime);

                threads.setCrossingTime(crossingTime);
                threads.setWaitingTime(waitingTime);

            }
        });

        buttonPanel.add(leftButton);
        buttonPanel.add(rightButton);

        buttonPanel.add(new JLabel("No Identificador:"));
        buttonPanel.add(idField);
        buttonPanel.add(new JLabel("Tempo Travessia (s):"));
        buttonPanel.add(crossingTimeField);
        buttonPanel.add(new JLabel("Tempo Espera (s):"));
        buttonPanel.add(waitingTimeField);

        buttonPanel.add(scrollPane);

        
        add(buttonPanel, BorderLayout.SOUTH);
        add(roadPanel);

        Threads threads = new Threads();
        threads.setLeftCars(leftCars);
        threads.setRightCars(rightCars);

        Threads.ThreadA threadA = threads.new ThreadA(threads, roadPanel, bridgeSemaphore, leftSemaphore);
        Threads.ThreadB threadB = threads.new ThreadB(threads, roadPanel, bridgeSemaphore, rightSemaphore); // Passando a instância de Threads
        threadA.start();
        threadB.start();
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Interface carSimulation = new Interface();
            carSimulation.setVisible(true);
            
        });
    }
    
}

