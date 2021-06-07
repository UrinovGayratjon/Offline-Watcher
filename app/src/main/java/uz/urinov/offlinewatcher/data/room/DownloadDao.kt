package uz.urinov.offlinewatcher.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DownloadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: DownloadModel)

    @Query("Select * from download_models where id=:id")
    fun find(id: Int): DownloadModel?

    @Query("UPDATE download_models SET downloaded_bytes = :downloadedBytes WHERE id =:id ")
    fun update(id: Int, downloadedBytes: Long)

    @Query("DELETE  FROM download_models where id=:id")
    fun remove(id: Int)

    //    @Query("SELECT * FROM download_models")
//    fun getAllModels(): LiveData<List<DownloadModel>>
//
    @Query("SELECT * FROM download_models")
    suspend fun getAllModels(): List<DownloadModel>

    @Query("DELETE FROM download_models")
    fun clear()

}
