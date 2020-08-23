package com.sungbin.gitkakaobot.ui.fragment.bot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.adapter.BotAdapter
import com.sungbin.gitkakaobot.model.BotItem
import com.sungbin.gitkakaobot.model.BotType
import com.sungbin.sungbintool.extensions.hide
import com.sungbin.sungbintool.extensions.show
import kotlinx.android.synthetic.main.fragment_bot.*

class BotFragment : Fragment() {

    companion object {
        private val instance by lazy {
            BotFragment()
        }

        fun instance() = instance
    }

    private var fromPos = -1
    private var toPos = -1
    private var adapter: BotAdapter? = null
    private val bots = arrayListOf(
        BotItem("test-bot-1", true, true, BotType.JS, "없음", 1),
        BotItem("test-bot-2", true, false, BotType.SIMPLE, "어제", 2),
        BotItem("test-bot-3", false, true, BotType.JS, "오늘", 3),
        BotItem("test-bot-4", false, false, BotType.JS, "내일", 4),
        BotItem("test-bot-5", true, true, BotType.JS, "내년", 5)
    )

    private val viewModel by viewModels<BotViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_bot, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = false

        cl_empty.hide()
        rv_bot.show()

        adapter = BotAdapter(bots)
        rv_bot.adapter = adapter
        rv_bot.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = true
        }
        ItemTouchHelper(dragCallback).attachToRecyclerView(rv_bot)
    }

    private val dragCallback = object : ItemTouchHelper.Callback() {
        var dragFrom = -1
        var dragTo = -1
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
                0
            )
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            if (dragFrom == -1) {
                dragFrom = fromPosition
            }
            dragTo = toPosition
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return false
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            if (dragFrom != -1 && dragTo != -1 && dragFrom != dragTo) {
                moveItem(dragFrom, dragTo)
            }
            dragTo = -1
            dragFrom = dragTo
        }

        private fun moveItem(oldPos: Int, newPos: Int) {
            val oldItem = bots[oldPos]
            val newItem = bots[newPos]
            bots.run {
                removeAt(oldPos)
                add(oldPos, newItem)
                removeAt(newPos)
                add(newPos, oldItem)
            }
            adapter?.run {
                notifyItemChanged(oldPos)
                notifyItemChanged(newPos)
            }
        }
    }
}