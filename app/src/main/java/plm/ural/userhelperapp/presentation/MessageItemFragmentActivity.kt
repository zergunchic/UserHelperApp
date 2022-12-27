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

class MessageItemFragmentActivity : AppCompatActivity() {
    private var messageItemId = -1
    private var screenMode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fmessage_item)
        parseIntent()
        launchRightMode()
    }

    private fun launchRightMode(){
        val fragment = when(screenMode){
            MODE_EDIT -> MessageItemFragment.newInstanceEditMessage(messageItemId)
            MODE_ADD -> MessageItemFragment.newInstanceAddMessage()
            else-> throw RuntimeException("Param message id is not set")
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.message_item_container, fragment)
            .commit()
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

    companion object{
        private const val EXTRA_SCREEN_MODE="extra_mode"
        private const val EXTRA_MESSAGE_ITEM_ID="extra_message_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"

        fun newIntentAddMessage(context: Context): Intent {
            val intent = Intent(context, MessageItemFragmentActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditMessage(context: Context, messageItemId:Int): Intent {
            val intent = Intent(context, MessageItemFragmentActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_MESSAGE_ITEM_ID, messageItemId)
            return intent
        }
    }
}