package br.com.fedablio.utility;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Porta {

    private static final String chave = "fedablio.com.br";
    private static String tipo = "RC4";

    public String fecha(String valor) {
        try {
            Cipher ch = Cipher.getInstance(tipo);
            SecretKey k1 = new SecretKeySpec(chave.getBytes(), (tipo));
            ch.init(Cipher.ENCRYPT_MODE, k1);
            byte b[] = ch.doFinal(valor.getBytes());
            return Kript.byteArrayToBase64String(b);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException erro) {
            throw new RuntimeException(erro);
        }
    }

    public String abre(String valor) {
        try {
            Cipher ch = Cipher.getInstance(tipo);
            SecretKey k1 = new SecretKeySpec(chave.getBytes(), tipo);
            ch.init(Cipher.DECRYPT_MODE, k1);
            byte b[] = Kript.base64StringToByteArray(valor);
            byte b1[] = ch.doFinal(b);
            return new String(b1);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException erro) {
            throw new RuntimeException(erro);
        }
    }
}