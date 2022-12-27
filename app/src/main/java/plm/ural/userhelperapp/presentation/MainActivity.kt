package plm.ural.userhelperapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import plm.ural.userhelperapp.R
import plm.ural.userhelperapp.presentation.MessageItemActivity.Companion.newIntentAddMessage

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var messageListAdapter: MessageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.messageList.observe(this) {
            messageListAdapter.submitList(it)
        }

        val buttonAddMessage = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        buttonAddMessage.setOnClickListener {
            //val intent = MessageItemActivity.newIntentAddMessage(this)
            val intent = MessageItemFragmentActivity.newIntentAddMessage(this)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val rvMessageList = findViewById<RecyclerView>(R.id.rv_message_list)
        with(rvMessageList) {
            messageListAdapter = MessageListAdapter()
            adapter = messageListAdapter
            recycledViewPool.setMaxRecycledViews(0, MessageListAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(1, MessageListAdapter.MAX_POOL_SIZE)
        }
        setupLongClickListener()

        setupClickListener()

        setupSwipeListener(rvMessageList)


    }

    private fun setupSwipeListener(rvMessageList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback
            (0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = messageListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteMessageItem(item)
            }

        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvMessageList)
    }

    private fun setupClickListener() {
        messageListAdapter.onMessageClickListener = {
            val intent = MessageItemFragmentActivity.newIntentEditMessage(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        messageListAdapter.onMessageLongClickListener = {
            viewModel.changeEnableState(it)
        }

        //Иной способ
//      messageListadapter.onMessageLongClickListener = object: MessageListAdapter.OnMessageLongClickListener{
//            override fun onMessageLongClick(messageItem: MessageItem) {
//                viewModel.changeEnableState(messageItem)
//            }
//        }
    }


}