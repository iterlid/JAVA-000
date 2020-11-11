package Week_04.q42;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskMethod {
    
    public static void main(String[] args) throws InterruptedException , ExecutionException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>(){
            public Integer call() throws Exception
            {
                Integer result = sum(); //这是得到的返回值
                System.out.println("线程内计算结果为："+ result);
                return result;
            }
        });
        new Thread(task).start();
        // 异步执行 下面方法

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+ task.get());
         
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