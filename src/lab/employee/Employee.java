package lab.employee;

import java.util.Date;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/** 社員1人分のデータ。JAXB の注釈(アノテーション)付き */
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {

    private int id;
    private String name;
    private String department;
    private int salary;      // 月給(円)
    private int evaluation;  // 評価 1〜5
    private Date joinedAt;   // 入社日

    /** JAXB が XML から復元するときに使う、引数なしのコンストラクタ */
    public Employee() {
    }

    public Employee(int id, String name, String department, int salary, int evaluation, Date joinedAt) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.evaluation = evaluation;
        this.joinedAt = joinedAt;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public int getSalary() { return salary; }
    public int getEvaluation() { return evaluation; }
    public Date getJoinedAt() { return joinedAt; }
}
