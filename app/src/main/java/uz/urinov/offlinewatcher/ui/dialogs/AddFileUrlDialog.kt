package uz.urinov.offlinewatcher.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_add_file_url.*
import uz.urinov.offlinewatcher.R

class AddFileUrlDialog(context: Context) : Dialog(context) {
    private var actionDownload: ((String) -> Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_file_url)


        actionStartDownload.setOnClickListener {
            actionDownload?.invoke(inputUrl.text.toString())
            dismiss()
        }

    }

    fun setAction(actionDownload: ((String) -> Unit)) {
        this.actionDownload = actionDownload
    }

}