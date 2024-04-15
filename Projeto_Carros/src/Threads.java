import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Threads {
    public ArrayList<Car> leftCars;
    private ArrayList<Car> rightCars;
    private int crossingTime;
    private int waitingTime;

    public boolean isBridgeOccupiedLeft() {
        boolean leftOccupied = false;
        for (Car car : leftCars) {
            if (car.isOnBridge()) {
                leftOccupied = true;
            }
        }
        return leftOccupied;
    }

    public boolean isBridgeOccupiedRight() {
        boolean rightOccupied = false;
        for (Car car : rightCars) {
            if (car.isOnBridge()) {
                rightOccupied = true;
            }
        }
        return rightOccupied;
    }

    public void setCrossingTime(int crossingTime) {
        this.crossingTime = crossingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
    public void setLeftCars(ArrayList<Car> leftCars) {
        this.leftCars = leftCars;
    }
    
    public void setRightCars(ArrayList<Car> rightCars) {
        this.rightCars = rightCars;
    }

    public class ThreadA extends Thread {
        private RoadPanel roadPanel;
        private Semaphore leftSemaphore;
        private Threads threads;

        public ThreadA(Threads threads, RoadPanel roadPanel, Semaphore bridgeSemaphore, Semaphore leftSemaphore) {
            this.threads = threads;
            this.roadPanel = roadPanel;
            this.leftSemaphore = leftSemaphore;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    if (!threads.isBridgeOccupiedRight()) {
                        leftSemaphore.acquire();
                        for (Car car : threads.leftCars) {
                            car.move(threads.crossingTime, threads.waitingTime);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    leftSemaphore.release();
                }

                roadPanel.repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class ThreadB extends Thread {
        private RoadPanel roadPanel;
        private Semaphore rightSemaphore;
        private Threads threads;

        public ThreadB(Threads threads, RoadPanel roadPanel, Semaphore bridgeSemaphore, Semaphore rightSemaphore) {
            this.threads = threads;
            this.roadPanel = roadPanel;
            this.rightSemaphore = rightSemaphore;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    if (!threads.isBridgeOccupiedLeft()) {
                        rightSemaphore.acquire();
                        for (Car car : threads.rightCars) {
                            car.move(threads.crossingTime, threads.waitingTime);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    rightSemaphore.release();
                }

                roadPanel.repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
