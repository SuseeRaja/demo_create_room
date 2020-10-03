package com.demo.videochat.app

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DateFormat
import java.util.*

class FeedsFragment : Fragment() {

    lateinit var progressDialog: ProgressDialog
    private lateinit var recyclerView: RecyclerView
    private lateinit var myFeedAdapter: MyFeedAdapter

    private lateinit var bottomSheet: LinearLayout
    private lateinit var createRoomFab: FloatingActionButton
    private lateinit var cancelRoom: TextView
    private lateinit var createRoom: TextView
    private lateinit var roomEdt: EditText
    private lateinit var checkIsLive: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_feeds, container, false)

        recyclerView = view.findViewById(R.id.my_recycler_view)
        bottomSheet = view.findViewById(R.id.bottom_sheet_behavior_id)
        createRoomFab = view.findViewById(R.id.fab_create_button)
        createRoom = view.findViewById(R.id.create_action)
        cancelRoom = view.findViewById(R.id.cancel_action)
        roomEdt = view.findViewById(R.id.etd_room)
        checkIsLive = view.findViewById(R.id.chk_live)

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        createRoomFab.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            createRoomFab.hide()
        }

        cancelRoom.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            createRoomFab.show()
        }

        createRoom.setOnClickListener {
            if(roomEdt.text.isNotEmpty()) {
                roomEdt.error = null
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                createRoomFab.show()
                context?.let { it1 -> saveRecord(it1) }
            } else {
              roomEdt.error = "Please enter room name"
            }
        }

        context?.let { viewRecord(it) }
        return view
    }

    private fun saveRecord(context: Context) {
        val df: DateFormat = DateFormat.getTimeInstance()
        df.timeZone = TimeZone.getTimeZone("gmt")
        val created: String = df.format(Date())

        val name = roomEdt.text.toString()

        var live =  0
        live = if(checkIsLive.isChecked) {
            1
        }else {
            0
        }

        val databaseHandler = DatabaseHandler(context)
        if(name.trim()!="" && created.trim()!="") {
            val status = databaseHandler.addFeed(FeedItemModel(name, live, created))
            if(status > -1){
                Toast.makeText(context, "record save", Toast.LENGTH_LONG).show()
                roomEdt.text.clear()
            }
        }else{
            Toast.makeText(context, "id or name or email cannot be blank", Toast.LENGTH_LONG).show()
        }
        viewRecord(context)
    }

    private fun viewRecord(context: Context) {
        val databaseHandler = DatabaseHandler(context)
        val feed: List<FeedItemModel> = databaseHandler.viewFeeds()
        val feedName = Array(feed.size){"0"}
        val feedLive: Array<Int> = Array(feed.size){0}
        val feedCreateAt = Array(feed.size){"null"}
        for((index, e) in feed.withIndex()) {
            feedName[index] = e.name
            feedLive[index] = e.live
            feedCreateAt[index] = e.created
        }
        //creating custom ArrayAdapter
        myFeedAdapter = MyFeedAdapter(feed)
        myFeedAdapter.onItemClick = { feedItem ->
            Log.d("TAG", feedItem.name)
            updateRecord(context, feedItem)
        }
        recyclerView.adapter = myFeedAdapter
        progressDialog.dismiss()
    }

    private fun updateRecord(context: Context, item: FeedItemModel){
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        val cancelAction = dialogView.findViewById(R.id.cancel_action) as TextView
        val changeAction = dialogView.findViewById(R.id.change_action) as TextView

        val b = dialogBuilder.create()

        cancelAction.setOnClickListener {
            b.dismiss()
        }

        changeAction.setOnClickListener {
            val databaseHandler = DatabaseHandler(context)
            val status = databaseHandler.updateByName(FeedItemModel(item.name, 0, item.created))
            if(status > -1) {
                Toast.makeText(context,"record update", Toast.LENGTH_LONG).show()
                viewRecord(context)
            }
            b.dismiss()
        }
        b.show()
    }

}