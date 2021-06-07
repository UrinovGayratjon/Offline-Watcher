package uz.urinov.offlinewatcher.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.urinov.offlinewatcher.R
import uz.urinov.offlinewatcher.ui.viewmodels.SplashViewModel

@AndroidEntryPoint
class SplashScreen : BaseFragment(R.layout.screen_splash) {
    private val TAG = "TTTSplashScreen"

    val viewModel: SplashViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.openMainScreenLiveData.observe(viewLifecycleOwner, openMainScreenObserver)

    }

    private val openMainScreenObserver = Observer<Unit> {
        findNavController().navigate(SplashScreenDirections.actionSplashScreenToMainScreen())
    }
}