package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private static final long MS_IN_SEC = 1000;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while (true) {
                long start = System.currentTimeMillis();
                bytesRead = in.read(dataBuffer, 0, 1024);
                if (bytesRead == -1) {
                    break;
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long finish = System.currentTimeMillis();
                long speedTime = MS_IN_SEC * bytesRead / speed;
                long timeSleep = speedTime - (finish - start);
                if (timeSleep > 0) {
                    Thread.sleep(timeSleep);
                }
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
        int needSize = 2;
        if (args.length < needSize) {
            throw new IllegalArgumentException("Необходимо передать не менее двух параметров");
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
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
