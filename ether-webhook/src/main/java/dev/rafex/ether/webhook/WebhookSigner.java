package dev.rafex.ether.webhook;

public interface WebhookSigner {

	WebhookSignature sign(WebhookPayload payload);
}
