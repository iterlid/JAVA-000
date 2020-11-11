# 思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程？

## 使用 volatile 以及使用while卡住进程 来获取返回值。 
WhileWaitMethod.java   
该方法会见到cpu飙高.

## 等待线程结束. 来获取返回值.
ThreadJoinMethod.java
正常的等待.

## 使用FutureTaskMethod来执行
FutureTaskMethod.java

## 使用CountDownLatch实现
CountDownLatchMethod.java

## 使用CyclicBarrier实现
CyclicBarrierMethod.java