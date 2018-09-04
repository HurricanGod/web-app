package cn.hurrican.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/8/31
 * @Modified 11:49
 */

public class XmlUtils {

    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * pojo转换成xml
     *
     * @param obj 待转化的对象
     * @param encoding 编码
     * @return xml格式字符串
     * @throws Exception JAXBException
     */
    public static String convertToXml(Object obj, String encoding) throws Exception {
        String result = null;

        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        // 指定是否使用换行和缩排对已编组 XML 数据进行格式化的属性名称。
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        result = writer.toString();
        return result;
    }
}
