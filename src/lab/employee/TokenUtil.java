package lab.employee;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;

/**
 * 社員証トークンを作る・読む。
 *
 * 【実験Eで修正】sun.misc.BASE64Encoder / BASE64Decoder(JDK の内部用クラス。Java 9 で削除)
 *   → java.util.Base64(Java 8 で追加された正式な API)
 *
 * 注意: 旧クラスは「76文字ごとに改行(CRLF)」を入れていた。
 *   Base64.getEncoder()      … 改行なし → 既存のトークンと文字列が変わってしまう
 *   Base64.getMimeEncoder()  … 76文字ごとに CRLF → 旧クラスと同じ出力になる
 * 今回は「Java 8 のときと同じ結果」を優先して MIME 用を使う。
 */
public class TokenUtil {

    /** 社員証トークン = 「ID:氏名:部署:入社日」を Base64 にしたもの */
    public static String createToken(Employee e) {
        String raw = e.getId() + ":" + e.getName() + ":" + e.getDepartment() + ":"
                + new SimpleDateFormat("yyyyMMdd").format(e.getJoinedAt());
        return Base64.getMimeEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }

    public static String decodeToken(String token) {
        // MIME 用のデコーダーは、途中の改行を無視して読んでくれる
        return new String(Base64.getMimeDecoder().decode(token), StandardCharsets.UTF_8);
    }
}
