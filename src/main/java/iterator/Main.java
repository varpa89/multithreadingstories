package iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author Pavel S Varchenko
 * @since 18.05.2018
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(4);
        values.add(5);
        Thread odd = new Thread(new Worker(values.iterator(), "odd"));
        Thread even = new Thread(new Worker(values.iterator(), "even"));

        odd.start();
        even.start();
        //Thread.currentThread().join();
        //System.out.println(Arrays.toString(Arrays.asList(values)));
    }


    public static class Worker implements Runnable {
        private final Iterator<Integer> values;
        private final String mode;

        Worker(Iterator<Integer> values, String mode) {
            this.values = values;
            this.mode = mode;
        }

        @Override
        public void run() {
            //Iterator<Integer> iterator = values.iterator();
            while (values.hasNext()) {
                Integer next = values.next();
                if (mode.equals("odd") && next % 2 == 0) {
                    values.remove();
                } else if (mode.equals("even") && next % 2 != 0) {
                    values.remove();
                }
            }
            System.out.println("Finish");
        }
    }
}
