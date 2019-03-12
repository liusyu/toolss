package com.example.demo.util.aspose.util;

import com.example.demo.util.aspose.impl.Doc2Pdf;

import java.io.InputStream;

public class Licenses {
    /**
     * TODO 破解
     * @return
     */
    public static boolean getLicense() {
        boolean result = false;
        try {
            //File file = new File("D:\\workspace\\web\\src\\test\\java\\license.xml");
            //InputStream in = new FileInputStream(file);
            InputStream is = Doc2Pdf.class.getClassLoader().getResourceAsStream("license.xml"); //  license.xml应放在..\WebRoot\WEB-INF\classes路径下

            com.aspose.words.License aposeLic = new com.aspose.words.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
