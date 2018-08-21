package com.jazzinjars.basis.concurrency;

public class MyRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println("Executing thread: " + Thread.currentThread().getName());

		long id = Thread.currentThread().getId();
		String name = Thread.currentThread().getName();
		int priority = Thread.currentThread().getPriority();
		Thread.State state = Thread.currentThread().getState();
		String threadGroupName = Thread.currentThread().getThreadGroup().getName();

		System.out.println("id="+id+"; name="+name+"; priority="+priority+"; state="+state+"; threadGroupName="+threadGroupName);
	}

	public static void main(String[] args) {
		Thread myThread = new Thread(new MyRunnable(), "myRunnable");
		myThread.start();
	}
}
