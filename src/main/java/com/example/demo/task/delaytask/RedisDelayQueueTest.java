package com.example.demo.task.delaytask;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houan on 18/5/8.
 */
public class RedisDelayQueueTest {

    @Test
    public void testRedisDistributedDelayQueue() {
        RedisConcurrentDelayQueue delayQueue = DelayQueueFactory.getRedisConcurrentDelayQueue("testdisqueue", 1000, true);
//        RedisSynDelayQueue delayQueue = DelayQueueFactory.getRedisSynDelayQueue("testdisqueue", 1000, false, "localhost", 6379);
        for (int i = 0; i < 100000; i++) {
            delayQueue.push(i + "");
        }

        List<Thread> threads = new ArrayList<>();
        //十个线程
        for (int i = 0; i < 10; i++) {
            final int ii = i;
            Thread t = new Thread(() -> {
                RedisConcurrentDelayQueue delayQueue1 = DelayQueueFactory.getRedisConcurrentDelayQueue("testdisqueue", 1000, false);
                //RedisSynDelayQueue delayQueue1 = DelayQueueFactory.getRedisSynDelayQueue("testdisqueue", 1000, false, "localhost", 6379);
                long start = System.currentTimeMillis();
                for (int j = 0; j < 10000; j++) {
                    DelayMessage message = delayQueue1.pop();
                    System.out.println(ii + ":" + message.getMessage());
                    delayQueue1.ack(message.getTmpKey());
                }
                long timeUsed = System.currentTimeMillis() - start;
                System.out.println("10 threads consume 10000, use " + timeUsed + "ms");
            });
            threads.add(t);
        }

        for (Thread t : threads) {
            t.start();
        }

        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}