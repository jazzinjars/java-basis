package com.jazzinjars.basis.reactivestreams;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.AsPublisher;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

public class Akka {

	public static void main(String[] args) {

		final ActorSystem actorSystem = ActorSystem.create("sample-system");
		final Materializer materializer = ActorMaterializer.create(actorSystem);

		final Publisher<News> publisher =
				Source.from(List.of(News.create("Important News"), News.create("Some other news"),
						News.create("And news, news, news")))
				.runWith(Sink.asPublisher(AsPublisher.WITH_FANOUT), materializer);

		Subscriber<News> newsSubscriber = new Subscriber<>() {

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
		};

		publisher.subscribe(newsSubscriber);
	}
}
