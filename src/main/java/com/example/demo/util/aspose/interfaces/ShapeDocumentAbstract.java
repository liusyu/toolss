package com.example.demo.util.aspose.interfaces;

import com.example.demo.util.aspose.entity.Position;
import org.apache.commons.lang.StringUtils;

public abstract class ShapeDocumentAbstract extends AbstractDocument {

    protected abstract void replaceTextImpl(Position position) throws Exception;
    public void replaceImgByKey(Position position){
        if(org.apache.commons.lang3.StringUtils.isBlank(position.getSourceDoc()) ||
                org.apache.commons.lang3.StringUtils.isBlank(position.getTargetDoc())
                || (org.apache.commons.lang3.StringUtils.isBlank(position.getImgLocal()) && StringUtils.isBlank(position.getImgUrl()))
                || org.apache.commons.lang3.StringUtils.isBlank(position.getKey()))
            return;
        getLicenses();
        try {
            replaceTextImpl(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 页面中间插入浮动图像
     * 在书签的位置插入浮动图像默认在书签位置居中
     */
    protected abstract void insertImgToBookmarkImpl( Position position) throws Exception;
    public void insertImgToBookmark( Position position){
        if(validate(position) || StringUtils.isBlank(position.getBookmark()) || (StringUtils.isBlank(position.getImgLocal()) && StringUtils.isBlank(position.getImgUrl())))
            return;
        try {
            getLicenses();
            insertImgToBookmarkImpl(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 本地图片作为水印
     * @param imgUrl
     * @param sourceDoc
     * @param targetDoc
     * @param bookmark
     */
    public void insertWaterToBook(String imgUrl,String sourceDoc,String targetDoc,String bookmark) {
        getLicenses();
        insertWaterImpl(imgUrl,sourceDoc,targetDoc,bookmark);
    }
    /**
     * 本地图片作为水印中间
     * @param imgUrl
     * @param sourceDoc
     * @param targetDoc
     *
     */
    public void insertWaterToCenter(String imgUrl,String sourceDoc,String targetDoc,String[] widthAndHeight) {
        getLicenses();
        try {
            insertWaterToCenterImpl(imgUrl,sourceDoc,targetDoc,widthAndHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 在指定位置签章
     * @param imgUrl
     * @param sourceDoc
     * @param targetDoc
     * @param bookmark
     * @param widthAndHeight
     */
    public void insertWaterToBook(String imgUrl,String sourceDoc,String targetDoc,String bookmark,String[] widthAndHeight) {
        getLicenses();
        try {
            insertWaterImpl(imgUrl,sourceDoc,targetDoc,bookmark,widthAndHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validate(Position position){
        if(StringUtils.isBlank(position.getSourceDoc()) || StringUtils.isBlank(position.getTargetDoc())){
            return true;
        }
        return false;
    }















    protected abstract void insertWaterToCenterImpl(String imgUrl,String sourceDoc,String targetDoc,String[] widthAndHeight) throws Exception;

    protected abstract void insertWaterImpl(String imgUrl,String sourceDoc,String targetDoc,String bookmark);

    protected abstract void insertWaterImpl(String imgUrl,String sourceDoc,String targetDoc,String bookmark,String[] widthAndHeight) throws Exception ;
}
