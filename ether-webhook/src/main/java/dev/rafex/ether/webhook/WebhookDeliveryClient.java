package dev.rafex.ether.webhook;

import java.io.IOException;
import java.net.URI;

import dev.rafex.ether.http.client.EtherHttpClient;
import dev.rafex.ether.http.client.HttpMethod;
import dev.rafex.ether.http.client.HttpRequestSpec;
import dev.rafex.ether.http.client.HttpResponseSpec;

public final class WebhookDeliveryClient {

	private final EtherHttpClient httpClient;
	private final WebhookSigner signer;

	public WebhookDeliveryClient(final EtherHttpClient httpClient, final WebhookSigner signer) {
		this.httpClient = httpClient;
		this.signer = signer;
	}

	public HttpRequestSpec buildRequest(final URI endpoint, final WebhookPayload payload) {
		final var signature = signer.sign(payload);
		final var builder = HttpRequestSpec.builder(HttpMethod.POST, endpoint)
				.body(payload.body())
				.contentType(payload.contentType())
				.header(WebhookHeaders.DELIVERY_ID, payload.deliveryId())
				.header(WebhookHeaders.EVENT_TYPE, payload.eventType())
				.header(WebhookHeaders.TIMESTAMP, Long.toString(signature.timestampEpochMilli()))
				.header(WebhookHeaders.ALGORITHM, signature.algorithm())
				.header(WebhookHeaders.SIGNATURE, signature.value());
		for (final var entry : payload.headers().entrySet()) {
			for (final var value : entry.getValue()) {
				builder.header(entry.getKey(), value);
			}
		}
		return builder.build();
	}

	public HttpResponseSpec send(final URI endpoint, final WebhookPayload payload)
			throws IOException, InterruptedException {
		return httpClient.send(buildRequest(endpoint, payload));
	}
}
