package com.elyseswoverland.circleconnect.ui.messages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.models.MessageResponse
import com.elyseswoverland.circleconnect.network.CircleConnectApiManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_messages.*
import rx.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MessagesFragment : Fragment() {
    private val groupAdapter = GroupAdapter<ViewHolder>()
    private lateinit var ctx: Context

    @Inject lateinit var circleConnectApiManager: CircleConnectApiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Dagger.getInstance().component().inject(this)
        activity?.invalidateOptionsMenu()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.elyseswoverland.circleconnect.R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ctx = context ?: return
        setUpRecyclerView()

        val lastDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val formattedDate = dateFormat.format(lastDate)

        circleConnectApiManager.getCustomerMessages(formattedDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetMessagesSuccess, this::onGetMessagesFailure)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        menu?.findItem(R.id.preferences)?.isVisible = false
        menu?.findItem(R.id.logout)?.isVisible = false
    }

    private fun setUpRecyclerView() {
        val lm = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = lm
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, lm.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun onGetMessagesSuccess(messages: MessageResponse) {
        if (messages.messages.size > 0) {
            recyclerView.visibility = View.VISIBLE
            noNewMessages.visibility = View.GONE
            groupAdapter.clear()
            groupAdapter.add(Section().apply {
                messages.messages.forEachIndexed { _, message ->
                    add(MessageItem(ctx, message))
                }
            })
            recyclerView.adapter = groupAdapter
        } else {
            recyclerView.visibility = View.GONE
            noNewMessages.visibility = View.VISIBLE
        }
    }

    private fun onGetMessagesFailure(throwable: Throwable) {
        throwable.printStackTrace()
    }
}