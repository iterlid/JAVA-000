package Week_04.q42;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchMethod {

    private volatile int result = -1;
    
    public static void main(String[] args) throws InterruptedException  {
        long start=System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        CountDownLatchMethod clm = new CountDownLatchMethod();
        // 在这里创建一个线程或线程池，
        Thread thread = new Thread(new Runnable(){
            public void run()
            {
                clm.result = sum(); //这是得到的返回值
                countDownLatch.countDown();
                System.out.println("线程内计算结果为："+ clm.result);
            }
        });

        // 异步执行 下面方法
        thread.start();
        // 确保  拿到result 并输出
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("异步计算结果为："+ clm.result);
         
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        
        // 然后退出main线程
    }
    
    private static int sum() {
        return fibo(36);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}