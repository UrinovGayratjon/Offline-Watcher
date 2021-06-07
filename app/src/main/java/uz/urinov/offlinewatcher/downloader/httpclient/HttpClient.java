

package uz.urinov.offlinewatcher.downloader.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import uz.urinov.offlinewatcher.downloader.request.DownloadRequest;


public interface HttpClient extends Cloneable {

    HttpClient clone();

    void connect(DownloadRequest request) throws IOException;

    int getResponseCode() throws IOException;

    InputStream getInputStream() throws IOException;

    long getContentLength();

    String getResponseHeader(String name);

    void close();

    Map<String, List<String>> getHeaderFields();

    InputStream getErrorStream() throws IOException;

}