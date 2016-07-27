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
        String message = args.getString(0);
        if (action.equals("Encrypt")) {
            callbackContext.success(this.Encrypt("5faa90d3038ad41ec5ed89802807965c",message));
            return true;
        }else if(action.equals("Decrypt")){
           callbackContext.success(this.Decrypt("5faa90d3038ad41ec5ed89802807965c",message));
           return true;
        }else{
          callbackContext.error("Metodo não Existe!");
          return false;
        }
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message + " Brasilcard");
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private String Encrypt(String chave, String texto) {
          String textoE = "";
          try{
              myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
              arrayBytes = chave.getBytes(UNICODE_FORMAT);
              ks = new DESedeKeySpec(arrayBytes);
              skf = SecretKeyFactory.getInstance(myEncryptionScheme);
              cipher = Cipher.getInstance(myEncryptionScheme);
              key = skf.generateSecret(ks);
              cipher.init(Cipher.ENCRYPT_MODE, key);
              byte[] plainText = texto.getBytes(UNICODE_FORMAT);
              byte[] encryptedText = cipher.doFinal(plainText);
              textoE = new String(Base64.encodeBase64(encryptedText));
          }
          catch (Exception e){
              e.printStackTrace();
              return "Nao foi possivel Criptografar";
          }
          return textoE;
        }

    private String Decrypt(String chave, String texto){
      byte[] decrypted = null;
      try{
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = chave.getBytes(UNICODE_FORMAT);
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.decodeBase64(texto.getBytes(UNICODE_FORMAT));
        decrypted = cipher.doFinal(decoded);
        }
          catch (Exception e){
              e.printStackTrace();
              return "Não foi possivel Descriptografar.";
          }

          return  new String(decrypted);
      }

}
