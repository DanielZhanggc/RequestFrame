package com.yitong.requestframe.request.requestbody;

import android.support.annotation.NonNull;

import com.yitong.requestframe.rxjava.FileUploadObserver;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * @Author Daniel Zhang
 * @Time 2018/7/30 15:35
 * @E-Mail zhanggc@yitong.com.cn
 * @Description 上传进度
 */
public class UploadFileRequestBody extends RequestBody {

    private RequestBody mRequestBody;
    private FileUploadObserver<ResponseBody> fileUploadObserver;

    public UploadFileRequestBody(File file, FileUploadObserver<ResponseBody> fileUploadObserver) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        this.fileUploadObserver = fileUploadObserver;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {

        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink); //写入
        mRequestBody.writeTo(bufferedSink); //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink {
        private long bytesWritten = 0;

        CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(@NonNull Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            if (fileUploadObserver != null) {
                fileUploadObserver.onProgressChange(bytesWritten, contentLength());
            }
        }
    }

}

