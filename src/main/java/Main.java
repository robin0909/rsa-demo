import org.apache.commons.io.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Main {

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException, NoSuchProviderException, InvalidKeySpecException {

        // 公钥私钥写入文件
        /*
        RSAKeyUtils.RSAKeyEntity rsaKeyEntity = RSAKeyUtils.getKey();
        Key publicKey = rsaKeyEntity.getPublicKey();
        Key privateKey = rsaKeyEntity.getPrivateKey();

        byte[] publicEncoded = publicKey.getEncoded();
        byte[] privateEncoded = privateKey.getEncoded();


        FileUtils.write(new File("private.key"), new String(Base64.getEncoder().encode(privateEncoded)));
        FileUtils.write(new File("public.key"), new String(Base64.getEncoder().encode(publicEncoded)));
        */

        // 使用公钥加密
        /*
        String s = FileUtils.readFileToString(new File("public.key"));
        byte[] decode = Base64.getDecoder().decode(s.getBytes());
        // 解析公钥
        X509EncodedKeySpec pubX509  = new X509EncodedKeySpec(decode);
        KeyFactory keyf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyf.generatePublic(pubX509);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal("hello world".getBytes());
        byte[] encode = Base64.getEncoder().encode(bytes);
        System.out.println(new String(encode));

        FileUtils.write(new File("content.key"), new String(encode));
        */



        // 使用私钥解密
        String s = FileUtils.readFileToString(new File("private.key"));
        byte[] decode = Base64.getDecoder().decode(s.getBytes());
        // 解析私钥
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(decode);
        KeyFactory keyf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyf.generatePrivate(priPKCS8);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        String s1 = FileUtils.readFileToString(new File("content.key"));
        byte[] decode1 = Base64.getDecoder().decode(s1.getBytes());
        byte[] bytes = cipher.doFinal(decode1);
        System.out.println(new String(bytes));




//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        byte[] bytes = cipher.doFinal("hello world".getBytes());
//
//        byte[] encode = Base64.getEncoder().encode(bytes);
//        System.out.println(new String(encode));
//
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        byte[] bytes1 = cipher.doFinal(bytes);
//        System.out.println(new String(bytes1));
    }
}
