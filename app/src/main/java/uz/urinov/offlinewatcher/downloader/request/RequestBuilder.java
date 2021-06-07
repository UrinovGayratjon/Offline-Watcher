

package uz.urinov.offlinewatcher.downloader.request;


import uz.urinov.offlinewatcher.downloader.Priority;

public interface RequestBuilder {

    RequestBuilder setHeader(String name, String value);

    RequestBuilder setPriority(Priority priority);

    RequestBuilder setReadTimeout(int readTimeout);

    RequestBuilder setConnectTimeout(int connectTimeout);

    RequestBuilder setUserAgent(String userAgent);

}
