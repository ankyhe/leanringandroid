package com.gmail.at.ankyhe.photogallery.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 9/2/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadHanderThread<TokenType> extends HandlerThread {

    private static final String TAG = DownloadHanderThread.class.getName();

    private ConcurrentHashMap<TokenType, String> map = new ConcurrentHashMap<TokenType, String>();

    private static final int MESSAGE_DOWNLOAD = 0;

    private Handler handler;

    private Handler responseHandler;

    public interface DownloadListener<TokenType> {
        void afterDownload(TokenType token, Bitmap thumbnail);
    }

    DownloadListener<TokenType> downloadListener;

    public DownloadHanderThread(Handler aReponseHandler) {
        super(TAG);
        responseHandler = aReponseHandler;
    }

    public void queue(TokenType token, String url) {
        map.put(token, url);
        handler.obtainMessage(MESSAGE_DOWNLOAD, token).sendToTarget();
    }

    public DownloadListener<TokenType> getDownloadListener() {
        return downloadListener;
    }

    public void setDownloadListener(DownloadListener<TokenType> downloadListener) {
        this.downloadListener = downloadListener;
    }


    public void clearQueue() {
        handler.removeMessages(MESSAGE_DOWNLOAD);
        map.clear();
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    TokenType token = (TokenType)msg.obj;
                    handleRequest(token);
                }
            }
        };
    }

    private void handleRequest(final TokenType token) {
        try {
            final String url = map.get(token);
            if (url == null) {
                return;
            }
            byte[] bitmapBytes = new FlickrFetcher().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory
                    .decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            Log.i(TAG, "Bitmap created");

            responseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (map.get(token) != url) {
                        return;
                    }
                    map.remove(token);
                    downloadListener.afterDownload(token, bitmap);
                }
            });


        } catch (IOException ioe) {
            Log.e(TAG, "Error downloading image", ioe);
        }
    }
}
