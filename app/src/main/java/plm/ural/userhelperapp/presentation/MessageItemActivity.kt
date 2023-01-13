package plm.ural.userhelperapp.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import plm.ural.userhelperapp.R

class MessageItemActivity : AppCompatActivity() {

    private lateinit var viewModel:MessageItemViewModel

    private lateinit var messageFld:EditText
    private lateinit var nameFld:EditText
    private lateinit var saveBtn: Button
    private var messageItemId = -1
    private var screenMode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MessageItemViewModel::class.java)
        setContentView(R.layout.activity_message_item)
        parseIntent()
        initViews()
        nameFld.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        messageFld.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputMessage()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        observeViewModel()
        launchRightMode()


    }

    private fun observeViewModel(){
        viewModel.errorInputMessage.observe(this){
            val message = if(it){
                getString(R.string.error_message)
            }else null
            messageFld.error = message
        }

        viewModel.errorInputName.observe(this){
            val message = if(it){
                getString(R.string.error_name)
            }else null
            nameFld.error = message
        }

        viewModel.shouldCloseScreen.observe(this){
            finish()
        }
    }

    private fun launchRightMode(){
        when(screenMode){
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchAddMode(){
        saveBtn.setOnClickListener{
            viewModel.addMessageItem(nameFld.text?.toString(),messageFld.text?.toString())
        }
    }
    private fun launchEditMode(){
        viewModel.getMessageItem(messageItemId)
        viewModel.messageLiveData.observe(this){
            nameFld.setText(it.userName)
            messageFld.setText(it.userMessage)
        }
        saveBtn.setOnClickListener{
            viewModel.editMessageItem(nameFld.text?.toString(),messageFld.text?.toString())
        }
    }

    private fun parseIntent(){
        if(!intent.hasExtra(EXTRA_SCREEN_MODE))
            throw RuntimeException("param screen is absent")
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD)
            throw RuntimeException("Unknown screen mode $mode")
        screenMode = mode
        if(screenMode == MODE_EDIT && !intent.hasExtra(EXTRA_MESSAGE_ITEM_ID))
            throw RuntimeException("Param message id is not set")
        if(screenMode == MODE_EDIT)
            messageItemId = intent.getIntExtra(EXTRA_MESSAGE_ITEM_ID, -1)
    }

    private fun initViews(){
        messageFld = findViewById(R.id.MessageFld)
        nameFld = findViewById(R.id.PersonName)
        saveBtn = findViewById(R.id.saveBtn)
    }

    companion object{
        private const val EXTRA_SCREEN_MODE="extra_mode"
        private const val EXTRA_MESSAGE_ITEM_ID="extra_message_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"

        fun newIntentAddMessage(context: Context):Intent{
            val intent = Intent(context, MessageItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditMessage(context: Context, messageItemId:Int):Intent{
            val intent = Intent(context, MessageItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_MESSAGE_ITEM_ID, messageItemId)
            return intent
        }
    }
}