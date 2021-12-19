package cn.cnlee.demo.databindingrecyclerview.ui;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/12/19
 * @Version 1.0
 */
public enum State {
    NOT_ACTIVE(0, "待激活"),       // 盲盒形象才有此状态 1，非游客，未激活 2，游客
    IN_USE(1, "使用中"),           // 正在使用
    SWITCHING(2, "切换中"),        // 正在切换中
    DOWNLOADED(3, "使用"),         // 盲盒形象，激活并下载完成
    NOT_DOWNLOADED(4, "下载"),     // 盲盒形象，已激活未下载
    WAIT_DOWNLOAD(5, "等待下载"),   // 等待下载，已点击下载但还没进度
    DOWNLOADING(6, "暂停下载"),     // 带进度条，开始下载，有进度出现时
    PAUSE_DOWNLOAD(7, "继续下载");  // 带进度条，暂停下载时

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    State(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
