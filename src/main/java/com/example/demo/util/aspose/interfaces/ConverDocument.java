package com.example.demo.util.aspose.interfaces;

import java.io.InputStream;

public abstract class ConverDocument extends AbstractDocument {

    //public abstract void docToPdfImpl(String docPath,String pdfPath);
    protected abstract void docToPdfImpl(InputStream inputStream,String docPath, String pdfPath);

   public final void docToPdf(String docPath,String pdfPath){
       //1.获取认证
       getLicenses();
       //转化文档
       docToPdfImpl(null,docPath,pdfPath);
   }

   public final void docToPdf(InputStream inputStream, String pdfPath){
       //1.获取认证
       getLicenses();
       //转化文档
       docToPdfImpl(inputStream,null,pdfPath);
   }


}
