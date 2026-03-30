package dev.rafex.ether.webhook.model;

/**
 * Firma de un payload de webhook.
 * Es un record inmutable que contiene la firma, el algoritmo usado
 * y la marca de tiempo de la firma.
 * 
 * @param algorithm algoritmo criptográfico usado
 * @param value valor de la firma en base64
 * @param timestampEpochMilli marca de tiempo en milisegundos desde epoch
 */
public record WebhookSignature(String algorithm, String value, long timestampEpochMilli) {
}
