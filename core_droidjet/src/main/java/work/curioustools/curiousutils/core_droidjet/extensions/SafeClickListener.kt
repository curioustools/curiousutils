package work.curioustools.curiousutils.core_droidjet.extensions

import android.os.SystemClock
import android.view.View

class SafeClickListener(
    private var defaultInterval: Int,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}