import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RSAKeyUtils {

    private RSAKeyUtils() {}

    public static RSAKeyEntity getKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator= KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair=keyPairGenerator.generateKeyPair();
        return new RSAKeyEntity(keyPair.getPublic(), keyPair.getPrivate());
    }


    public static class RSAKeyEntity {

        private Key publicKey;
        private Key privateKey;

        public RSAKeyEntity() {
        }

        public RSAKeyEntity(Key publicKey, Key privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public Key getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(Key publicKey) {
            this.publicKey = publicKey;
        }

        public Key getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(Key privateKey) {
            this.privateKey = privateKey;
        }
    }
}
