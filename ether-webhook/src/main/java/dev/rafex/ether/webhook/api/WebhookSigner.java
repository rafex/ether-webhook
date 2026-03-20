package dev.rafex.ether.webhook.api;

import dev.rafex.ether.webhook.model.WebhookPayload;
import dev.rafex.ether.webhook.model.WebhookSignature;

public interface WebhookSigner {

    WebhookSignature sign(WebhookPayload payload);
}
