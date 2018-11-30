package cn.hurrican.zookeeper;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class ZooKeeperConnectTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        String connectString = "118.89.57.129:2181";
        ZooKeeper zooKeeper = new ZooKeeper(connectString, 10000, watchedEvent -> {
            System.out.println("watchedEvent.getState() = " + watchedEvent.getState());
            System.out.println("watchedEvent = " + watchedEvent);
            latch.countDown();
        });


        ZooKeeper.States state = zooKeeper.getState();
        System.out.println(state);
        latch.await();
        state = zooKeeper.getState();
        System.out.println(state);
        long sessionId = zooKeeper.getSessionId();
        byte[] sessionPasswd = zooKeeper.getSessionPasswd();
        System.out.println("sessionId = " + sessionId);

        InputStreamReader reader = new InputStreamReader(System.in);
        Scanner scanner = new Scanner(reader);
        String next = scanner.next();
        System.out.println("next = " + next);
        zooKeeper = new ZooKeeper(connectString, 10000, watchedEvent -> {
            System.out.println("watchedEvent.getType() = " + watchedEvent.getType());
        }, sessionId, sessionPasswd);
        next = scanner.next();
        System.out.println(next);
        reader.close();
    }
}
