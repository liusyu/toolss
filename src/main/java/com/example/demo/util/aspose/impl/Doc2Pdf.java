package com.example.demo.util.aspose.impl;

import com.aspose.words.*;
import com.example.demo.util.aspose.interfaces.ConverDocument;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Doc2Pdf extends ConverDocument {
	public static void main(String[] args) throws Exception{
		//Doc2Pdf.doc2pdf("D:\\test/1537430090301.doc");
		//extractImagesToFiles("D:\\test/1537430090301.doc,D:\\test");//提取doc的图片
		  String wordFile = "D:\\file\\word\\科探流程开发制作规范.docx";
	       String pdfFile = "C:\\Users\\liuyu\\Desktop/华师签章模板asp.pdf";
	       String targetFile="D:\\file\\pdf\\科探流程开发制作规范.docx";
	       pdfFile="D:\\file\\pdf\\科探流程开发制作规范.pdf";

        Doc2Pdf doc2Pdf = new Doc2Pdf();

        doc2Pdf.docToPdf(wordFile,pdfFile);
		System.out.println("========================");
		
		
		
	}


	/**
	 * doc replace to pdf
	 * @param sourceFile
	 * @param targeFile
	 * @param sourceText
	 * @param targetText
	 * @throws Exception
	 */
    public static void replaceText(String sourceFile,String targeFile,String sourceText,String targetText) throws Exception{ 

    	Document doc = new Document(sourceFile);

    	doc.getRange().replace(sourceText,targetText, true, false);

    	doc.save(targeFile);
    	System.out.println("OK");
    }

    

    /**
     * 提取doc的图片
     * @param filePath
     * @param dir
     * @throws Exception
     */
    public static void extractImagesToFiles(String filePath,String dir) throws Exception
    {
    	Document doc = new Document(filePath);

		NodeCollection<Shape> shapes = doc.getChildNodes(NodeType.SHAPE, true);
        int imageIndex = 0;
        for (Shape shape : (Iterable<Shape>) shapes){
            if (shape.hasImage()){
                String imageFileName = java.text.MessageFormat.format("Image.ExportImages.{0} Out{1}", imageIndex, FileFormatUtil.imageTypeToExtension(shape.getImageData().getImageType()));
                shape.getImageData().save(dir +File.separator+ imageFileName);
                imageIndex++;
            }
        }
    }


    protected void docToPdfImpl(InputStream inputStream,String docPath, String pdfPath) {
        try {
            long old = System.currentTimeMillis();

            Document doc = null;
            if(inputStream != null)
                doc = new Document(inputStream);
            else if(StringUtils.isNotEmpty(docPath))
                 doc = new Document(docPath);
            else return;

            File file = new File(pdfPath);  //新建一个空白pdf文档
            FileOutputStream os = new FileOutputStream(file);
                              //Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}