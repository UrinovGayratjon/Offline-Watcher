

package uz.urinov.offlinewatcher.downloader.internal;


import uz.urinov.offlinewatcher.downloader.Error;
import uz.urinov.offlinewatcher.downloader.Priority;
import uz.urinov.offlinewatcher.downloader.Response;
import uz.urinov.offlinewatcher.downloader.Status;
import uz.urinov.offlinewatcher.downloader.request.DownloadRequest;

public class DownloadRunnable implements Runnable {

    public final Priority priority;
    public final int sequence;
    public final DownloadRequest request;

    DownloadRunnable(DownloadRequest request) {
        this.request = request;
        this.priority = request.getPriority();
        this.sequence = request.getSequenceNumber();
    }

    @Override
    public void run() {
        request.setStatus(Status.RUNNING);
        DownloadTask downloadTask = DownloadTask.create(request);
        Response response = downloadTask.run();

        if (response.isSuccessful()) {

            request.deliverSuccess();

        } else if (response.isPaused()) {


        } else if (response.getError() != null) {

            request.deliverError(response.getError());

        } else if (!response.isCancelled()) {

            request.deliverError(new Error());

        }
    }

}
