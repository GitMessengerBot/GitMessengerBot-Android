package com.sungbin.gitkakaobot.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.gitkakaobot.adapter.BotAdapter
import com.sungbin.gitkakaobot.databinding.FragmentBotBinding
import com.sungbin.gitkakaobot.ui.fragment.vm.BotViewModel
import com.sungbin.gitkakaobot.util.BotUtil

class BotFragment : Fragment() {

    private val vm by viewModels<BotViewModel>()
    private val binding by lazy { FragmentBotBinding.inflate(layoutInflater) }
    private lateinit var adapter: BotAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

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

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit
        override fun isLongPressDragEnabled() = true
        override fun isItemViewSwipeEnabled() = false
        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            if (dragFrom != -1 && dragTo != -1 && dragFrom != dragTo) {
                moveItem(dragFrom, dragTo)
            }
            dragTo = -1
            dragFrom = -1
        }

        private fun moveItem(oldPos: Int, newPos: Int) {
            val oldItem = vm.botList.value!![oldPos]
            val newItem = vm.botList.value!![newPos]
            BotUtil.changeBotIndex(oldItem, newPos)
            BotUtil.changeBotIndex(newItem, oldPos)
            vm.botList.value!!.run {
                removeAt(oldPos)
                add(oldPos, newItem)
                removeAt(newPos)
                add(newPos, oldItem)
            }
            binding.rvBot.post {
                adapter.run {
                    notifyItemChanged(oldPos)
                    notifyItemChanged(newPos)
                }
            }
        }
    }

}