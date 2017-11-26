import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Objects;

/**
 *  * description: xml转成csv
 *  * author: 方超
 *  * date: 2017-11-24 下午1:22
 *  
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(args[0]);
        File file = new File(args[0]);
        // File file = new File("/home/web/1.xml");
        Path path = Paths.get(file.getPath() + ".csv");

        String content = "";
        if (!file.exists()) {
            System.out.println("文件不存在");
        } else {
            SAXReader saxReader = new SAXReader();
            try {
                Document document = saxReader.read(file);
                Element root = document.getRootElement();
                Iterator<Element> iterator = root.elementIterator();
                while (iterator.hasNext()) {
                    Element node = iterator.next();
                    String attr = node.attributeValue("path");
                    if (!Objects.isNull(attr)) {
                        String str = node.getName() + "," + node.attributeValue("path") + "\n";
                        content = content.concat(str);
                        System.out.println(content);
                    }
                }
                Files.createFile(path);
                BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.WRITE);
                bufferedWriter.write(content, 0, content.length());
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
