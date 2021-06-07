

package uz.urinov.offlinewatcher.downloader.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import uz.urinov.offlinewatcher.downloader.Status;
import uz.urinov.offlinewatcher.downloader.core.Core;
import uz.urinov.offlinewatcher.downloader.request.DownloadRequest;


public class DownloadRequestQueue {

    private static DownloadRequestQueue instance;
    private final Map<Integer, DownloadRequest> currentRequestMap;
    private final AtomicInteger sequenceGenerator;

    private DownloadRequestQueue() {
        currentRequestMap = new ConcurrentHashMap<>();
        sequenceGenerator = new AtomicInteger();
    }

    public static void initialize() {
        getInstance();
    }

    public static DownloadRequestQueue getInstance() {
        if (instance == null) {
            synchronized (DownloadRequestQueue.class) {
                if (instance == null) {
                    instance = new DownloadRequestQueue();
                }
            }
        }
        return instance;
    }

    private int getSequenceNumber() {
        return sequenceGenerator.incrementAndGet();
    }

    public void pause(int downloadId) {
        DownloadRequest request = currentRequestMap.get(downloadId);
        if (request != null) {
            request.setStatus(Status.PAUSED);
        }
    }

    public void pauseAll() {
        for (Integer downloadId : currentRequestMap.keySet()) {
            DownloadRequest request = currentRequestMap.get(downloadId);
            if (request != null) {
                request.setStatus(Status.PAUSED);
            }
        }
    }

    public void resume(int downloadId) {
        DownloadRequest request = currentRequestMap.get(downloadId);
        if (request != null) {
            request.setStatus(Status.QUEUED);
            request.setFuture(Core.getInstance()
                    .getExecutorSupplier()
                    .forDownloadTasks()
                    .submit(new DownloadRunnable(request)));
        }
    }

    private void cancelAndRemoveFromMap(DownloadRequest request) {
        if (request != null) {
            request.cancel();
            currentRequestMap.remove(request.getDownloadId());
        }
    }

    public void cancel(int downloadId) {
        DownloadRequest request = currentRequestMap.get(downloadId);
        cancelAndRemoveFromMap(request);
    }


    public Status getStatus(int downloadId) {
        DownloadRequest request = currentRequestMap.get(downloadId);
        if (request != null) {
            return request.getStatus();
        }
        return Status.UNKNOWN;
    }

    public void addRequest(DownloadRequest request) {
        currentRequestMap.put(request.getDownloadId(), request);
        request.setStatus(Status.QUEUED);
        request.setSequenceNumber(getSequenceNumber());
        request.setFuture(Core.getInstance()
                .getExecutorSupplier()
                .forDownloadTasks()
                .submit(new DownloadRunnable(request)));
    }

    public void finish(DownloadRequest request) {
        currentRequestMap.remove(request.getDownloadId());
    }
}
