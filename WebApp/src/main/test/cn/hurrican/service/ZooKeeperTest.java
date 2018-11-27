package cn.hurrican.service;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZooKeeperTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("118.89.57.129:2181", 10000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("watchedEvent.getState() = " + watchedEvent.getState());
                System.out.println("watchedEvent.getPath() = " + watchedEvent.getPath());
                latch.countDown();
            }
        });
        ZooKeeper.States state = zooKeeper.getState();
        System.out.println(state);
        latch.await();
        state = zooKeeper.getState();
        System.out.println(state);
    }
}
