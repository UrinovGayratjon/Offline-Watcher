package uz.urinov.offlinewatcher.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.screen_video_view.*
import uz.urinov.offlinewatcher.R
import uz.urinov.offlinewatcher.ui.viewmodels.VideoViewModel

@AndroidEntryPoint
class VideoViewScreen : BaseFragment(R.layout.screen_video_view) {
    private val TAG = "TTTVideoViewScreen"
    val viewModel: VideoViewModel by viewModels()
    lateinit var exoPlayer: SimpleExoPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        exoPlayer = SimpleExoPlayer.Builder(requireContext()).build()
        playerView.player = exoPlayer
        arguments?.getString("videoPath")?.let {
            val mediaItem: MediaItem = MediaItem.fromUri(Uri.parse(it))
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.play()
            Log.d(TAG, "onViewCreated: " + it)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer.stop()

    }
}