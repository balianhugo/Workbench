package dev.gavin.wb.util;

import org.apache.commons.fileupload.ProgressListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

/**
 * 定义文件上传监听器更新上传进度
 * Created by Administrator on 2017/4/12.
 */
public class FileProgressListen implements ProgressListener {

    private HttpSession session;

    public FileProgressListen(HttpSession session) {
        this.session = session;
        session.setAttribute("progressStatus", new ProgressInfo());
    }

    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        ProgressInfo status = (ProgressInfo) session.getAttribute("progressStatus");
        status.setBytesRead(pBytesRead);
        status.setContentLength(pContentLength);
        status.setItems(pItems);
        session.setAttribute("progressStatus", status);
    }
}
