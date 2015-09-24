package org.learningconcurrency.ch2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Sync {
    private final int THREAD_NUM = 50;
    private final int COUNT_NUM = 1000;

    private static Sync instance = new Sync();


    private int mCounter=0;
    private volatile int mVolatileCounter=0;
    private Object mSyncObj = new Object();
    private AtomicInteger mAtomCounter = new AtomicInteger(0);

    public static void main(String[] args) {
        instance._run( "dummy", instance._no_sync );
        instance.mCounter = 0;
        instance._run( "_no_sync", instance._no_sync );
        instance.mCounter = 0;
        instance._run( "_synchronized", instance._synchronized );
        instance._run( "_volatile", instance._volatile );
        instance._run( "_atom", instance._atom );
        instance.mAtomCounter.set(-1);
        instance._run( "_lock_free", instance._lock_free );
    }


    private Runnable _no_sync   =           
        new Runnable(){
            public void run() {
                for (int i = 0; i < COUNT_NUM; i++) {
                    System.out.print( mCounter++ + ",");
                }
            }   
        };

    private Runnable _synchronized =
        new Runnable(){
            public void run() {
                for (int i = 0; i < COUNT_NUM; i++) {
                    synchronized(mSyncObj){
                        System.out.print( mCounter++ + ",");
                    }
                }
            }
        };

    private Runnable _volatile = 
        new Runnable(){
            public void run() {
                for (int i = 0; i < COUNT_NUM; i++) {
                    System.out.print( mVolatileCounter++ + ",");
                }
            }                       
        };

    private Runnable _atom = 
            new Runnable(){
        public void run() {
            for (int i = 0; i < COUNT_NUM; i++) {
                System.out.print( mAtomCounter.getAndIncrement() + ",");    
            }
        }                       
    };

    private Runnable _lock_free = 
            new Runnable(){
        public void run() {
            for (int i = 0; i < COUNT_NUM; i++) {
                int n;
                do{
                    n = mAtomCounter.get();
                }
                while(!mAtomCounter.compareAndSet(n, n+1));
                        // print前に他スレッドからcompareAndSetが呼ばれ、出力がずれるので修正
                // System.out.print( mAtomCounter.get() + ",");                 
                System.out.print( n+1 + ",");                   

            }
        }                       
    };


    public void _run( String sMsg, Runnable runnable){

        System.out.println( "[" + sMsg + "]  Start!!");     

        long start = System.currentTimeMillis();

        ArrayList<Thread>   thread = new ArrayList<Thread>();
        for( int i=0; i<THREAD_NUM; i++ ){
            Thread tt = new Thread( runnable );
            thread.add(tt);
            tt.start();
        }

        for( Thread t : thread ){
            try {
                t.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println("\n" + sMsg + ": " + (System.currentTimeMillis() - start) + "ms");       
    }
}
