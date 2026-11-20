package lab.employee;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * 社員情報ミニ管理ツール
 * (Java 8 時代の「古い書き方」をわざと入れた実験用サンプル)
 *
 * 使い方: run.bat [all|list|dates|xml|bonus|token|csv]
 *   引数なし = all(全部を順に実行)
 *
 * 1つの処理が失敗しても残りは続けて実行し、最後に NG の数を表示する。
 * こうしておくと、Java 21 で動かしたときに「どれが壊れたか」を1回で確認できる。
 */
public class Main {

    static final File WORK_DIR = new File("work");
    static final File XML_FILE = new File(WORK_DIR, "employees.xml");
    static final File CSV_FILE = new File(WORK_DIR, "employees.csv");
    static final File RULE_FILE = new File("config/bonus-rule.js");

    /** 各処理(チェックされる例外を投げてもよい Runnable のようなもの) */
    interface Step {
        void run() throws Exception;
    }

    public static void main(String[] args) {
        String cmd = args.length > 0 ? args[0] : "all";
        boolean all = "all".equals(cmd);
        WORK_DIR.mkdirs();

        printEnvironment();
        final List<Employee> employees = SampleData.create();

        int executed = 0;
        int ng = 0;

        // 罠4: リフレクションで JDK 内部のフィールドにアクセス
        if (all || "list".equals(cmd)) {
            executed++;
            ng += runStep("list(社員一覧)", () -> EmployeeReport.printList(employees));
        }
        // 罠5: 日付の書式(ロケールデータの変更で結果が変わる)
        if (all || "dates".equals(cmd)) {
            executed++;
            ng += runStep("dates(日付の書式)", () -> DateFormatDemo.print(employees.get(2).getJoinedAt()));
        }
        // 罠1: JAXB で XML 読み書き
        if (all || "xml".equals(cmd)) {
            executed++;
            ng += runStep("xml(XML保存と読み込み)", () -> {
                EmployeeXmlStore store = new EmployeeXmlStore(XML_FILE);
                store.save(employees);
                List<Employee> loaded = store.load();
                System.out.println("保存先: " + XML_FILE.getPath() + "(" + XML_FILE.length() + " バイト)");
                System.out.println("読み込んだ件数: " + loaded.size() + " 件 / 先頭: " + loaded.get(0).getName());
            });
        }
        // 罠3: Nashorn(JavaScript エンジン)で賞与計算
        if (all || "bonus".equals(cmd)) {
            executed++;
            ng += runStep("bonus(賞与計算)", () -> {
                BonusCalculator calc = new BonusCalculator(RULE_FILE);
                for (Employee e : employees) {
                    System.out.println(e.getName() + " 評価" + e.getEvaluation()
                            + " → 賞与 " + String.format("%,d", calc.calculate(e)) + " 円");
                }
            });
        }
        // 罠2: sun.misc.BASE64Encoder(JDK 内部 API)
        if (all || "token".equals(cmd)) {
            executed++;
            ng += runStep("token(社員証トークン)", () -> {
                for (Employee e : employees) {
                    String token = TokenUtil.createToken(e);
                    System.out.println(e.getId() + ": " + token);
                    System.out.println("      長さ=" + token.length()
                            + " 改行を含む=" + (token.contains("\n") ? "yes" : "no")
                            + " 復元=" + TokenUtil.decodeToken(token));
                }
            });
        }
        // 罠6(おまけ): 文字コードを指定せずにファイル出力
        if (all || "csv".equals(cmd)) {
            executed++;
            ng += runStep("csv(CSV出力)", () -> {
                CsvExporter.export(employees, CSV_FILE);
                System.out.println("出力先: " + CSV_FILE.getPath() + "(" + CSV_FILE.length() + " バイト)");
            });
        }

        if (executed == 0) {
            System.out.println("使い方: run.bat [all|list|dates|xml|bonus|token|csv]");
            return;
        }
        System.out.println();
        System.out.println("=== 結果: " + executed + " 件中 NG " + ng + " 件 ===");
    }

    static void printEnvironment() {
        System.out.println("=== 実行環境 ===");
        System.out.println("java.version    : " + System.getProperty("java.version"));
        System.out.println("java.vendor     : " + System.getProperty("java.vendor"));
        System.out.println("file.encoding   : " + System.getProperty("file.encoding"));
        System.out.println("default charset : " + Charset.defaultCharset());
        System.out.println("locale          : " + System.getProperty("user.language") + "_" + System.getProperty("user.country"));
        System.out.println("timezone        : " + TimeZone.getDefault().getID());
        System.out.println("実行日時        : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
    }

    static int runStep(String name, Step step) {
        System.out.println();
        System.out.println("=== " + name + " ===");
        try {
            step.run();
            System.out.println("[OK] " + name);
            return 0;
        } catch (Throwable t) { // Error(NoClassDefFoundError など)も拾うため Throwable
            System.out.println("[NG] " + name + " : " + t);
            t.printStackTrace(System.out);
            return 1;
        }
    }
}
