package uz.urinov.offlinewatcher.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "download_models")
data class DownloadModel(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "dir_path")
    val dirPath: String,
    @ColumnInfo(name = "file_name")
    val fileName: String,
    @ColumnInfo(name = "total_bytes")
    var totalBytes: Long,
    @ColumnInfo(name = "downloaded_bytes")
    var downloadedBytes: Long,
)