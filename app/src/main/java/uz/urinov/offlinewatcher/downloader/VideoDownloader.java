

package uz.urinov.offlinewatcher.downloader;


import android.content.Context;

import uz.urinov.offlinewatcher.downloader.core.Core;
import uz.urinov.offlinewatcher.downloader.internal.ComponentHolder;
import uz.urinov.offlinewatcher.downloader.internal.DownloadRequestQueue;
import uz.urinov.offlinewatcher.downloader.request.DownloadRequestBuilder;


public class VideoDownloader {

    private VideoDownloader() {

    }

    public static void initialize(Context context) {
        initialize(context, DownloaderConfig.newBuilder().build());
    }


    public static void initialize(Context context, DownloaderConfig config) {
        ComponentHolder.getInstance().init(context, config);
        DownloadRequestQueue.initialize();
    }

    public static DownloadRequestBuilder download(String url, String dirPath, String fileName) {
        return new DownloadRequestBuilder(url, dirPath, fileName);
    }

    public static void pause(int downloadId) {
        DownloadRequestQueue.getInstance().pause(downloadId);
    }

    public static void pauseAll() {
        DownloadRequestQueue.getInstance().pauseAll();
    }

    public static void resume(int downloadId) {
        DownloadRequestQueue.getInstance().resume(downloadId);
    }

    public static void cancel(int downloadId) {
        DownloadRequestQueue.getInstance().cancel(downloadId);
    }

    public static Status getStatus(int downloadId) {

        return DownloadRequestQueue.getInstance().getStatus(downloadId);
    }

    public static void shutDown() {
        Core.shutDown();
    }
}
