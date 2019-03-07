package com.yitong.requestframe.download;

/**
 * @Author Daniel Zhang
 * @Time 2019/2/26 22:44
 * @E-Mail zhanggaocheng@yitong.com.cn
 * @Description
 */
public interface DownloadListener {

    void onProgress(long total, long current);

}
