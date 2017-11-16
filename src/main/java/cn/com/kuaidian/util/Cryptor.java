package cn.com.kuaidian.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.*;

/**
 * Cryptor 类可以用于对文本/数据进行加密或信息摘要的提取，目前支持DES、DESede和Blowfish加密算法，以及MD5和SHA-1信息摘要提取算法。<br>
 *
 * @author Rex
 * @version 1.0(beta)
 *          date 2007-02-02
 *          time 9:30:24
 * @since Common API 1.0
 */
public class Cryptor {
    private static Log logger = LogFactory.getLog(Cryptor.class);

    /* *************** 可使用的摘要算法 BEGIN ****************/
    /**
     * MD5信息摘要提取算法
     */
    public static final String MD5 = "MD5";
    /**
     * SHA-1信息摘要提取算法
     */
    public static final String SHA1 = "SHA-1";
    /* *************** 可使用的摘要算法 END   ****************/

    /* *************** 可使用的加密算法 BEGIN ****************/
    /**
     * DES加密算法
     */
    public static final String DES = "DES";
    /**
     * DESede加密算法
     */
    public static final String DESede = "DESede";
    /**
     * Blowfish加密算法
     */
    public static final String Blowfish = "Blowfish";
    /* *************** 可使用的加密算法 END ******************/

    /* *********** 可继承 Singleton 代码块 BEGIN *************/
    private static Map cryptors = new HashMap();

    static {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Cryptor cryptor = new Cryptor();
        cryptors.put(cryptor.getClass().getName(), cryptor);
    }

    /**
     * 用于 Singleton 继承的构造函数
     */
    protected Cryptor() {

    }

    /**
     * 获取 Cryptor 或其子类的单例实例
     *
     * @param name 想要获取的 Cryptor 或其子类的实例的名字
     * @return Cryptor 或其子类的实例
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Cryptor getInstance(String name)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (name == null) {
            name = "cn.cn.com.pc.common.security.Cryptor";
        }
        if (cryptors.get(name) == null) {
            cryptors.put(name, Class.forName(name).newInstance());
        }

        return (Cryptor) cryptors.get(name);
    }
    /* *********** 可继承 Singleton 代码块 END   *************/

    /**
     * 获取 Singleton Cryptor 实例的静态方法
     *
     * @return Cryptor实例
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Cryptor getInstance()
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (cryptors.get("cn.cn.com.pc.common.security.Cryptor") == null) {
            cryptors.put("cn.cn.com.pc.common.security.Cryptor",
                    Class.forName("cn.cn.com.pc.common.security.Cryptor").newInstance());
        }

        return (Cryptor) cryptors.get("cn.cn.com.pc.common.security.Cryptor");
    }

    /**
     * 提取文本的摘要信息
     *
     * @param text      准备提取摘要的文本
     * @param algorithm 要使用的摘要算法
     * @return 摘要
     */
    public static String digest(String text, String algorithm) throws NoSuchAlgorithmException {
        byte[] textBytes = text.getBytes();
        byte[] digestBytes = digest(textBytes, algorithm);
        return byte2hex(digestBytes);
    }

    /**
     * 提取数据的摘要信息
     *
     * @param data      准备提取摘要的数据
     * @param algorithm 要使用的摘要算法
     * @return 摘要
     */
    public static byte[] digest(byte[] data, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = null;
        digest = MessageDigest.getInstance(algorithm);
        digest.update(data);
        byte[] digestBytes = digest.digest();
        logger.debug(algorithm + "码:" + byte2hex(digestBytes));
        return digestBytes;
    }

    /**
     * 随机产生合法的密钥
     *
     * @param algorithm 要采用的加密算法，可以是 DES, DESede, Blowfish
     * @return 密钥
     * @throws NoSuchAlgorithmException
     */
    public static String generateKey(String algorithm) throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
        SecretKey key = keygen.generateKey();
        String keyStr = byte2hex(key.getEncoded());
        logger.debug("生成密钥:" + keyStr);
        return keyStr;
    }

    /**
     * 加密文本信息
     *
     * @param plain     准备加密的明文
     * @param key       密钥
     * @param algorithm 要使用的加密算法
     * @return 加密后的密文
     * @throws Exception
     */
    public static String encode(String plain, String key, String algorithm)
            throws IllegalBlockSizeException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException {
        logger.debug("加密前的明文串:" + plain);
        byte[] plainBytes = plain.getBytes();
        byte[] keyBytes = hex2byte(key);
        byte[] cipherBytes = encode(plainBytes, keyBytes, algorithm);
        return byte2hex(cipherBytes);
    }

    /**
     * 加密数据
     *
     * @param data      准备加密的数据
     * @param key       密钥
     * @param algorithm 要使用的加密算法
     * @return 加密后的数据
     * @throws Exception
     */
    public static byte[] encode(byte[] data, byte[] key, String algorithm)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKey secKey = new javax.crypto.spec.SecretKeySpec(key, algorithm);
        logger.debug("加密前的字节码:" + byte2hex(data));
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] cipherBytes = cipher.doFinal(data);
        logger.debug("加密后的字节码:" + byte2hex(cipherBytes));
        return cipherBytes;
    }

    /**
     * 解密加密后的密文
     *
     * @param cipher    准备解密的密文文本
     * @param key       密钥
     * @param algorithm 用于加密密文的加密算法
     * @return 解密后的明文
     * @throws Exception
     */
    public static String decode(String cipher, String key, String algorithm)
            throws IllegalBlockSizeException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException {
        byte[] cipherBytes = hex2byte(cipher);
        byte[] keyBytes = hex2byte(key);
        byte[] plainBytes = decode(cipherBytes, keyBytes, algorithm);
        String plainStr = new String(plainBytes);
        logger.debug("解密后的明文串:" + plainStr);
        return plainStr;
    }

    /**
     * 解密加密后的数据
     *
     * @param data      准备解密的数据
     * @param key       密钥
     * @param algorithm 用于加密数据的加密算法
     * @return 解密后的数据
     * @throws Exception
     */
    public static byte[] decode(byte[] data, byte[] key, String algorithm)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKey secKey = new javax.crypto.spec.SecretKeySpec(key, algorithm);
        logger.debug("解密前的字节码:" + byte2hex(data));
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.DECRYPT_MODE, secKey);
        byte[] plainBytes = c.doFinal(data);
        logger.debug("解密后的字节码:" + byte2hex(plainBytes));
        return plainBytes;
    }

    /**
     * 转换 byte 数组为16进制文本串
     *
     * @param bs byte 数组
     * @return 16进制文本串
     */
    public static String byte2hex(byte[] bs) {
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < bs.length; ++i) {
            hex.append(Integer.toHexString(0x0100 + (bs[i] & 0x00FF)).substring(1).toUpperCase());
        }
        return hex.toString();
    }

    /**
     * 转换16进制文本串为 byte 数组
     *
     * @param hex 16进制文本串，串中数字/字母的总数必须为偶数
     * @return byte数组
     * @throws Exception
     */
    public static byte[] hex2byte(String hex) {
        byte[] bs = new byte[hex.length() / 2];
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bs;
    }

    public static void main(String[] args) {
        // 生成key
        try {
            System.out.println("key = " + Cryptor.encode("123", "29D751912F44AAABD753444A1E44B678", Cryptor.DES));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
