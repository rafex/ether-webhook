package dev.rafex.ether.webhook;

public record WebhookSignature(String algorithm, String value, long timestampEpochMilli) {
}
