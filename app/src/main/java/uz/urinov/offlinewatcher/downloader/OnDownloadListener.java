package uz.urinov.offlinewatcher.downloader;

public interface OnDownloadListener {

    void onDownloadComplete();

    void onError(Error error);

}
