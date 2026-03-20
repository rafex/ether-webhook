package dev.rafex.ether.webhook.model;

public record WebhookSignature(String algorithm, String value, long timestampEpochMilli) {
}
