package com.example.demo.util.aspose.interfaces;//////////////////////////////////////////////////////////////////////////

import com.example.demo.util.aspose.entity.Position;
import org.apache.commons.lang3.StringUtils;

public abstract class InsertDocumentAbstract extends AbstractDocument
{
    protected abstract void insertDocumentAtBookmarkImpl(String [] sourceFile,String targetFile,String bookmarks );

    protected abstract void insertDocumentAtReplaceImpl(Position position) throws Exception ;

    protected abstract void replaceTextImpl(Position position) throws Exception;

    public void replaceText(Position position){
        if(StringUtils.isBlank(position.getSourceDoc()) ||
                StringUtils.isBlank(position.getTargetDoc())
                ||StringUtils.isBlank(position.getReplaceText())
                ||StringUtils.isBlank(position.getKey()))
            return;
        try {
            getLicenses();
            replaceTextImpl(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *将文档1插入到文档2.
     * @param sourceFile D:\\word\\source\\InsertDocument1.doc";"D:\\word\\target\\InsertDocument2.doc";
     * @param targetFile  InsertDocumentAtBookmark.doc
     * @param bookmarks
     */
    public void insertDocumentAtBookmark(String [] sourceFile,String targetFile,String bookmarks )
    {
        getLicenses();
        insertDocumentAtBookmarkImpl(sourceFile,targetFile,bookmarks);
    }
    public void insertDocumentAtReplace(Position position){
        if(StringUtils.isBlank(position.getSourceDoc()) ||
                StringUtils.isBlank(position.getTargetDoc())
                ||StringUtils.isBlank(position.getInsertDoc())
                ||StringUtils.isBlank(position.getKey()))
            return;
        try {
            getLicenses();
            insertDocumentAtReplaceImpl(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

