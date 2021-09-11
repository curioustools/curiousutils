package work.curioustools.curiousutils.core_droidjet.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

//todo convert to vb// move to curiousutils
class QuickAdapter<T> (
    private val items:List<T>,
    private val layoutRes : Int,
    private val onBind: (itemView: View, item:T) -> Unit
): RecyclerView.Adapter<QuickAdapter.QuickVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickVH {
        return QuickVH(
            LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuickVH, position: Int) {
        onBind.invoke(holder.itemView,items[position])
    }

    override fun getItemCount() = items.size

    class QuickVH(v: View): RecyclerView.ViewHolder(v)
}