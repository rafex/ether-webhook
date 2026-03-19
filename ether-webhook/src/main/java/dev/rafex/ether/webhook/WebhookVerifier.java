package dev.rafex.ether.webhook;

public interface WebhookVerifier {

	WebhookVerificationResult verify(WebhookPayload payload, WebhookSignature signature);
}
