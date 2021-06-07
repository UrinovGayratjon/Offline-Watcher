package uz.urinov.offlinewatcher.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.screen_main.*
import uz.urinov.offlinewatcher.R
import uz.urinov.offlinewatcher.data.room.DownloadModel
import uz.urinov.offlinewatcher.ui.adapters.DownloadListAdapterTwo
import uz.urinov.offlinewatcher.ui.adapters.invisible
import uz.urinov.offlinewatcher.ui.adapters.visible
import uz.urinov.offlinewatcher.ui.dialogs.AddFileUrlDialog
import uz.urinov.offlinewatcher.ui.viewmodels.MainViewModel


@AndroidEntryPoint
class MainScreen : BaseFragment(R.layout.screen_main) {
    private val TAG = "TTTMainScreen"

    val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: DownloadListAdapterTwo
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.showAddFileUrlDialog.observe(this, showDialogObserver)
        viewModel.showErrorMessage.observe(this, showErrorMessage)
        viewModel.openExoPlayerLiveData.observe(this, openExoPlayerObserver)
        viewModel.itemListLiveData.observe(this, itemListObserver)

        navController = findNavController()

        adapter = DownloadListAdapterTwo()

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        listView.layoutManager = layoutManager
        listView.adapter = adapter
        listView.itemAnimator = null

        adapter.setPlayPauseListener {

        }

        addFileUrl.setOnClickListener {
            viewModel.actionAddFileUrl()
        }
        adapter.setOpenListener {
            viewModel.openExoPlayer(it)
        }
    }

    private val itemListObserver = Observer<List<DownloadModel>> {
        adapter.submitList(it)
        if (it.isEmpty()) {
            emptyView.visible()
        } else {
            emptyView.invisible()
        }
    }
    private val openExoPlayerObserver = Observer<String> {
        navController.navigate(MainScreenDirections.actionToEcoPlayer(it))

    }

    private val showErrorMessage = Observer<String> {
        Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
    }

    private val showDialogObserver = Observer<Unit> {
        val dialog = AddFileUrlDialog(requireContext())
        dialog.setAction {
            viewModel.addFileUrl(it)
        }
        dialog.show()
    }
}