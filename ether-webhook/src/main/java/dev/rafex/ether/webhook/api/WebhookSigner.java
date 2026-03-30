package dev.rafex.ether.webhook.api;

import dev.rafex.ether.webhook.model.WebhookPayload;
import dev.rafex.ether.webhook.model.WebhookSignature;

/**
 * Interfaz para firmar payloads de webhook.
 * Proporciona un mecanismo para firmar payloads de webhook
 * con diferentes algoritmos de criptografía.
 */
public interface WebhookSigner {

    /**
     * Firma un payload de webhook.
     * 
     * @param payload el payload a firmar
     * @return la firma generada
     */
    WebhookSignature sign(WebhookPayload payload);
}
