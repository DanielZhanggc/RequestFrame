package com.yitong.requestframe.download;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * @Author Daniel Zhang
 * @Time 2019/2/26 22:39
 * @E-Mail zhanggaocheng@yitong.com.cn
 * @Description
 */
public class DownloadResponseBody extends ResponseBody {

    private ResponseBody responseBody;
    private BufferedSource bufferedSource;
    private DownloadListener downloadListener;

    public DownloadResponseBody(ResponseBody responseBody, DownloadListener listener) {
        this.responseBody = responseBody;
        this.downloadListener = listener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(getSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source getSource(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (null != downloadListener) {
                    if (bytesRead != -1) {
                        downloadListener.onProgress(responseBody.contentLength(), (int) (totalBytesRead));
                    }
                }
                return bytesRead;
            }
        };
    }
}
