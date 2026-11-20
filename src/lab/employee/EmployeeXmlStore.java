package lab.employee;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * 【罠1】JAXB(javax.xml.bind)で XML を読み書きする。
 * Java 8 までは JDK に入っていたので、ライブラリを追加しなくても使えた。
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
