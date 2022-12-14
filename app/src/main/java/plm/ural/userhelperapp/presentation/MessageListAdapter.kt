package plm.ural.userhelperapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import plm.ural.userhelperapp.R
import plm.ural.userhelperapp.domain.MessageItem
//Версия когда мы наследовались от RecycleView
//class MessageListAdapter: RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>() {
class MessageListAdapter: ListAdapter<MessageItem, MessageItemViewHolder>(MessageItemDiffCallback()) {

//Версия когда мы наследовались от RecycleView
//    var messages_list = listOf<MessageItem>()
//        set(value){
//            val callback = MessageListDiffCallback(messages_list,value)
//            val diffResult = DiffUtil.calculateDiff(callback)
//            diffResult.dispatchUpdatesTo(this)
//            field = value
//        }
    var onMessageLongClickListener: ((MessageItem) -> Unit)?=null
    var onMessageClickListener: ((MessageItem) -> Unit)?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageItemViewHolder {

        val layout = when(viewType){
            0->R.layout.message
            1->R.layout.message_disabled
            else -> throw java.lang.RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MessageItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageItemViewHolder, position: Int) {
        val messageItem = getItem(position)
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
        val messageItem = getItem(position)
        return if(messageItem.enabled) 0 else 1

    }

//    override fun getItemCount(): Int {
//        return itemCount
//    }



//    interface OnMessageLongClickListener{
//        fun onMessageLongClick(messageItem: MessageItem)
//    }

    companion object Const{
        const val MAX_POOL_SIZE = 15
    }
}