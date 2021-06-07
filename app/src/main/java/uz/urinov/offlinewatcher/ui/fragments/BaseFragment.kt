package uz.urinov.offlinewatcher.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {
    private val TAG = "TTTBaseFragment"
    abstract override fun onViewCreated(view: View, savedInstanceState: Bundle?)
}