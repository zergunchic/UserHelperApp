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

class MessageItemFragment(): Fragment() {

    private lateinit var viewModel:MessageItemViewModel
    private lateinit var messageFld: EditText
    private lateinit var nameFld: EditText
    private lateinit var saveBtn: Button

    private var screenMode: String = ""
    private var messageItemId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_message_item, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MessageItemViewModel::class.java]
        initViews(view)
        launchRightMode()
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
        val args = requireArguments()
        if(!args.containsKey(EXTRA_SCREEN_MODE))
            throw RuntimeException("param screen is absent")
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD)
            throw RuntimeException("Unknown screen mode $mode")
        screenMode = mode
        if(screenMode == MODE_EDIT && !args.containsKey(
                EXTRA_MESSAGE_ITEM_ID
            ))
            throw RuntimeException("Param message id is not set")
        if(screenMode == MODE_EDIT)
            messageItemId = args.getInt(EXTRA_MESSAGE_ITEM_ID, -1)
    }

    private fun initViews(view:View){
        messageFld = view.findViewById(R.id.MessageFld)
        nameFld = view.findViewById(R.id.PersonName)
        saveBtn = view.findViewById(R.id.saveBtn)
    }

    private fun launchRightMode(){
        when(screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    companion object{
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SCREEN_MODE="extra_mode"
        private const val EXTRA_MESSAGE_ITEM_ID="extra_message_item_id"

        fun newInstanceAddMessage():MessageItemFragment{
            return MessageItemFragment().apply{
                arguments = Bundle().apply{
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditMessage(messageItemId: Int):MessageItemFragment{
            return MessageItemFragment().apply{
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_MESSAGE_ITEM_ID,messageItemId)
                }
            }
        }
    }
}