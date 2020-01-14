package com.jeeplus.modules.esign.bean.signflow;

/**
 * 流程文档添加参数
 */
public class FlowAddFile {
    private int encryption; //是否加密，0-不加密，1-加，默认0
    private String fileId;  // 文档id
    private String fileName; // 文档名称,默认文件名称
    private String filePassword; //文档密码, 如果encryption值为1, 文档密码不能为空，默认没有密码

    public int getEncryption() {
        return encryption;
    }

    public void setEncryption(int encryption) {
        this.encryption = encryption;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePassword() {
        return filePassword;
    }

    public void setFilePassword(String filePassword) {
        this.filePassword = filePassword;
    }
}
