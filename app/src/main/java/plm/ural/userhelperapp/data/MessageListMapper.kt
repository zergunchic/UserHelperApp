package plm.ural.userhelperapp.data

import plm.ural.userhelperapp.domain.MessageItem

class MessageListMapper {

    fun mapEntityToDbModel(messageItem: MessageItem):MessageItemDBModel{
        return MessageItemDBModel(
            id = messageItem.id,
            userName = messageItem.userName,
            userMessage = messageItem.userMessage,
            enabled = messageItem.enabled
        )
    }

    fun mapDBModelToEntity(messageItemDB: MessageItemDBModel) = MessageItem(
            id = messageItemDB.id,
            userName = messageItemDB.userName,
            userMessage = messageItemDB.userMessage,
            enabled = messageItemDB.enabled
    )

    fun mapListDbModelToListEntity(list:List<MessageItemDBModel>) = list.map{
        mapDBModelToEntity(it)
    }
}