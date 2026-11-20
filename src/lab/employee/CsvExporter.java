package lab.employee;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 【罠6(おまけ)】文字コードを指定せずに FileWriter で CSV を書く。
 * この場合、ファイルは「実行環境の既定の文字コード」で書かれる。
 */
public class CsvExporter {

    public static void export(List<Employee> employees, File file) throws IOException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        try (Writer w = new FileWriter(file)) { // 文字コード指定なし
            w.write("ID,氏名,部署,入社日,月給\r\n");
            for (Employee e : employees) {
                w.write(e.getId() + "," + e.getName() + "," + e.getDepartment() + ","
                        + fmt.format(e.getJoinedAt()) + "," + e.getSalary() + "\r\n");
            }
        }
    }
}
