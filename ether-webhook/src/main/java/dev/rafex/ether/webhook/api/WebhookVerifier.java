package dev.rafex.ether.webhook.api;

import dev.rafex.ether.webhook.model.WebhookPayload;
import dev.rafex.ether.webhook.model.WebhookSignature;
import dev.rafex.ether.webhook.model.WebhookVerificationResult;

/**
 * Interfaz para verificar firmas de payloads de webhook.
 * Proporciona un mecanismo para validar la autenticidad
 * de payloads de webhook recibidos.
 */
public interface WebhookVerifier {

    /**
     * Verifica la firma de un payload de webhook.
     * 
     * @param payload el payload a verificar
     * @param signature la firma a validar
     * @return el resultado de la verificación
     */
    WebhookVerificationResult verify(WebhookPayload payload, WebhookSignature signature);
}
