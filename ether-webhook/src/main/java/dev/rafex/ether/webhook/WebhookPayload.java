package dev.rafex.ether.webhook;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.rafex.ether.http.client.HttpMethod;
import dev.rafex.ether.http.client.HttpRequestSpec;
import dev.rafex.ether.json.JsonUtils;

public record WebhookPayload(
		String deliveryId,
		String eventType,
		Instant occurredAt,
		String contentType,
		byte[] body,
		Map<String, List<String>> headers) {

	public WebhookPayload {
		body = body == null ? new byte[0] : body.clone();
		headers = normalizeHeaders(headers);
	}

	public static WebhookPayload ofJson(final String deliveryId, final String eventType, final Object value) {
		return new WebhookPayload(deliveryId, eventType, Instant.now(), "application/json", JsonUtils.toJsonBytes(value), Map.of());
	}

	public static WebhookPayload ofText(final String deliveryId, final String eventType, final String value) {
		return new WebhookPayload(deliveryId, eventType, Instant.now(), "text/plain; charset=utf-8",
				value == null ? new byte[0] : value.getBytes(StandardCharsets.UTF_8), Map.of());
	}

	public WebhookPayload withHeader(final String name, final String value) {
		final var copy = new LinkedHashMap<String, List<String>>();
		copy.putAll(headers);
		copy.put(name, List.of(value));
		return new WebhookPayload(deliveryId, eventType, occurredAt, contentType, body, copy);
	}

	public HttpRequestSpec toRequest(final URI endpoint) {
		final var builder = HttpRequestSpec.builder(HttpMethod.POST, endpoint)
				.body(body)
				.contentType(contentType);
		for (final var entry : headers.entrySet()) {
			for (final var value : entry.getValue()) {
				builder.header(entry.getKey(), value);
			}
		}
		return builder.build();
	}

	private static Map<String, List<String>> normalizeHeaders(final Map<String, List<String>> raw) {
		if (raw == null || raw.isEmpty()) {
			return Map.of();
		}
		final var copy = new LinkedHashMap<String, List<String>>();
		for (final var entry : raw.entrySet()) {
			copy.put(entry.getKey(), List.copyOf(entry.getValue()));
		}
		return Map.copyOf(copy);
	}
}
