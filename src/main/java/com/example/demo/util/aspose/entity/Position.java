package com.example.demo.util.aspose.entity;

/**
 *
 */
public class Position {

    private double width = 50;
    private double height = 50;
    private double left = 25;
    private double right = 25;
    private boolean isCenter;
    private String sourceDoc;
    private String targetDoc;
    private String insertDoc;
    private String key;
    private String replaceText;
    private String url;
    private String bookmark;
    private String imgLocal;
    private String imgUrl;

    public String getReplaceText() {
        return replaceText;
    }

    public void setReplaceText(String replaceText) {
        this.replaceText = replaceText;
    }

    public Position() {
    }

    public Position(String sourceDoc, String targetDoc, String insertDoc, String key) {
        this.sourceDoc = sourceDoc;
        this.targetDoc = targetDoc;
        this.insertDoc = insertDoc;
        this.key = key;
    }

    public Position(String sourceDoc, String targetDoc) {
        this.sourceDoc = sourceDoc;
        this.targetDoc = targetDoc;
    }

    public String getInsertDoc() {
        return insertDoc;
    }

    public void setInsertDoc(String insertDoc) {
        this.insertDoc = insertDoc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImgLocal() {
        return imgLocal;
    }

    public void setImgLocal(String imgLocal) {
        this.imgLocal = imgLocal;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSourceDoc() {
        return sourceDoc;
    }

    public void setSourceDoc(String sourceDoc) {
        this.sourceDoc = sourceDoc;
    }

    public String getTargetDoc() {
        return targetDoc;
    }

    public void setTargetDoc(String targetDoc) {
        this.targetDoc = targetDoc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public boolean isCenter() {
        return isCenter;
    }

    public void setCenter(boolean center) {
        isCenter = center;
    }
}
