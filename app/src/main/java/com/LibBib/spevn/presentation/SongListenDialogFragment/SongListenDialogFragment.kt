package com.LibBib.spevn.presentation.SongListenDialogFragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.LibBib.spevn.R

class SongListenDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.song_listen_dialog, container, false)

        view.findViewById<ImageView>(R.id.close_dialog_btn).setOnClickListener {
            dialog?.dismiss()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setGravity(Gravity.BOTTOM)
            setBackgroundDrawableResource(android.R.color.transparent)
            val params = attributes
            val w = (resources.displayMetrics.widthPixels)
            params.width = w
            attributes = params
        }
    }
}
