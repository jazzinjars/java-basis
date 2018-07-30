package com.jazzinjars.basis.reactivestreams;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicInteger;

public class EndSubscriber<T> implements Subscriber<T> {

	private AtomicInteger howMuchMessageConsume;
	private Subscription subscription;
	public List<T> consumedElements = new LinkedList<T>();

	public EndSubscriber(Integer howMuchMessagesConsume) {
		this.howMuchMessageConsume = new AtomicInteger(howMuchMessagesConsume);
	}

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(T item) {
		howMuchMessageConsume.decrementAndGet();
		System.out.println("Got: " + item);
		consumedElements.add(item);
		if (howMuchMessageConsume.get() > 0) {
			subscription.request(1);
		}
	}

	@Override
	public void onError(Throwable throwable) {
		throwable.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("Done");
	}
}
