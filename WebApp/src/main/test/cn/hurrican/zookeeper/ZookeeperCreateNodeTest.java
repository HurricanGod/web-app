package cn.hurrican.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class ZookeeperCreateNodeTest {

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        String connectString = "118.89.57.129:2181";
        ZooKeeper zooKeeper = new ZooKeeper(connectString, 10000, watchedEvent -> {
            System.out.println("watchedEvent.getState() = " + watchedEvent.getState());
            System.out.println("watchedEvent = " + watchedEvent);
            latch.countDown();
        });

        String data = "118.89.59.66:3306";
        String path = "/hello";
        String subpath = path + "/hurrican";
        String s = zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("s = " + s);
        zooKeeper.create(subpath, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
                (rc, path1, ctx, name) -> {
                    System.out.println("rc = " + rc);
                    System.out.println("ctx = " + ctx);
                    System.out.println("name = " + name);
                    System.out.println("path1 = " + path1);
                }, "callback param");

        Thread.sleep(200000);
    }
}
