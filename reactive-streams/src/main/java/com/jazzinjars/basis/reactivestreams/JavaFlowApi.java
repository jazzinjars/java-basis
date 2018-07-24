package com.jazzinjars.basis.reactivestreams;

import java.util.List;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

public class JavaFlowApi {

	static class NewsSubscriber implements Subscriber<News> {

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
	}

	public static void main(String[] args) throws Exception {
		try (SubmissionPublisher<News> newsPublisher = new SubmissionPublisher<>()) {

			NewsSubscriber newsSubscriber = new NewsSubscriber();
			newsPublisher.subscribe(newsSubscriber);

			List.of(News.create("Important News"), News.create("Some other news"), News.create("And news, news, news"))
					.forEach(newsPublisher::submit);

			while(newsPublisher.hasSubscribers()) {
				//wait
			}
			System.out.println("no more news subscribers left, closing publisher");
		}
	}
}
