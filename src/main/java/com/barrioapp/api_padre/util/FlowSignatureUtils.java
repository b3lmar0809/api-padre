package com.barrioapp.api_padre.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;
import java.util.TreeMap;

/**
 * FlowSignatureUtils class
 *
 * @Version: 1.0.0 - Apr 21, 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - Apr 21, 2026
 */
public class FlowSignatureUtils {

    private static final String HMAC_SHA256 = "HmacSHA256";

    private FlowSignatureUtils() {}

    //flow requiere que los parametros se ordenen alfabeticamente por clave, concatenandolos como
    //"key1value1key2value2..." (sin separadores) antes de firmar con HMAC-SHA256.
    public static String sign(Map<String, String> params, String secretKey) {
        StringBuilder data = new StringBuilder();
        new TreeMap<>(params).forEach((key, value) -> data.append(key).append(value));

        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(new SecretKeySpec(secretKey.getBytes(), HMAC_SHA256));
            byte[] hash = mac.doFinal(data.toString().getBytes());

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to sign Flow request", e);
        }
    }

    //extrae "s" de los parametros, recalcula la firma con el resto y la compara.
    public static boolean verifySignature(Map<String, String> params, String secretKey) {
        Map<String, String> copy = new TreeMap<>(params);
        String receivedSignature = copy.remove("s");

        if (receivedSignature == null) {
            return false;
        }

        return sign(copy, secretKey).equals(receivedSignature);
    }
}
