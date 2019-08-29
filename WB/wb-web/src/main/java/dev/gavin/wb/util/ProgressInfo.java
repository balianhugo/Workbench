package dev.gavin.wb.util;

/**
 * 文件上传进度信息
 * Created by Administrator on 2017/4/12.
 */
public class ProgressInfo {

    /**
     * 已读字byte
     **/
    private long bytesRead = 0L;

    /**
     * 总长度
     **/
    private long contentLength = 0L;

    /**
     * 第几个文件游标
     **/
    private int items;

    public long getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

}
