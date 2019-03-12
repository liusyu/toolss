package com.example.demo.util.aspose.impl;//////////////////////////////////////////////////////////////////////////

import com.aspose.words.*;
import com.example.demo.util.aspose.entity.Position;
import com.example.demo.util.aspose.interfaces.InsertDocumentAbstract;

import java.util.regex.Pattern;

public  class InsertDocument extends InsertDocumentAbstract
{
    @Override
    protected void insertDocumentAtReplaceImpl(Position position) throws Exception {

        Document mainDoc = new Document(position.getSourceDoc());

        FindReplaceOptions opts = new FindReplaceOptions();
        opts.setDirection(FindReplaceDirection.BACKWARD);
        opts.setReplacingCallback(new InsertDocumentAtReplaceHandler(position));

        mainDoc.getRange().replace(Pattern.compile(position.getKey()), "", opts);
        mainDoc.save(position.getTargetDoc());

    }
    @Override
    /**
     * //String html = "<p>&ldquo;Some Text&rdquo;</p>"; repalceText
     * @param position
     * @throws Exception
     */
    protected void replaceTextImpl(Position position) throws Exception {
        // Initialize a Document.
        Document doc = new Document(position.getSourceDoc());
        // Use a document builder to add content to the document.
        DocumentBuilder builder = new DocumentBuilder(doc);
        FindReplaceOptions findReplaceOptions = new FindReplaceOptions();
        findReplaceOptions.setReplacingCallback(new FindAndInsertHtml());
        findReplaceOptions.setPreserveMetaCharacters(true);
        doc.getRange().replace(position.getKey(), position.getReplaceText(), findReplaceOptions);
        doc.save(position.getTargetDoc());


    }
    private class FindAndInsertHtml implements IReplacingCallback {
        public int replacing(ReplacingArgs e) throws Exception {
            // This is a Run node that contains either the beginning or the complete match.
            Node currentNode = e.getMatchNode();
            // create Document Buidler and insert MergeField
            DocumentBuilder builder = new DocumentBuilder((Document) e.getMatchNode().getDocument());
            builder.moveTo(currentNode);
            builder.insertHtml(e.getReplacement());
            currentNode.remove();
            //Signal to the replace engine to do nothing because we have already done all what we wanted.
            return ReplaceAction.SKIP;
        }
    }
    private static class InsertDocumentAtReplaceHandler implements IReplacingCallback {

        final Position position;
        public InsertDocumentAtReplaceHandler(Position position){
            this.position = position;
        }
        public int replacing(ReplacingArgs e) throws Exception {
            Document subDoc = new Document(position.getInsertDoc());

            // Insert a document after the paragraph, containing the match text.
            Paragraph para = (Paragraph) e.getMatchNode().getParentNode();
            insertDocument(para, subDoc);

            // Remove the paragraph with the match text.
            para.remove();

            return ReplaceAction.SKIP;
        }
    }


    /**
     *在指定节点后插入外部文档的内容。
     *
     * 插入文档的分节符和节格式将被忽略。
     *
     * @param insertAfterNode Node in the destination document after which the content
     *                        should be inserted. This node should be a block level node (paragraph or table).
     * @param srcDoc          要插入的文档。
     */
    private static void insertDocument(Node insertAfterNode, Document srcDoc)
    {
        // Make sure that the node is either a paragraph or table.
        if ((insertAfterNode.getNodeType() != NodeType.PARAGRAPH) & (insertAfterNode.getNodeType() != NodeType.TABLE))
            throw new IllegalArgumentException("The destination node should be either a paragraph or table.");
        // 目标段落的父段中插入。
        CompositeNode dstStory = insertAfterNode.getParentNode();
        // 导入期间"此对象将转换样式和列表。
        NodeImporter importer = new NodeImporter(srcDoc, insertAfterNode.getDocument(), ImportFormatMode.KEEP_SOURCE_FORMATTING);
        // 循环浏览源文档中的所有部分。
        for (Section srcSection : srcDoc.getSections()) {
            //循环浏览该节正文中的所有块级节点(段落和表格(
            for (Node srcNode : srcSection.getBody()) {
                // 如果节点是节中最后一个空段落"那么我们跳过它。
                if (srcNode.getNodeType() == (NodeType.PARAGRAPH)) {
                    Paragraph para = (Paragraph) srcNode;
                    if (para.isEndOfSection() && !para.hasChildNodes()) continue;
                }

                //这将创建一个节点的克隆"适合插入到目标文档中。
                Node newNode = importer.importNode(srcNode, true);

                // 在引用节点后插入新节点。
                dstStory.insertAfter(newNode, insertAfterNode);
                insertAfterNode = newNode;
            }
        }
    }


    /**
     *
     * @param sourceFile D:\\word\\source\\InsertDocument1.doc";"D:\\word\\target\\InsertDocument2.doc";
     * @param targetFile  InsertDocumentAtBookmark.doc
     * @param bookmarks
     */
    public final void insertDocumentAtBookmarkImpl(String [] sourceFile,String targetFile,String bookmarks )
    {
        try {
            if(sourceFile.length != 2) return;
            Document mainDoc = new Document(sourceFile[0] );
            Document subDoc = new Document(sourceFile[1] );
            Bookmark bookmark = mainDoc.getRange().getBookmarks().get(bookmarks);
            insertDocument(bookmark.getBookmarkStart().getParentNode(), subDoc);
            mainDoc.save(targetFile );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

