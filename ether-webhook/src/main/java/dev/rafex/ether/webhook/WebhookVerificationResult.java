package dev.rafex.ether.webhook;

public record WebhookVerificationResult(boolean valid, String reason, WebhookSignature signature) {

	public static WebhookVerificationResult ok(final WebhookSignature signature) {
		return new WebhookVerificationResult(true, null, signature);
	}

	public static WebhookVerificationResult failed(final String reason, final WebhookSignature signature) {
		return new WebhookVerificationResult(false, reason, signature);
	}
}
