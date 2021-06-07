package uz.urinov.offlinewatcher.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.download_item_view.view.*
import uz.urinov.offlinewatcher.R
import uz.urinov.offlinewatcher.data.room.AppDataBase
import uz.urinov.offlinewatcher.data.room.DownloadModel
import uz.urinov.offlinewatcher.downloader.Error
import uz.urinov.offlinewatcher.downloader.OnDownloadListener
import uz.urinov.offlinewatcher.downloader.Status
import uz.urinov.offlinewatcher.downloader.VideoDownloader
import uz.urinov.offlinewatcher.ui.fragments.Utils

class DownloadListAdapterTwo :
    ListAdapter<DownloadModel, DownloadListAdapterTwo.ViewHolder>(ItemDiffUtil) {
    private val TAG = "TTTDownloadListAdapter"

    private var openExoPlayer: ((DownloadModel?) -> Unit)? = null
    private var playPauseListener: ((DownloadModel) -> Unit)? = null

    object ItemDiffUtil : DiffUtil.ItemCallback<DownloadModel>() {
        override fun areItemsTheSame(oldItem: DownloadModel, newItem: DownloadModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DownloadModel, newItem: DownloadModel): Boolean {

            return oldItem.downloadedBytes == newItem.downloadedBytes
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.cancelButton.setOnClickListener {
                val model = getItem(adapterPosition)
                cancelDownload(model)
            }

            itemView.playPauseButton.setOnClickListener {
                val model = getItem(adapterPosition)
                playPauseListener?.invoke(model)
                val status = VideoDownloader.getStatus(model.id)

                when (status) {
                    Status.PAUSED -> {
                        itemView.playPauseButton.setImageResource(R.drawable.ic_baseline_pause_24)
                        VideoDownloader.resume(model.id)
                    }
                    Status.RUNNING -> {
                        itemView.playPauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                        VideoDownloader.pause(model.id)
                    }
                    Status.UNKNOWN -> {
                        itemView.playPauseButton.setImageResource(R.drawable.ic_baseline_pause_24)
                        VideoDownloader.download(model.url, model.dirPath, model.fileName)
                            .build()
                            .start(object :
                                OnDownloadListener {
                                override fun onDownloadComplete() {

                                }

                                override fun onError(error: Error?) {

                                }

                            })
                    }
                }
            }

            itemView.setOnClickListener {
                openExoPlayer?.invoke(
                    AppDataBase.dataBase.downloadDao().find(getItem(adapterPosition).id)
                )
            }

        }

        fun bind(model: DownloadModel) {
            val status = VideoDownloader.getStatus(model.id)

            when (status) {
                Status.PAUSED -> {
                    itemView.playPauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                }
                Status.RUNNING -> {
                    itemView.playPauseButton.setImageResource(R.drawable.ic_baseline_pause_24)
                }
                else -> {
                    itemView.playPauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                }
            }

            if (model.downloadedBytes == model.totalBytes) {
                itemView.playPauseButton.invisible()
                itemView.cancelButton.setImageResource(R.drawable.ic_baseline_delete_24)
            } else {
                itemView.playPauseButton.visible()
                itemView.cancelButton.setImageResource(R.drawable.ic_baseline_stop_24)
            }
            itemView.fileName.text = model.fileName
            val text = Utils.getProgressPercentage(model.downloadedBytes, model.totalBytes)
            itemView.textProgress.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.download_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOpenListener(listener: (DownloadModel?) -> Unit) {
        openExoPlayer = listener
    }

    fun setPlayPauseListener(listener: (DownloadModel) -> Unit) {
        playPauseListener = listener
    }
}