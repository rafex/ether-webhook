package dev.rafex.ether.webhook.model;

/**
 * Resultado de la verificación de una firma de webhook.
 * Es un record inmutable que indica si la verificación fue exitosa
 * y proporciona detalles en caso de fallo.
 * 
 * @param valid true si la firma es válida
 * @param reason motivo del fallo (si aplica)
 * @param signature la firma verificada
 */
public record WebhookVerificationResult(boolean valid, String reason, WebhookSignature signature) {

    /**
     * Crea un resultado de verificación exitosa.
     * 
     * @param signature la firma verificada
     * @return el resultado de verificación
     */
    public static WebhookVerificationResult ok(final WebhookSignature signature) {
        return new WebhookVerificationResult(true, null, signature);
    }

    /**
     * Crea un resultado de verificación fallida.
     * 
     * @param reason motivo del fallo
     * @param signature la firma (puede ser null)
     * @return el resultado de verificación
     */
    public static WebhookVerificationResult failed(final String reason, final WebhookSignature signature) {
        return new WebhookVerificationResult(false, reason, signature);
    }
}
