package org.vinyes.asistencia.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encryption {
    // key hardcodeada, si le hacen ingieneria inversa al programa we are fucked
    private static final String SECRET_KEY = "TU_CLAVE_SECRETA";

    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar", e);
        }
    }

    public static String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decoded = Base64.getDecoder().decode(encryptedData);
            return new String(cipher.doFinal(decoded));
        } catch (Exception e) {
            throw new RuntimeException("Error al descifrar", e);
        }
    }

    // registramos el usuario tambien de manera hardcodeada buff
    private static final String ucrypt1 = "ZFmETBB6ITBl/5Sr2Oi+sw==";
    private static final String pcrypt1 = "wlbGlVvchPMotlmtNfnzJg==";

    private static final String ucrypt2 = "ZFmETBB6ITBl/5Sr2Oi+sw==";
    private static final String pcrypt2 = "s33Htt8ajEiiUAG1rj0LVw==";

    public static boolean validateCredentials(String inputUser, String inputPass) {
        String decryptedUser = decrypt(ucrypt1);
        String decryptedPass = decrypt(pcrypt1);

        return decryptedUser.equals(inputUser) && decryptedPass.equals(inputPass);
    }
}
