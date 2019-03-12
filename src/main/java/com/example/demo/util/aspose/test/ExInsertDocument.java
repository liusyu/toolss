//package com.example.demo.common.word.aspose.impl;//////////////////////////////////////////////////////////////////////////
//// Copyright 2001-2018 Aspose Pty Ltd. All Rights Reserved.
////
//// This file is part of Aspose.Words. The source code in this file
//// is only intended as a supplement to the documentation, and is provided
//// "as is", without warranty of any kind, either expressed or implied.
////////////////////////////////////////////////////////////////////////////
//
//import com.aspose.words.*;
//
//import java.io.ByteArrayInputStream;
//import java.util.regex.Pattern;
//
//public class ExInsertDocument //extends ApiExampleBase
//{
//
//
//    /**
//     * Inserts content of the external document after the specified node.
//     * Section breaks and section formatting of the inserted document are ignored.
//     *
//     * @param insertAfterNode Node in the destination document after which the content
//     *                        should be inserted. This node should be a block level node (paragraph or table).
//     * @param srcDoc          The document to insert.
//     */
//    public static void insertDocument(Node insertAfterNode, Document srcDoc)
//    {
//        // Make sure that the node is either a paragraph or table.
//        if ((insertAfterNode.getNodeType() != NodeType.PARAGRAPH) & (insertAfterNode.getNodeType() != NodeType.TABLE))
//            throw new IllegalArgumentException("The destination node should be either a paragraph or table.");
//
//        // 目标段落的父段中插入。
//        CompositeNode dstStory = insertAfterNode.getParentNode();
//
//        // 导入期间"此对象将转换样式和列表。
//        NodeImporter importer = new NodeImporter(srcDoc, insertAfterNode.getDocument(), ImportFormatMode.KEEP_SOURCE_FORMATTING);
//
//        // 循环浏览源文档中的所有部分。
//        for (Section srcSection : srcDoc.getSections())
//        {
//            //循环浏览该节正文中的所有块级节点(段落和表格(
//            for (Node srcNode : srcSection.getBody())
//            {
//                // 如果节点是节中最后一个空段落"那么我们跳过它。
//                if (srcNode.getNodeType() == (NodeType.PARAGRAPH))
//                {
//                    Paragraph para = (Paragraph) srcNode;
//                    if (para.isEndOfSection() && !para.hasChildNodes()) continue;
//                }
//
//                //这将创建一个节点的克隆"适合插入到目标文档中。
//                Node newNode = importer.importNode(srcNode, true);
//
//                // 在引用节点后插入新节点。
//                dstStory.insertAfter(newNode, insertAfterNode);
//                insertAfterNode = newNode;
//            }
//        }
//    }
//    //ExEnd
//
//    public static void main(String[] args) throws Exception {
//        ExInsertDocument document = new ExInsertDocument();
//        document.insertDocumentAtBookmark();
//    }
//   public static final String sourceFile = "D:\\word\\source\\";
//    public static final String targetFile = "D:\\word\\target\\";
//    public void insertDocumentAtBookmark() throws Exception
//    {
//
//        Document mainDoc = new Document(sourceFile + "InsertDocument1.doc");
//        Document subDoc = new Document(sourceFile + "InsertDocument2.doc");
//
//        Bookmark bookmark = mainDoc.getRange().getBookmarks().get("insertionPlace");
//        insertDocument(bookmark.getBookmarkStart().getParentNode(), subDoc);
//
//        mainDoc.save(targetFile + "InsertDocumentAtBookmark.doc");
//        //ExEnd
//    }
//
//
//    public void insertDocumentAtMailMergeCaller() throws Exception
//    {
//        insertDocumentAtMailMerge();
//    }
//
//    //ExStart
//    //ExFor:CompositeNode.HasChildNodes
//    //ExId:InsertDocumentAtMailMerge
//    //ExSummary:Demonstrates how to use the InsertDocument method to insert a document into a merge field during mail merge.
//    public void insertDocumentAtMailMerge() throws Exception
//    {
//        // Open the main document.
//        Document mainDoc = new Document(sourceFile + "InsertDocument1.doc");
//
//        // Add a handler to MergeField event
//        mainDoc.getMailMerge().setFieldMergingCallback(new InsertDocumentAtMailMergeHandler());
//
//        // The main document has a merge field in it called "Document_1".
//        // The corresponding data for this field contains fully qualified path to the document
//        // that should be inserted to this field.
//        mainDoc.getMailMerge().execute(new String[]{"Document_1"}, new String[]{sourceFile + "InsertDocument2.doc"});
//
//        mainDoc.save(targetFile + "InsertDocumentAtMailMerge.doc");
//    }
//
//    private class InsertDocumentAtMailMergeHandler implements IFieldMergingCallback
//    {
//        /**
//         * This handler makes special processing for the "Document_1" field.
//         * The field value contains the path to load the document.
//         * We load the document and insert it into the current merge field.
//         */
//        public void fieldMerging(FieldMergingArgs e) throws Exception
//        {
//            if ("Document_1".equals(e.getDocumentFieldName()))
//            {
//                // Use document builder to navigate to the merge field with the specified name.
//                DocumentBuilder builder = new DocumentBuilder(e.getDocument());
//                builder.moveToMergeField(e.getDocumentFieldName());
//
//                // The name of the document to load and insert is stored in the field value.
//                Document subDoc = new Document((String) e.getFieldValue());
//
//                // Insert the document.
//                insertDocument(builder.getCurrentParagraph(), subDoc);
//
//                // The paragraph that contained the merge field might be empty now and you probably want to delete it.
//                if (!builder.getCurrentParagraph().hasChildNodes()) builder.getCurrentParagraph().remove();
//
//                // Indicate to the mail merge engine that we have inserted what we wanted.
//                e.setText(null);
//            }
//        }
//
//        public void imageFieldMerging(ImageFieldMergingArgs args)
//        {
//            // Do nothing.
//        }
//    }
//    //ExEnd
//
//    //ExStart
//    //ExId:InsertDocumentAtMailMergeBlob
//    //ExSummary:A slight variation to the above example to load a document from a BLOB database field instead of a file.
//    private class InsertDocumentAtMailMergeBlobHandler implements IFieldMergingCallback
//    {
//        /**
//         * This handler makes special processing for the "Document_1" field.
//         * The field value contains the path to load the document.
//         * We load the document and insert it into the current merge field.
//         */
//        public void fieldMerging(FieldMergingArgs e) throws Exception
//        {
//            if ("Document_1".equals(e.getDocumentFieldName()))
//            {
//                // Use document builder to navigate to the merge field with the specified name.
//                DocumentBuilder builder = new DocumentBuilder(e.getDocument());
//                builder.moveToMergeField(e.getDocumentFieldName());
//
//                // Load the document from the blob field.
//                ByteArrayInputStream inStream = new ByteArrayInputStream((byte[]) e.getFieldValue());
//                Document subDoc = new Document(inStream);
//                inStream.close();
//
//                // Insert the document.
//                insertDocument(builder.getCurrentParagraph(), subDoc);
//
//                // The paragraph that contained the merge field might be empty now and you probably want to delete it.
//                if (!builder.getCurrentParagraph().hasChildNodes()) builder.getCurrentParagraph().remove();
//
//                // Indicate to the mail merge engine that we have inserted what we wanted.
//                e.setText(null);
//            }
//        }
//
//        public void imageFieldMerging(ImageFieldMergingArgs args)
//        {
//            // Do nothing.
//        }
//    }
//    //ExEnd
//
//
//    public void insertDocumentAtReplaceCaller() throws Exception
//    {
//        insertDocumentAtReplace();
//    }
//
//    //ExStart
//    //ExFor:Range.Replace(Regex, String, FindReplaceOptions)
//    //ExFor:IReplacingCallback
//    //ExFor:ReplaceAction
//    //ExFor:IReplacingCallback.Replacing
//    //ExFor:ReplacingArgs
//    //ExFor:ReplacingArgs.MatchNode
//    //ExId:InsertDocumentAtReplace
//    //ExSummary:Shows how to insert content of one document into another during a customized find and replace operation.
//    public void insertDocumentAtReplace() throws Exception
//    {
//        Document mainDoc = new Document(sourceFile + "InsertDocument1.doc");
//
//        FindReplaceOptions options = new FindReplaceOptions();
//        options.setDirection(FindReplaceDirection.BACKWARD);
//        options.setReplacingCallback(new InsertDocumentAtReplaceHandler());
//
//        mainDoc.getRange().replace(Pattern.compile("\\[MY_DOCUMENT\\]"), "", options);
//        mainDoc.save(targetFile + "InsertDocumentAtReplace.doc");
//    }
//
//    private class InsertDocumentAtReplaceHandler implements IReplacingCallback
//    {
//        public int replacing(ReplacingArgs e) throws Exception
//        {
//            Document subDoc = new Document(sourceFile + "InsertDocument2.doc");
//
//            // Insert a document after the paragraph, containing the match text.
//            Paragraph para = (Paragraph) e.getMatchNode().getParentNode();
//            insertDocument(para, subDoc);
//
//            // Remove the paragraph with the match text.
//            para.remove();
//
//            return ReplaceAction.SKIP;
//        }
//    }
//    //ExEnd
//}
//
