package Week_04.q42;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierMethod {

    private volatile int result = -1;
    
    public static void main(String[] args) throws InterruptedException  {
        long start=System.currentTimeMillis();
        CyclicBarrier cyclicBarrier  = new CyclicBarrier(2);
        // 在这里创建一个线程或线程池，
        CyclicBarrierMethod yaMethod = new CyclicBarrierMethod();
        Thread thread = new Thread(new Runnable(){
            public void run()
            {
                yaMethod.result = sum(); //这是得到的返回值
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
        
                System.out.println("线程内计算结果为："+ yaMethod.result);
            }
        });

        // 异步执行 下面方法
        thread.start();
        // 确保  拿到result 并输出
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("异步计算结果为："+ yaMethod.result);
         
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