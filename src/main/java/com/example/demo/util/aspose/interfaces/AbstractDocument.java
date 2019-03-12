package com.example.demo.util.aspose.interfaces;

import com.example.demo.util.aspose.util.Licenses;

public abstract class AbstractDocument implements Document {

    // 验证License 若不验证则转化出的pdf文档会有水印产生
    public void getLicenses(){
        if (!Licenses.getLicense()) {
            return;
        }
    }
}
