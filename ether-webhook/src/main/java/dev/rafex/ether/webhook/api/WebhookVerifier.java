package dev.rafex.ether.webhook.api;

import dev.rafex.ether.webhook.model.WebhookPayload;
import dev.rafex.ether.webhook.model.WebhookSignature;
import dev.rafex.ether.webhook.model.WebhookVerificationResult;

public interface WebhookVerifier {

    WebhookVerificationResult verify(WebhookPayload payload, WebhookSignature signature);
}
