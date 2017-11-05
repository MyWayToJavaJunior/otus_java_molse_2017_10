package ru.otus.hw;

/**
 *
 */
public class Main {
    private static final int THREAD_SLEEP = 10000;

    public static void main(final String... args) throws InterruptedException {

      GcMonitor.startGCMonitor();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(THREAD_SLEEP);
                    GcMonitor.printGcGenDuration();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();


        Leaker lk = new Leaker();
        lk.leak();
    }
}
