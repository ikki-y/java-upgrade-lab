package lab.employee;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 社員データを CSV に出力する。
 *
 * 【実験Eで修正】文字コードを指定せずに FileWriter で書いていたのを、明示的に指定する形に変えた。
 *   - 指定しないと「実行環境の既定の文字コード」になる
 *   - その既定値が Java 18 で UTF-8 に変わった(Java 8 の日本語 Windows では MS932)
 * 今回は「Java 8 のときと同じファイル」を優先して Windows-31J(= MS932)を指定する。
 * UTF-8 に移行したい場合も、ここで明示的に書けば、実行環境に左右されなくなる。
 */
public class CsvExporter {

    static final Charset CSV_CHARSET = Charset.forName("Windows-31J");

    public static void export(List<Employee> employees, File file) throws IOException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        try (Writer w = new FileWriter(file, CSV_CHARSET)) { // Java 11 で追加された、文字コード指定付きのコンストラクタ
            w.write("ID,氏名,部署,入社日,月給\r\n");
            for (Employee e : employees) {
                w.write(e.getId() + "," + e.getName() + "," + e.getDepartment() + ","
                        + fmt.format(e.getJoinedAt()) + "," + e.getSalary() + "\r\n");
            }
        }
    }
}
