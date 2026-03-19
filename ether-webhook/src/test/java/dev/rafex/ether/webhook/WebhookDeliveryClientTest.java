package dev.rafex.ether.webhook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.rafex.ether.http.client.EtherHttpClient;
import dev.rafex.ether.http.client.HttpMethod;
import dev.rafex.ether.http.client.HttpRequestSpec;
import dev.rafex.ether.http.client.HttpResponseSpec;
import org.junit.jupiter.api.Test;

class WebhookDeliveryClientTest {

	@Test
	void shouldBuildSignedRequest() {
		final var signing = new HmacWebhookSignerVerifier("secret".getBytes());
		final var payload = WebhookPayload.ofJson("delivery-7", "order.created", Map.of("orderId", 7))
				.withHeader("X-Custom", "yes");
		final var client = new WebhookDeliveryClient(new RecordingHttpClient(), signing);

		final var request = client.buildRequest(URI.create("https://example.com/webhook"), payload);

		assertEquals("delivery-7", request.headers().get(WebhookHeaders.DELIVERY_ID).get(0));
		assertEquals("order.created", request.headers().get(WebhookHeaders.EVENT_TYPE).get(0));
		assertTrue(request.headers().containsKey(WebhookHeaders.SIGNATURE));
		assertEquals("yes", request.headers().get("X-Custom").get(0));
	}

	private static final class RecordingHttpClient implements EtherHttpClient {
		@Override
		public HttpResponseSpec send(final HttpRequestSpec request) throws IOException, InterruptedException {
			return new HttpResponseSpec(202, Map.of(), new byte[0]);
		}

		@Override
		public <T> T sendJson(final HttpRequestSpec request, final TypeReference<T> typeReference)
				throws IOException, InterruptedException {
			throw new UnsupportedOperationException();
		}

		@Override
		public <T> T sendJson(final HttpRequestSpec request, final Class<T> type) throws IOException, InterruptedException {
			throw new UnsupportedOperationException();
		}
	}
}
