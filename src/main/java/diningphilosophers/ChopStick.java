package diningphilosophers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChopStick {
    private final Lock lock = new ReentrantLock();
    private final Condition conditionA = lock.newCondition();

    private static int counter = 0;
    private boolean iAmFree = true;
    private final int myNumber;

    public ChopStick() {
        myNumber = ++counter;
    }

     public boolean tryTake(int delay) throws InterruptedException {
        lock.lock();
       try { while (!iAmFree) {
           conditionA.await();
                return false; // Echec
        }
        iAmFree = false;
        // Pas utile de faire notifyAll ici, personne n'attend qu'elle soit occupée
        return true; // Succès
    }
    finally {lock.unlock();}}


      public void release() {
        lock.lock();
        try {
        iAmFree = true;
        conditionA.signalAll();
        System.out.println("Stick " + myNumber + " Released");
    }finally {
            lock.unlock();
        }
      }


    @Override
    public String toString() {
        return "Stick#" + myNumber;
    }
}