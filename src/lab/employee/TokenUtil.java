package lab.employee;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 【罠2】sun.misc.BASE64Encoder / BASE64Decoder を使う。
 * JDK の内部用クラスで、本来アプリから使うものではない(Java 8 でも警告が出る)。
 * 昔は java.util.Base64(Java 8 で追加)がなかったので、よく使われていた。
 */
public class TokenUtil {

    /** 社員証トークン = 「ID:氏名:部署:入社日」を Base64 にしたもの */
    public static String createToken(Employee e) {
        String raw = e.getId() + ":" + e.getName() + ":" + e.getDepartment() + ":"
                + new SimpleDateFormat("yyyyMMdd").format(e.getJoinedAt());
        try {
            return new BASE64Encoder().encode(raw.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static String decodeToken(String token) throws IOException {
        return new String(new BASE64Decoder().decodeBuffer(token), "UTF-8");
    }
}
