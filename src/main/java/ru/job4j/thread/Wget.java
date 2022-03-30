package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final String file;
    private final int speed;
    private static final long MS_IN_SEC = 1000;

    public Wget(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long finish = System.currentTimeMillis();
                long speedTime = (long) Math.ceil((double) MS_IN_SEC * bytesRead / speed);
                long timeSleep = speedTime - (finish - start);
                if (timeSleep > 0) {
                    Thread.sleep(timeSleep);
                }
                start = System.currentTimeMillis();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void validate(String[] args) {
        int needSize = 3;
        if (args.length < needSize) {
            throw new IllegalArgumentException("Необходимо передать не менее трех параметров");
        }
        try {
            Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Необходимо передать вторым параметром число");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String file = args[2];
        Thread wget = new Thread(new Wget(url, speed, file));
        wget.start();
        wget.join();
    }
}
