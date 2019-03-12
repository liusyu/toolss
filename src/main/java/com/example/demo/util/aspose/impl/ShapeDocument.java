package com.example.demo.util.aspose.impl;

import com.aspose.words.*;
import com.example.demo.util.aspose.entity.Position;
import com.example.demo.util.aspose.interfaces.ShapeDocumentAbstract;
import org.apache.commons.lang.StringUtils;

import java.net.URL;

public class ShapeDocument extends ShapeDocumentAbstract {
    @Override
    /**
     * //String html = "<p>&ldquo;Some Text&rdquo;</p>"; repalceText
     * @param position
     * @throws Exception
     */
    protected void replaceTextImpl(Position position) throws Exception {
        // Initialize a Document.
        com.aspose.words.Document doc = new Document(position.getSourceDoc());
        // Use a document builder to add content to the document.
        DocumentBuilder builder = new DocumentBuilder(doc);
        FindReplaceOptions findReplaceOptions = new FindReplaceOptions();
        findReplaceOptions.setReplacingCallback(new ShapeDocument.FindAndInsertImg(position));
        findReplaceOptions.setPreserveMetaCharacters(true);
        if(StringUtils.isNotBlank(position.getImgLocal()))
             doc.getRange().replace(position.getKey(), position.getImgLocal(), findReplaceOptions);
        else
            doc.getRange().replace(position.getKey(), position.getImgUrl(), findReplaceOptions);
        doc.save(position.getTargetDoc());


    }
    private class FindAndInsertImg implements IReplacingCallback {
        Position position;
        FindAndInsertImg(Position position){
            this.position = position;
        }
        public int replacing(ReplacingArgs e) throws Exception {
            // This is a Run node that contains either the beginning or the complete match.
            Node currentNode = e.getMatchNode();
            // create Document Buidler and insert MergeField
            DocumentBuilder builder = new DocumentBuilder((Document) e.getMatchNode().getDocument());
            builder.moveTo(currentNode);
            if(StringUtils.isNotBlank(position.getImgLocal()))
                builder.insertImage(position.getImgLocal(),RelativeHorizontalPosition.MARGIN,position.getLeft(),RelativeVerticalPosition.MARGIN,position.getRight(),position.getWidth(),position.getHeight(),WrapType.SQUARE);
                //  Shape shape = builder.insertImage(local)
            else
                builder.insertImage(new URL(position.getImgUrl()).openStream(),RelativeHorizontalPosition.MARGIN,position.getLeft(),RelativeVerticalPosition.MARGIN,position.getRight(),position.getWidth(),position.getHeight(),WrapType.SQUARE);
            // builder.insertHtml(e.getReplacement());
            currentNode.remove();
            //Signal to the replace engine to do nothing because we have already done all what we wanted.
            return ReplaceAction.SKIP;
        }
    }
    @Override
    protected void insertImgToBookmarkImpl(Position position) throws Exception {
        // This creates a builder and also an empty document inside the builder.
        com.aspose.words.Document document = new Document(position.getSourceDoc());
        DocumentBuilder builder = new DocumentBuilder(document);
        builder.moveToBookmark(position.getBookmark());
// By default, the image is inline.
        Shape shape = null;
        if(StringUtils.isNotBlank(position.getImgLocal()))
            shape = builder.insertImage(position.getImgLocal(),RelativeHorizontalPosition.MARGIN,position.getLeft(),RelativeVerticalPosition.MARGIN,position.getRight(),position.getWidth(),position.getHeight(),WrapType.SQUARE);
            //  Shape shape = builder.insertImage(local)
        else
            shape = builder.insertImage(new URL(position.getImgUrl()).openStream(),RelativeHorizontalPosition.MARGIN,position.getLeft(),RelativeVerticalPosition.MARGIN,position.getRight(),position.getWidth(),position.getHeight(),WrapType.SQUARE);
        shape.setWrapType(WrapType.NONE);
        shape.setBehindText(false);
        shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        shape.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
        if(position.isCenter()) {
            shape.setHorizontalAlignment(HorizontalAlignment.CENTER);
            shape.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        document.save(position.getTargetDoc());
    }

    @Override
    protected void insertWaterToCenterImpl(String imgUrl, String sourceDoc, String targetDoc,String[] widthAndHeight) throws Exception {
        Document document = new Document(sourceDoc);
        DocumentBuilder builder = new DocumentBuilder(document);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);

        Shape shape = builder.insertImage(imgUrl);
        //设置宽和高
        if(widthAndHeight.length == 2){
            shape.setWidth(Double.valueOf(widthAndHeight[0]));
            shape.setHeight(Double.valueOf(widthAndHeight[1]));
        }

        shape.setWrapType(WrapType.NONE);
        shape.setBehindText(true);

        shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        shape.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);

        // 计算图像左侧和顶部位置，使其显示在页面中央。
        shape.setLeft((builder.getPageSetup().getPageWidth() - shape.getWidth()) / 2);
        shape.setTop((builder.getPageSetup().getPageHeight() - shape.getHeight()) / 2);

        document.save(targetDoc);
    }

    @Override
    protected void insertWaterImpl(String imgUrl,String sourceDoc,String targetDoc,String bookmark){
        try {
            insertWaterToBook(imgUrl,sourceDoc,targetDoc,bookmark,new String[]{"70","70"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    /**
     * 在书签的位置杀入水印
     * @param imgUrl
     * @param sourceDoc
     * @param targetDoc
     * @param bookmark
     * @throws Exception
     */
    protected void insertWaterImpl(String imgUrl,String sourceDoc,String targetDoc,String bookmark,String[] widthAndHeight) throws Exception {
        Document document = new Document(sourceDoc);
        DocumentBuilder builder = new DocumentBuilder(document);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);

        Shape shape = builder.insertImage(imgUrl);

        //设置宽和高
        if(widthAndHeight.length == 2){
            shape.setWidth(Double.valueOf(widthAndHeight[0]));
            shape.setHeight(Double.valueOf(widthAndHeight[1]));
        }

        shape.setWrapType(WrapType.NONE);
        shape.setBehindText(true);

        shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        shape.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);

        // 计算图像左侧和顶部位置，使其显示在页面中央。
        // shape.setLeft((builder.getPageSetup().getPageWidth() - shape.getWidth()) / 2);
        //shape.setTop((builder.getPageSetup().getPageHeight() - shape.getHeight()) / 2);

        builder.moveToBookmark(bookmark);
        document.save(targetDoc);
    }
}
