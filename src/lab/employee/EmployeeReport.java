package lab.employee;

import java.text.SimpleDateFormat;
import java.util.List;

/** 社員一覧を、全角・半角の幅をそろえて表示する */
public class EmployeeReport {

    public static void printList(List<Employee> employees) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(pad("ID", 6) + pad("氏名", 12) + pad("部署", 24) + pad("入社日", 12) + "月給");
        for (Employee e : employees) {
            System.out.println(pad(String.valueOf(e.getId()), 6)
                    + pad(e.getName(), 12)
                    + pad(e.getDepartment(), 24)
                    + pad(fmt.format(e.getJoinedAt()), 12)
                    + String.format("%,d", e.getSalary()));
        }
    }

    /** 表示幅が width になるまで後ろに空白を足す */
    static String pad(String s, int width) {
        StringBuilder sb = new StringBuilder(s);
        for (int w = LegacyStringUtil.displayWidth(s); w < width; w++) {
            sb.append(' ');
        }
        return sb.toString();
    }
}
