package lab.employee;

import java.io.File;
import java.util.List;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

/**
 * JAXB で XML を読み書きする。
 *
 * 【実験Eで修正】JAXB は Java 11 で JDK から削除された。
 *   - pom.xml に部品(jakarta.xml.bind-api と jaxb-runtime)を追加した
 *   - 今の JAXB はパッケージ名が javax.xml.bind → jakarta.xml.bind に変わっているので、import を書き換えた
 *     (Java EE が Eclipse 財団に移って「Jakarta EE」になり、javax の名前が使えなくなったため)
 * コードの中身は変更なし。
 */
public class EmployeeXmlStore {

    private final File file;

    public EmployeeXmlStore(File file) {
        this.file = file;
    }

    public void save(List<Employee> employees) throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(EmployeeList.class);
        Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.marshal(new EmployeeList(employees), file);
    }

    public List<Employee> load() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(EmployeeList.class);
        Unmarshaller u = ctx.createUnmarshaller();
        EmployeeList list = (EmployeeList) u.unmarshal(file);
        return list.getEmployees();
    }
}
