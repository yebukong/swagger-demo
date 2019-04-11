package swagger.demo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import lombok.extern.slf4j.Slf4j;

/**
 * word读取测试
 * 
 * @author Mine
 * @date 2019/04/10 00:04:33
 */
@Slf4j
public class DocUtil {
	/**
	 * 读取doc文件内容
	 * 
	 * @param file 想要读取的文件对象
	 * @return 返回文件内容
	 * @throws IOException
	 */
	public static String doc2String(InputStream is) throws IOException {
		is = FileMagic.prepareToCheckMagic(is);
		StringBuilder result = new StringBuilder();
		try {
			if (FileMagic.valueOf(is) == FileMagic.OLE2) {//.doc
				WordExtractor re = new WordExtractor(is);
				result.append(re.getText());
				re.close();
			} else if (FileMagic.valueOf(is) == FileMagic.OOXML) {//.docx
				XWPFDocument doc = new XWPFDocument(is);
				XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
				result.append(extractor.getText());
				extractor.close();
			}
		} catch (Exception e) {
			log.warn("发生异常", e);
		} finally {
			if (is != null) {
				is.close();
			}
		}

		return result.toString();
	}

	public static String doc2String(File file) throws IOException {
		return doc2String(new FileInputStream(file));
	}

	public static void main(String[] args) {
		File file = new File("D:\\Mine\\Desktop\\test.docx");
		try {
			System.out.println(doc2String(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}