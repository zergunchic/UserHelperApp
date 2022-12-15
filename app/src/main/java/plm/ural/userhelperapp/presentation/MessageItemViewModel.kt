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

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen:LiveData<Unit>
        get() = _shouldCloseScreen

    fun getMessageItem(MessageItemId: Int){
        val item = messageItemController.getMessageItem(MessageItemId)
        _messageLiveData.value = item
    }

    fun addMessageItem(inputMessage:String?,inputName:String?){
        val nameIn = parseName(inputName)
        val messageIn = parseName(inputMessage)
        val fildesIsValid = validateInput(nameIn,messageIn)
        if(fildesIsValid) {
            val message = MessageItem(nameIn, messageIn, true)
            messageItemController.addMessageItem(message)
            _shouldCloseScreen.value = Unit
        }
    }

    fun editMessageItem(inputMessage:String?,inputName:String?){
        val nameIn = parseName(inputName)
        val messageIn = parseName(inputMessage)
        val fildsIsValid = validateInput(nameIn,messageIn)
        if(fildsIsValid) {
            _messageLiveData.value?.let {
                val message = it.copy(userName = nameIn, userMessage = messageIn)
                messageItemController.editMessageItem(message)
                _shouldCloseScreen.value = Unit
            }

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