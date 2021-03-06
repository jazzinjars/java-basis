package com.jazzinjars.basis.reactivestreams;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import org.reactivestreams.Subscription;

public class RxJava2 {

	public static void main(String[] args) {

		Flowable.just(News.create("Important news"), News.create("Some other news"), News.create("And news, news, news"))
				.subscribe(new FlowableSubscriber<News>() {

					private Subscription subscription;
					private static final int MAX_NEWS = 3;
					private int newsReceived = 0;

					@Override
					public void onSubscribe(Subscription subscription) {
						System.out.printf("New Subscription %s\n", subscription);
						this.subscription = subscription;
						subscription.request(1);
					}

					@Override
					public void onNext(News news) {
						System.out.printf("News received: %s (%s)\n", news.getHeadLine(), news.getDate());
						newsReceived++;
						if (newsReceived >= MAX_NEWS) {
							System.out.printf("%d news received (max: %d), cancelling subscription\n", newsReceived, MAX_NEWS);
							subscription.cancel();
							return;
						}
						subscription.request(1);
					}

					@Override
					public void onError(Throwable throwable) {
						System.err.printf("Error occurred fetching news: %s\n", throwable.getMessage());
						throwable.printStackTrace(System.err);
					}

					@Override
					public void onComplete() {
						System.out.println("Fetching news completed");
					}
				});
	}
}
