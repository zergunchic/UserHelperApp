package plm.ural.userhelperapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import plm.ural.userhelperapp.data.MessageListRepositoryImpl
import plm.ural.userhelperapp.domain.MessageItem
import plm.ural.userhelperapp.domain.MessageItemController

class MessageItemViewModel: ViewModel() {

    private val repository = MessageListRepositoryImpl
    private val messageItemController = MessageItemController(repository)
    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName:LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputMessage = MutableLiveData<Boolean>()
    val errorInputMessage:LiveData<Boolean>
        get() = _errorInputMessage

    private val _messageLiveData = MutableLiveData<MessageItem>()
    val messageLiveData:LiveData<MessageItem>
        get() = _messageLiveData

    fun getMessageItem(MessageItemId: Int){
        val item = messageItemController.getMessageItem(MessageItemId)
    }

    fun addMessageItem(inputMessage:String?,inputName:String?){
        val nameIn = parseName(inputName)
        val messageIn = parseName(inputMessage)
        val fildesIsValid = validateInput(nameIn,messageIn)
        if(fildesIsValid) {
            val message = MessageItem(nameIn, messageIn, true)
            messageItemController.addMessageItem(message)
        }
    }

    fun editMessageItem(inputMessage:String?,inputName:String?){
        val nameIn = parseName(inputName)
        val messageIn = parseName(inputMessage)
        val fildesIsValid = validateInput(nameIn,messageIn)
        if(fildesIsValid) {
            val message = MessageItem(nameIn, messageIn, true)
            messageItemController.editMessageItem(message)
        }
    }

    private fun parseName(inputName:String?): String{
        return inputName?.trim() ?:"";
    }

    private fun validateInput(name: String, message: String):Boolean{
        var res = true
        if(name.isBlank()){
            _errorInputName.value = true
            res = false
        }
        if(message.isBlank()){
            res = false
            _errorInputMessage.value = true
        }
        return res
    }

    public fun resetErrorInputName(){
        _errorInputName.value = false
    }

    public fun resetErrorInputMessage(){
        _errorInputMessage.value = false
    }
}