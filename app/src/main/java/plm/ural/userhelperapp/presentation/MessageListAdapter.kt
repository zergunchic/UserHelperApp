package plm.ural.userhelperapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import plm.ural.userhelperapp.R
import plm.ural.userhelperapp.domain.MessageItem

class MessageListAdapter: RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>() {



    var messages_list = listOf<MessageItem>()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    var onMessageLongClickListener: ((MessageItem) -> Unit)?=null
    var onMessageClickListener: ((MessageItem) -> Unit)?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val layout = when(viewType){
            0->R.layout.message
            1->R.layout.message_disabled
            else -> throw java.lang.RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val messageItem = messages_list[position]
        holder.tvMessage.text = messageItem.userMessage
        holder.tvName.text = messageItem.userName

        holder.view.setOnLongClickListener{
            //invoke позволяет вызывать нулабельные функции
           onMessageLongClickListener?.invoke(messageItem)
           true
        }

        holder.view.setOnClickListener{
            onMessageClickListener?.invoke(messageItem)
        }
    }
//Определяет какой вид использовать для элементов списка
    override fun getItemViewType(position: Int): Int {
        val messageItem = messages_list[position]
        return if(messageItem.enabled) 0 else 1

    }

    override fun getItemCount(): Int {
        return messages_list.size
    }

    class MessageViewHolder(val view:View): RecyclerView.ViewHolder(view){
        val tvMessage = view.findViewById<TextView>(R.id.textView)
        val tvName = view.findViewById<TextView>(R.id.textView2)
    }

    interface OnMessageLongClickListener{
        fun onMessageLongClick(messageItem: MessageItem)
    }

    companion object Const{
        const val MAX_POOL_SIZE = 15
    }
}