package plm.ural.userhelperapp.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import plm.ural.userhelperapp.R

class MessageItemViewHolder(val view: View): RecyclerView.ViewHolder(view){
    val tvMessage = view.findViewById<TextView>(R.id.textView)
    val tvName = view.findViewById<TextView>(R.id.textView2)
}