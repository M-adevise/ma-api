package app.m.advise;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.simple.JSONObject;

public class TokenServerAssistant {
  private static final String VERSION_FLAG = "04";
  private static final int IV_LENGTH = 16;
  private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

  /**
   * Use this variable to control whether to print console information during the generation of the
   * authentication token.
   */
  public static boolean VERBOSE = false;

  /** Definition of privilege bits. */
  public static final String PrivilegeKeyLogin = "1"; // Login

  public static final String PrivilegeKeyPublish = "2"; // Broadcast

  /** Definition of privilege switches. */
  public static final int PrivilegeEnable = 1; // Enable

  public static final int PrivilegeDisable = 0; // Disable

  public static enum ErrorCode {
    /** Authentication token generation successful. */
    SUCCESS(0),
    /** AppId parameter error. */
    ILLEGAL_APP_ID(1),
    /** UserId parameter error. */
    ILLEGAL_USER_ID(3),
    /** Secret parameter error. */
    ILLEGAL_SECRET(5),
    /** EffectiveTimeInSeconds parameter error. */
    ILLEGAL_EFFECTIVE_TIME(6),
    /** Other undefined errors. */
    OTHER(-1);

    private ErrorCode(int code) {
      this.value = code;
    }

    public int value;
  }

  public static class ErrorInfo {
    public ErrorCode code;
    public String message;

    ErrorInfo() {
      code = ErrorCode.SUCCESS;
      message = "";
    }

    @Override
    public String toString() {
      return "{\"code\": " + code.value + ", \"message\": \"" + message + "\"}";
    }
  }

  /** Token structure. */
  public static class TokenInfo {
    /** Token body generated based on the provided content. */
    public String data = "";

    /** Error message. */
    public ErrorInfo error;

    TokenInfo() {
      this.error = new ErrorInfo();
    }

    @Override
    public String toString() {
      return "TokenInfo {\"error\": " + error + ", \"data\": \"" + data + "\"}";
    }
  }

  private TokenServerAssistant() {}

  /**
   * Generates an authentication token for communication with the Zego server based on the provided
   * parameters.
   *
   * <p>appId The digital ID assigned by Zego, the unique identifier for each developer userId User
   * ID secret The key corresponding to appId provided by Zego, please keep it safe and do not
   * disclose it. effectiveTimeInSeconds Validity period of the token, unit: second Returns the
   * token content. Before use, check whether the error field is SUCCESS.
   */
  public static TokenInfo generateToken04(
      long appId, String userId, String secret, int effectiveTimeInSeconds, String payload) {
    TokenInfo token = new TokenInfo();

    // Check the appId.
    if (appId == 0) {
      token.error.code = ErrorCode.ILLEGAL_APP_ID;
      token.error.message = "illegal appId";
      debugInfo("illegal appId");
      return token;
    }

    // Check the userId.
    if (userId == null || userId == "" || userId.length() > 64) {
      token.error.code = ErrorCode.ILLEGAL_USER_ID;
      token.error.message = "illegal userId";
      debugInfo("userId can't empty and must no more than 64 characters");
      return token;
    }

    // Check the secret.
    if (secret == null || secret == "" || secret.length() != 32) {
      token.error.code = ErrorCode.ILLEGAL_SECRET;
      token.error.message = "illegal secret";
      debugInfo("secret must 32 characters");
      return token;
    }

    // Check the effectiveTimeInSeconds.
    if (effectiveTimeInSeconds <= 0) {
      token.error.code = ErrorCode.ILLEGAL_EFFECTIVE_TIME;
      token.error.message = "effectiveTimeInSeconds must > 0";
      debugInfo("effectiveTimeInSeconds must > 0");
      return token;
    }

    debugInfo("generate random IV ...");
    byte[] ivBytes = new byte[IV_LENGTH];
    SecureRandom rnd = new SecureRandom();
    rnd.nextBytes(ivBytes);
    //         String iv = "cceutxv9vrhfnx0r";
    //         ivBytes = iv.getBytes();
    //         ThreadLocalRandom.current().nextBytes(ivBytes);

    JSONObject json = new JSONObject();
    json.put("app_id", appId);
    json.put("user_id", userId);

    long nowTime = System.currentTimeMillis() / 1000;
    long expire_time = nowTime + effectiveTimeInSeconds;
    json.put("ctime", nowTime);
    json.put("expire", expire_time);
    int nonce = new Random().nextInt();
    json.put("nonce", nonce);
    json.put("payload", payload);
    String content = json.toString();
    System.out.println("current nonce: " + nonce);

    try {
      debugInfo("encrypt content ...");
      byte[] contentBytes = encrypt(content.getBytes("UTF-8"), secret.getBytes(), ivBytes);

      ByteBuffer buffer = ByteBuffer.wrap(new byte[contentBytes.length + IV_LENGTH + 12]);
      buffer.order(ByteOrder.BIG_ENDIAN);
      buffer.putLong(expire_time); // 8 bytes
      packBytes(ivBytes, buffer); // IV_LENGTH + 2 bytes
      packBytes(contentBytes, buffer); // contentBytes.length + 2 bytes

      debugInfo("serialize with base64 ...");
      token.data = VERSION_FLAG + Base64.getEncoder().encodeToString(buffer.array());

      token.error.code = ErrorCode.SUCCESS;
    } catch (Exception e) {
      debugInfo("generate token failed: " + e);
      token.error.code = ErrorCode.OTHER;
      token.error.message = "" + e;
    }

    return token;
  }

  private static byte[] encrypt(byte[] content, byte[] secretKey, byte[] ivBytes) throws Exception {
    if (secretKey == null || secretKey.length != 32) {
      throw new IllegalArgumentException("secret key's length must be 32 bytes");
    }

    if (ivBytes == null || ivBytes.length != 16) {
      throw new IllegalArgumentException("ivBytes's length must be 16 bytes");
    }

    if (content == null) {
      content = new byte[] {};
    }
    SecretKeySpec key = new SecretKeySpec(secretKey, "AES");
    IvParameterSpec iv = new IvParameterSpec(ivBytes);

    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);

    return cipher.doFinal(content);
  }

  private static void packBytes(byte[] buffer, ByteBuffer target) {
    target.putShort((short) buffer.length);
    target.put(buffer);
  }

  private static void debugInfo(String info) {
    if (VERBOSE) {
      System.out.println(info);
    }
  }
}
