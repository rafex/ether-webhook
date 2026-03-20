package dev.rafex.ether.webhook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dev.rafex.ether.webhook.crypto.HmacWebhookSignerVerifier;
import dev.rafex.ether.webhook.model.WebhookPayload;

class HmacWebhookSignerVerifierTest {

    @Test
    void shouldSignAndVerifyPayload() {
        final var clock = Clock.fixed(Instant.parse("2026-03-18T18:00:00Z"), ZoneOffset.UTC);
        final var codec = new HmacWebhookSignerVerifier("super-secret-key".getBytes(), "HmacSHA256", clock);
        final var payload = WebhookPayload.ofJson("delivery-1", "user.created", Map.of("id", 42));

        final var signature = codec.sign(payload);
        assertTrue(codec.verify(payload, signature).valid());
    }

    @Test
    void shouldRejectTamperedPayload() {
        final var clock = Clock.fixed(Instant.parse("2026-03-18T18:00:00Z"), ZoneOffset.UTC);
        final var codec = new HmacWebhookSignerVerifier("super-secret-key".getBytes(), "HmacSHA256", clock);
        final var payload = WebhookPayload.ofJson("delivery-1", "user.created", Map.of("id", 42));
        final var tampered = WebhookPayload.ofJson("delivery-1", "user.created", Map.of("id", 99));

        final var signature = codec.sign(payload);
        assertFalse(codec.verify(tampered, signature).valid());
    }
}
