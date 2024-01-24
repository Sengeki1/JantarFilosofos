import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner num = new Scanner(System.in);
        System.out.print("Quantos Filósofos: ");
        int filosophers = Integer.parseInt(num.nextLine());

        List<Integer> buffer = new ArrayList<>();

        Semaphore leftFork = new Semaphore(1);
        Semaphore rightFork = new Semaphore(1);

        for (int i = 0; i < filosophers; i++) {
            Thread thread = new Filosofer(i, leftFork, rightFork, buffer);
            thread.start();
        }
    }
}