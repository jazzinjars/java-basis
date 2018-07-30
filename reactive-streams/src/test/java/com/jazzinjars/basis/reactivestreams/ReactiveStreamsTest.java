package com.jazzinjars.basis.reactivestreams;

import static org.assertj.core.api.Assertions.assertThat;

import static com.jayway.awaitility.Awaitility.await;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class ReactiveStreamsTest {

	@Test
	public void whenSubscribeToIt_thenShouldConsumeAll() throws InterruptedException {

		//given
		SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
		EndSubscriber<String> subscriber = new EndSubscriber<>(6);
		publisher.subscribe(subscriber);
		List<String> items = List.of("1", "x", "2", "x", "3", "x");

		//when
		assertThat(publisher.getNumberOfSubscribers()).isEqualTo(1);
		items.forEach(publisher::submit);
		publisher.close();

		//when
		await().atMost(1000, TimeUnit.MILLISECONDS).until(
				() -> assertThat(subscriber.consumedElements).containsExactlyElementsOf(items)
		);
	}

	@Test
	public void whenSubscribteAndTransformElements_thenShouldConsumeAll() throws InterruptedException {

		//given
		SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
		TransformProcessor<String, Integer> transformProcessor = new TransformProcessor<>(Integer::parseInt);
		EndSubscriber<Integer> subscriber = new EndSubscriber<>(3);

		List<String> items = List.of("1", "2", "3");
		List<Integer> expectedResult = List.of(1, 2, 3);

		//when
		publisher.subscribe(transformProcessor);
		transformProcessor.subscribe(subscriber);
		items.forEach(publisher::submit);
		publisher.close();

		//then
		await().atMost(1000, TimeUnit.MILLISECONDS).until(
				() -> assertThat(subscriber.consumedElements).containsExactlyElementsOf(expectedResult)
		);
	}

	@Test
	public void whenRequestForOnlyOneElement_thenShouldConsumeOne() throws InterruptedException {

		//given
		SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
		EndSubscriber<String> subscriber = new EndSubscriber<>(1);
		publisher.subscribe(subscriber);

		List<String> items = List.of("1", "x", "2", "x", "3", "x");
		List<String> expected = List.of("1");

		//when
		assertThat(publisher.getNumberOfSubscribers()).isEqualTo(1);
		items.forEach(publisher::submit);
		publisher.close();

		//then
		await().atMost(1000, TimeUnit.MILLISECONDS).until(
				() -> assertThat(subscriber.consumedElements).containsExactlyElementsOf(expected)
		);
	}
}
