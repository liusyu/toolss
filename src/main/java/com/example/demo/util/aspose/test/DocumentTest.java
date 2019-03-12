package com.example.demo.util.aspose.test;

import com.aspose.words.*;
import com.example.demo.util.aspose.entity.Position;
import com.example.demo.util.aspose.impl.InsertDocument;
import com.example.demo.util.aspose.impl.ShapeDocument;
import com.example.demo.util.aspose.util.Licenses;

import java.awt.*;

public class DocumentTest {
    static String targetDoc  =  "D:\\word\\target\\createDocument.doc";
    static String insertDoc = "D:\\word\\source\\InsertDocument1.doc";
    static String sourceDoc = "D:\\word\\source\\aspose19.2破解.docx";
    static String img = "C:\\Users\\Public\\Pictures\\Sample Pictures\\aa.png";
    static String key = "签章";
    static InsertDocument insertDocument = new InsertDocument();
    static ShapeDocument shapeDocument = new ShapeDocument();


    public static void main(String[] args) throws Exception {
        boolean license = Licenses.getLicense();
        Position position = new Position(sourceDoc,targetDoc,insertDoc,"签章");
        position.setReplaceText("<p>&ldquo;Some Text&rdquo;</p>");
        position.setImgLocal(img);
        position.setLeft(0);
        position.setRight(0);
        shapeDocument.replaceImgByKey(position);
//        Position position = new Position(sourceDoc,targetDoc,insertDoc,"签章");
       // insertDocument.replaceText(position);
       // insertDocument.insertDocumentAtReplace(position);
       // getDoc(sourceDoc);
       // document.setBackgroundShape(shape);
        // insertImg();
    }
    public static void getDoc(String doc) throws Exception {
        Document document = new Document(doc);
        FieldCollection fields = document.getRange().getFields();

        for (Field field:fields ) {
            String result = field.getResult();
            System.out.println(result);
        }
    }
    public void test()throws Exception {
        boolean license = Licenses.getLicense();
        String url  =  "D:\\word\\target\\createDocument.doc";
        String sourceDoc = "D:\\view\\Document.EpubConversion.doc";
        Document document = new Document();
        DocumentBuilder builder = new DocumentBuilder(document);
        String img = "C:\\Users\\Public\\Pictures\\Sample Pictures\\aa.png";
        // byte [] imageBytes = DocumentHelpe.convertImageToByteArray(new File(img),"png");


        builder.insertImage(img,RelativeHorizontalPosition.MARGIN,100.0,RelativeVerticalPosition.MARGIN,100.0,100.0,100.0,WrapType.SQUARE);

        // builder.insertImage();

        //builder.insertHtml("<P align ='right'> Paragraph right </ P>"+"<b>隐式段落</ b>"+"<div align ='center'> Div center </ div>" +"<h1 align ='left'>标题1左。</ h1><br><span>11111111111</span>");
        builder.insertHtml("<p>1111111111</p>",true);
        //我们称这种方法开始构建表。
        builder.startTable();
        builder.insertCell();
        builder.write("第1行,单元格1内容。");

//构建第二个单元格
        builder.insertCell();
        builder.write("第1行,Cell 2内容。");
        builder.insertCell();
        builder.write("第1行,单元格3内容。");
//调用以下方法结束行并开始新行。
        builder.endRow();

//构建第二行的第一个单元格。
        builder.insertCell();
        builder.write("第2行,单元格1内容");

//构建第二个单元格。
        builder.insertCell();
        builder.write("第2行,Cell 2内容。");
        builder.endRow();

//表示我们已经完成了构建表的信号。
        builder.endTable();


        final Table table = builder.startTable();

//制作标题行
        builder.insertCell();

//设置表格的左缩进。必须在之后应用表格宽格式
//表格中至少有一行。
        table.setLeftIndent(20.0);

//设置高度并定义标题行的高度规则。
        builder.getRowFormat();//自动调用setHeight(40.0)。
        builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);

//标题行的一些特殊功能。
        builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(198,217,241));
        builder.getParagraphFormat();//参考setAlignment(ParagraphAlignment.CENTER);
        builder.getFont();//的setSize(16)。
        builder.getFont();//的setName( "Arial字体");
        builder.getFont();//参考setBold(真)。

        builder.getCellFormat().setWidth(100.0);
        builder.write("Header Row，\\ n Cell 1");

//我们不需要指定此单元格的宽度，因为它是从前一个单元格继承的。
        builder.insertCell();
        builder.write("Header Row，\\ n Cell 2");

        builder.insertCell();
        builder.getCellFormat().setWidth(200.0);
        builder.write("标题行,\\ n单元格3");
        builder.endRow();

//设置其他行和单元格的功能。
        builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
        builder.getCellFormat().setWidth(100.0);
        builder.getCellFormat();//参考setVerticalAlignment(CellVerticalAlignment.CENTER)。

//重置高度并为表体定义不同的高度规则
        builder.getRowFormat();//自动调用setHeight(30.0)。
        builder.getRowFormat().setHeightRule(HeightRule.AUTO);
        builder.insertCell();
//重置字体格式。
        builder.getFont();//的setSize(12)。
        builder.getFont();//参考setBold(假);

//构建其他单元格。
        builder.write("第1行，单元格1内容");
        builder.insertCell();
        builder.write("第1行，Cell 2内容");

        builder.insertCell();
        builder.getCellFormat().setWidth(200.0);
        builder.write("第1行，第3单元内容");
        builder.endRow();

        builder.insertCell();
        builder.getCellFormat().setWidth(100.0);
        builder.write("第2行，单元格1内容");

        builder.insertCell();
        builder.write("第2行，第2单元内容");

        builder.insertCell();
        builder.getCellFormat().setWidth(200.0);
        builder.write("第2行，第3单元内容。");
        builder.endRow();
        builder.endTable();
        builder.insertDocument(new Document(sourceDoc),2);

        document.save(url);

    }
    public static void insertDocument(Node insertAfterNode, Document srcDoc) throws Exception {
        // Make sure that the node is either a paragraph or table.
        if ((insertAfterNode.getNodeType() != NodeType.PARAGRAPH) & (insertAfterNode.getNodeType() != NodeType.TABLE))
            throw new IllegalArgumentException("The destination node should be either a paragraph or table.");

        // We will be inserting into the parent of the destination paragraph.
        CompositeNode dstStory = insertAfterNode.getParentNode();

        // This object will be translating styles and lists during the import.
        NodeImporter importer = new NodeImporter(srcDoc, insertAfterNode.getDocument(), ImportFormatMode.KEEP_SOURCE_FORMATTING);

        // Loop through all sections in the source document.
        for (Section srcSection : srcDoc.getSections()) {
            // Loop through all block level nodes (paragraphs and tables) in the body of the section.
            for (Node srcNode : srcSection.getBody()) {
                // Let's skip the node if it is a last empty paragraph in a section.
                if (srcNode.getNodeType() == (NodeType.PARAGRAPH)) {
                    Paragraph para = (Paragraph) srcNode;
                    if (para.isEndOfSection() && !para.hasChildNodes())
                        continue;
                }

                // This creates a clone of the node, suitable for insertion into the destination document.
                Node newNode = importer.importNode(srcNode, true);

                // Insert new node after the reference node.
                dstStory.insertAfter(newNode, insertAfterNode);
                insertAfterNode = newNode;
            }
        }
    }
    public static void insertImg(){
        Position position = new Position(sourceDoc,targetDoc);
        position.setImgLocal(img);
        position.setBookmark("签章");
    }
}
