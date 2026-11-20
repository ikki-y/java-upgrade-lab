package lab.employee;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 【罠3】Nashorn(JDK 内蔵の JavaScript エンジン)で賞与を計算する。
 * 計算式を外部の .js ファイルに出しておけば、Java を再コンパイルせずに
 * 人事部門の都合で式を変えられる…という昔よくあった作り。
 *
 * 【実験Eで修正】Nashorn は Java 15 で JDK から削除された。
 *   pom.xml に単体版の部品(org.openjdk.nashorn:nashorn-core)を追加した。
 *   このクラスのコードは変更なし(部品があれば getEngineByName("nashorn") で見つかる)。
 */
public class BonusCalculator {

    private final String rule;

    public BonusCalculator(File ruleFile) throws IOException {
        this.rule = new String(Files.readAllBytes(ruleFile.toPath()), StandardCharsets.UTF_8);
    }

    public long calculate(Employee e) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.put("salary", e.getSalary());
        engine.put("evaluation", e.getEvaluation());
        Object result = engine.eval(rule);
        return ((Number) result).longValue();
    }
}
