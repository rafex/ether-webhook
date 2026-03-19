package dev.rafex.ether.webhook;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class HmacWebhookSignerVerifier implements WebhookSigner, WebhookVerifier {

	private static final String DEFAULT_ALGORITHM = "HmacSHA256";

	private final byte[] secret;
	private final String algorithm;
	private final Clock clock;

	public HmacWebhookSignerVerifier(final byte[] secret) {
		this(secret, DEFAULT_ALGORITHM, Clock.systemUTC());
	}

	public HmacWebhookSignerVerifier(final byte[] secret, final String algorithm, final Clock clock) {
		this.secret = secret == null ? new byte[0] : secret.clone();
		this.algorithm = Objects.requireNonNullElse(algorithm, DEFAULT_ALGORITHM);
		this.clock = clock == null ? Clock.systemUTC() : clock;
		if (this.secret.length == 0) {
			throw new IllegalArgumentException("secret is required");
		}
	}

	@Override
	public WebhookSignature sign(final WebhookPayload payload) {
		final long timestamp = Instant.now(clock).toEpochMilli();
		return new WebhookSignature(algorithm, computeSignature(payload, timestamp), timestamp);
	}

	@Override
	public WebhookVerificationResult verify(final WebhookPayload payload, final WebhookSignature signature) {
		if (signature == null) {
			return WebhookVerificationResult.failed("missing_signature", null);
		}
		if (!algorithm.equals(signature.algorithm())) {
			return WebhookVerificationResult.failed("unsupported_algorithm", signature);
		}

		final String expected = computeSignature(payload, signature.timestampEpochMilli());
		if (!MessageDigest.isEqual(expected.getBytes(StandardCharsets.UTF_8),
				signature.value().getBytes(StandardCharsets.UTF_8))) {
			return WebhookVerificationResult.failed("bad_signature", signature);
		}
		return WebhookVerificationResult.ok(signature);
	}

	private String computeSignature(final WebhookPayload payload, final long timestamp) {
		try {
			final var mac = Mac.getInstance(algorithm);
			mac.init(new SecretKeySpec(secret, algorithm));
			mac.update(canonicalPayload(payload, timestamp).getBytes(StandardCharsets.UTF_8));
			return Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal());
		} catch (final Exception e) {
			throw new IllegalStateException("unable to sign webhook payload", e);
		}
	}

	private static String canonicalPayload(final WebhookPayload payload, final long timestamp) {
		return String.join("\n",
				nullToEmpty(payload.deliveryId()),
				nullToEmpty(payload.eventType()),
				nullToEmpty(payload.contentType()),
				Long.toString(timestamp),
				Base64.getUrlEncoder().withoutPadding().encodeToString(payload.body()));
	}

	private static String nullToEmpty(final String value) {
		return value == null ? "" : value;
	}
}
