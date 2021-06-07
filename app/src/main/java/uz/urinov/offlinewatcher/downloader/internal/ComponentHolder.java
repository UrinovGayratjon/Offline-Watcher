

package uz.urinov.offlinewatcher.downloader.internal;

import android.content.Context;

import uz.urinov.offlinewatcher.data.room.AppDataBase;
import uz.urinov.offlinewatcher.data.room.DownloadDao;
import uz.urinov.offlinewatcher.downloader.Constants;
import uz.urinov.offlinewatcher.downloader.DownloaderConfig;
import uz.urinov.offlinewatcher.downloader.httpclient.DefaultHttpClient;
import uz.urinov.offlinewatcher.downloader.httpclient.HttpClient;


public class ComponentHolder {

    private final static ComponentHolder INSTANCE = new ComponentHolder();
    private int readTimeout;
    private int connectTimeout;
    private String userAgent;
    private HttpClient httpClient;
    private DownloadDao downloadDao;

    public static ComponentHolder getInstance() {
        return INSTANCE;
    }

    public void init(Context context, DownloaderConfig config) {
        this.readTimeout = config.getReadTimeout();
        this.connectTimeout = config.getConnectTimeout();
        this.userAgent = config.getUserAgent();
        this.httpClient = config.getHttpClient();

        this.downloadDao = AppDataBase.dataBase.downloadDao();
    }

    public int getReadTimeout() {
        if (readTimeout == 0) {
            synchronized (ComponentHolder.class) {
                if (readTimeout == 0) {
                    readTimeout = Constants.DEFAULT_READ_TIMEOUT_IN_MILLS;
                }
            }
        }
        return readTimeout;
    }

    public int getConnectTimeout() {
        if (connectTimeout == 0) {
            synchronized (ComponentHolder.class) {
                if (connectTimeout == 0) {
                    connectTimeout = Constants.DEFAULT_CONNECT_TIMEOUT_IN_MILLS;
                }
            }
        }
        return connectTimeout;
    }

    public String getUserAgent() {
        if (userAgent == null) {
            synchronized (ComponentHolder.class) {
                if (userAgent == null) {
                    userAgent = Constants.DEFAULT_USER_AGENT;
                }
            }
        }
        return userAgent;
    }

    public DownloadDao getDbHelper() {

        synchronized (ComponentHolder.class) {
            if (downloadDao == null) {
                downloadDao = AppDataBase.dataBase.downloadDao();
            }
        }
        return downloadDao;
    }

    public HttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (ComponentHolder.class) {
                if (httpClient == null) {
                    httpClient = new DefaultHttpClient();
                }
            }
        }
        return httpClient.clone();
    }

}
