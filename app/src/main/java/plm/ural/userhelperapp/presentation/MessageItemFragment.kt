package plm.ural.userhelperapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import plm.ural.userhelperapp.R

class MessageItemFragment: Fragment() {

    private lateinit var viewModel:MessageItemViewModel
    private lateinit var messageFld: EditText
    private lateinit var nameFld: EditText
    private lateinit var saveBtn: Button
    private var messageItemId = -1
    private var screenMode = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_message_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MessageItemViewModel::class.java]
        //setContentView(R.layout.activity_message_item)
        parseParams()
        initViews(view)
        nameFld.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        messageFld.addTextChangedListener(object: TextWatcher {
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
        viewModel.errorInputMessage.observe(viewLifecycleOwner){
            val message = if(it){
                getString(R.string.error_message)
            }else null
            messageFld.error = message
        }

        viewModel.errorInputName.observe(viewLifecycleOwner){
            val message = if(it){
                getString(R.string.error_name)
            }else null
            nameFld.error = message
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner){
            activity?.onBackPressed()
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
        viewModel.messageLiveData.observe(viewLifecycleOwner){
            nameFld.setText(it.userName)
            messageFld.setText(it.userMessage)
        }
        saveBtn.setOnClickListener{
            viewModel.editMessageItem(nameFld.text?.toString(),messageFld.text?.toString())
        }
    }

    private fun parseParams(){
        if (screenMode != MODE_EDIT && screenMode != MODE_ADD)
            throw RuntimeException("Unknown screen mode $screenMode")
        if(screenMode == MODE_EDIT && messageItemId == -1)
            throw RuntimeException("Param message id is not set")
    }

    private fun initViews(view:View){
        messageFld = view.findViewById(R.id.MessageFld)
        nameFld = view.findViewById(R.id.PersonName)
        saveBtn = view.findViewById(R.id.saveBtn)
    }

    companion object{
        private const val EXTRA_SCREEN_MODE="extra_mode"
        private const val EXTRA_MESSAGE_ITEM_ID="extra_message_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"

        fun newIntentAddMessage(context: Context): Intent {
            val intent = Intent(context, MessageItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditMessage(context: Context, messageItemId:Int): Intent {
            val intent = Intent(context, MessageItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_MESSAGE_ITEM_ID, messageItemId)
            return intent
        }
    }
}