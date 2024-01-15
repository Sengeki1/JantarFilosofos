import java.util.concurrent.Semaphore;

public class Filosofer extends Thread{
    private int id_filosofer;
    private Semaphore leftFork;
    private Semaphore rightFork;

    public Filosofer(int id_filosofer, Semaphore leftFork, Semaphore rightFork) {
        this.id_filosofer = id_filosofer;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        try {
            while(true) {
                think();
                leftFork.acquire();
                rightFork.acquire();
                eat();
                leftFork.release();
                rightFork.release();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void think() {
        System.out.println("Filosofer " + this.id_filosofer + " think.");
    }

    public void eat() {
        System.out.println("Filosofer " + this.id_filosofer + " eats.");
    }
}
