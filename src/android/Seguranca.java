package cordova.plugin.seguranca;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class Seguranca extends CordovaPlugin {

    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    byte[] arrayBytes;
    SecretKey key;

    private String myEncryptionScheme;

    private String TextoDecrypt;
    private String TextoEncrypt;
    private String Chave;

    public String getTextoDecrypt() {
        return TextoDecrypt;
    }

    public void setTextoDecrypt(String textoDecrypt) {
        TextoDecrypt = textoDecrypt;
    }

    public String getTextoEncrypt() {
        return TextoEncrypt;
    }

    public void setTextoEncrypt(String textoEncrypt) {
        TextoEncrypt = textoEncrypt;
    }

    public String getChave() {
        return Chave;
    }

    public void setChave(String chave) {
        Chave = chave;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    public String Encrypt(Seguranca seguranca) {
          try {

              myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
              arrayBytes = this.getChave().getBytes(UNICODE_FORMAT);
              ks = new DESedeKeySpec(arrayBytes);
              skf = SecretKeyFactory.getInstance(myEncryptionScheme);
              cipher = Cipher.getInstance(myEncryptionScheme);
              key = skf.generateSecret(ks);
              cipher.init(Cipher.ENCRYPT_MODE, key);
              byte[] plainText = seguranca.getTextoDecrypt().getBytes(UNICODE_FORMAT);
              byte[] encryptedText = cipher.doFinal(plainText);
              seguranca.setTextoEncrypt(new String(Base64.encodeBase64(encryptedText)));
          }
          catch (Exception e)
          {
              e.printStackTrace();
          }
          return seguranca.getTextoEncrypt();
      }


}
