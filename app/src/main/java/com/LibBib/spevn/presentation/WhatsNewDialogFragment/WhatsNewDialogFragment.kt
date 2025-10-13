
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import android.widget.TextView
import androidx.core.net.toUri
import com.LibBib.spevn.R
import com.LibBib.spevn.presentation.MainActivity.Companion.TELEGRAM_URL

class WhatsNewDialogFragment(
) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_whats_new, container, false)

        view.findViewById<TextView>(R.id.exit_tv).setOnClickListener {
            dialog?.dismiss()
        }
            val spanStringGoToTelegram =
                SpannableString(getString(R.string.whats_new_second_block))
            spanStringGoToTelegram.setSpan(
                UnderlineSpan(),
                0,
                spanStringGoToTelegram.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            with(view.findViewById<TextView>(R.id.go_to_telegram_tv)) {
                setText(spanStringGoToTelegram, TextView.BufferType.SPANNABLE)
                setOnClickListener {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        TELEGRAM_URL.toUri()
                    )
                    startActivity(intent)
                }
            }
        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            val params = attributes
            val w = (resources.displayMetrics.widthPixels * 0.85).toInt()
            params.width = w
            attributes = params
        }
    }
}
