package dev.rafex.ether.webhook;

public final class WebhookHeaders {

	public static final String DELIVERY_ID = "X-Ether-Webhook-Delivery-Id";
	public static final String EVENT_TYPE = "X-Ether-Webhook-Event-Type";
	public static final String TIMESTAMP = "X-Ether-Webhook-Timestamp";
	public static final String ALGORITHM = "X-Ether-Webhook-Algorithm";
	public static final String SIGNATURE = "X-Ether-Webhook-Signature";
	public static final String CONTENT_TYPE = "X-Ether-Webhook-Content-Type";

	private WebhookHeaders() {
	}
}
