package com.example.taross.view

/**
 * Created by taross on 2017/11/05.
 */
import android.app.Dialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.View
import com.example.taross.jinkawa_android.R


class CustomBottomSheetDialog : BottomSheetDialogFragment() {

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val view = View.inflate(context, R.layout.layout_buttom_sheet, null)
        dialog.setContentView(view)

        //ここにButtonの処理など
    }

    companion object {

        fun newInstance(): CustomBottomSheetDialog {
            return CustomBottomSheetDialog()
        }
    }
}