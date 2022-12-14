package plm.ural.userhelperapp.presentation

import androidx.recyclerview.widget.DiffUtil
import plm.ural.userhelperapp.domain.MessageItem

class MessageListDiffCallback(
    private val oldList:List<MessageItem>,
    private val newList:List<MessageItem>
):DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMessage = oldList[oldItemPosition]
        val newMessage = newList[newItemPosition]
        return oldMessage == newMessage
    }
}