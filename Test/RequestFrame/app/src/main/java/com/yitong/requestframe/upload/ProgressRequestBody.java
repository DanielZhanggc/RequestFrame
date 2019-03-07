package com.yitong.requestframe.upload;

import android.support.annotation.NonNull;

import com.yitong.requestframe.rxjava.UploadObserver;

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
 * @Time 2019/2/28 22:42
 * @E-Mail zhanggaocheng@yitong.com.cn
 * @Description
 */
public class ProgressRequestBody extends RequestBody {

    private RequestBody mRequestBody;
    private UploadObserver<ResponseBody> fileUploadObserver;

    public ProgressRequestBody(File file, UploadObserver<ResponseBody> fileUploadObserver) {
        //image/*  application/octet-stream
        //表明数据包这个part下包含的数据格式[服务端一般对这个没有必传需求，但是Android提供的API必传，即使随便传一个也没事]
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
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        mRequestBody.writeTo(bufferedSink);
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
                fileUploadObserver.onProgress(contentLength(), bytesWritten);
            }
        }
    }
}
