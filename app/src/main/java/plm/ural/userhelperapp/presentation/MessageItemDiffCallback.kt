package plm.ural.userhelperapp.presentation

import androidx.recyclerview.widget.DiffUtil
import plm.ural.userhelperapp.domain.MessageItem

class MessageItemDiffCallback : DiffUtil.ItemCallback<MessageItem>() {
    override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem==newItem
    }

}