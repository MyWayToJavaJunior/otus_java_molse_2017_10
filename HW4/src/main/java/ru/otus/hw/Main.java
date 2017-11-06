package ru.otus.hw;

public class Main {
    private static final int THREAD_SLEEP = 60000;

    public static void main(final String... args) throws InterruptedException {

      Monitor.startGCMonitor();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(THREAD_SLEEP);
                    Monitor.printGcGenDuration();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();


        Leaker lk = new Leaker();
        lk.leak();
    }
}
