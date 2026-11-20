package lab.employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** 実験用のサンプル社員データ */
public class SampleData {

    public static List<Employee> create() {
        List<Employee> list = new ArrayList<Employee>();
        list.add(new Employee(1001, "山田 太郎", "営業部", 320000, 4, date("2015-04-01 09:00")));
        list.add(new Employee(1002, "佐藤 花子", "システム開発部", 380000, 5, date("2012-04-01 09:00")));
        list.add(new Employee(1003, "鈴木 一郎", "インフラ基盤サービス部", 290000, 3, date("2019-05-01 09:30")));
        list.add(new Employee(1004, "高橋 美咲", "総務部", 260000, 2, date("2021-10-01 09:00")));
        list.add(new Employee(1005, "Tanaka Ken", "海外事業部", 350000, 4, date("2018-07-15 13:00")));
        return list;
    }

    private static Date date(String s) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(s);
        } catch (ParseException e) {
            throw new IllegalArgumentException(s, e);
        }
    }
}
