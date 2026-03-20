# ether-webhook

Webhook signing, verification and delivery utilities for Ether.

## Scope

- Payload abstraction with headers and body normalization
- HMAC signing and verification helpers
- Delivery client built on top of `ether-http-client`
- Standard webhook headers for event delivery metadata

## Notes

- `ether-webhook` is layered on top of `ether-http-client`, not a separate transport stack.
- It is intended for signed callbacks and event delivery, not as a generic message broker abstraction.

## Maven

```xml
<dependency>
  <groupId>dev.rafex.ether.webhook</groupId>
  <artifactId>ether-webhook</artifactId>
  <version>8.0.0-SNAPSHOT</version>
</dependency>
```

## Example

```java
var webhook = new HmacWebhookSignerVerifier("change-me".getBytes(StandardCharsets.UTF_8));
var payload = WebhookPayload.ofJson("delivery-1", "user.created", Map.of("id", 42));
var signature = webhook.sign(payload);
```
