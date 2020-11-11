package Week_04.q42;

public class WhileWaitMethod {

    private volatile int result = -1;
    
    public static void main(String[] args) throws InterruptedException  {
        long start=System.currentTimeMillis();
        WhileWaitMethod vm = new WhileWaitMethod();
        // 在这里创建一个线程或线程池，
        Thread thread = new Thread(new Runnable(){
            public void run()
            {
                vm.result = sum(); //这是得到的返回值
                System.out.println("线程内计算结果为："+ vm.result);
            }
        });

        // 异步执行 下面方法
        thread.start();
        // 确保  拿到result 并输出
        while(vm.result == -1) {
            Thread.sleep(1);
        }
        System.out.println("异步计算结果为："+ vm.result);
         
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