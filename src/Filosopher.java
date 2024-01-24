import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Filosopher extends Thread{
    private int id_filosopher;
    private Semaphore leftFork;
    private Semaphore rightFork;
    private List<Integer> buffer;

    public Filosopher(int id_filosopher, Semaphore leftFork, Semaphore rightFork, List<Integer> buffer) {
        this.id_filosopher = id_filosopher;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random rand = new Random(); // random integer
        try {
            while(buffer.size() < 10) {
                think();
                sleep(rand.nextInt(5000)); // before eating think for some time
                leftFork.acquire(); // threads acquires the lock
                boolean gotRightFork = rightFork.tryAcquire(5000, TimeUnit.MILLISECONDS); // try to acquire the rightFork lock, returns a boolean
                if (gotRightFork && buffer.size() < 10) { // critical section
                    eat(); // if the threads has access to the lock it runs this block of code and release its resources
                    sleep(2000); // after acquiring the forks eats for some time
                    rightFork.release();
                    leftFork.release();
                    buffer.add(1);
                } else {
                    leftFork.release(); // if the thread couldn't get the lock since another thread already has the rightFork lock
                    // it releases its leftFork so when a thread release the resource rightFork, the thread that was on queue is able to
                    // acquire or compete for the lock again
                    sleep(rand.nextInt(5000)); // makes the thread sleep for a period of random time, so it doesn't
                    // happen a deadlock or starvation when all the threads try to acquire the Fork simultaneously
                }
            }
            System.out.println("O filosofo " + (this.id_filosopher + 1) + " terminou de comer!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void think() {
        System.out.println("Filosofer " + (this.id_filosopher + 1)+ " think.");
    }

    public void eat() {
        System.out.println("Filosofer " + (this.id_filosopher + 1) + " eats.");
    }

}
