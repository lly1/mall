package com.mall.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.concurrent.*;

@SpringBootTest
public class CallableTest {

    private Logger logger = LoggerFactory.getLogger(CallableTest.class);

    private static Random random = new Random();

    private static ExecutorService executorService = Executors.newCachedThreadPool();


    @org.junit.jupiter.api.Test
    public void callableTest() throws Exception{
        //单线程测试
        for(int i=0; i<10; i++) {
            methodWithTimeout();
        }

        System.out.println("----------------------");

        //并发测试
        for(int i=0; i<10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run(){
                    try{
                        methodWithTimeout();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        while(true){
        }
    }

    //future逻辑
    private static void methodWithTimeout() throws Exception{

        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            Thread.sleep(random.nextInt(3000));
            return "ok";
        });

        executorService.submit(futureTask);
        String result = null;
        try {
            result = futureTask.get(1, TimeUnit.SECONDS);
        }catch (TimeoutException e) {
            result = "timeout";
        }
        System.out.println(String.format("the result is: %s", result));
    }

}