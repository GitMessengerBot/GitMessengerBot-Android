package com.sungbin.gitkakaobot.ui.fragment.bot

import android.app.Activity
import android.os.Build
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
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.OnBackPressedUtil
import com.sungbin.gitkakaobot.util.UiUtil
import com.sungbin.sungbintool.LogUtils
import com.sungbin.sungbintool.Utils
import com.sungbin.sungbintool.extensions.clear
import com.sungbin.sungbintool.extensions.hide
import com.sungbin.sungbintool.extensions.show
import kotlinx.android.synthetic.main.fragment_bot.*


class BotFragment : Fragment(), OnBackPressedUtil {

    companion object {
        private val instance by lazy {
            BotFragment()
        }

        fun instance() = instance
    }

    private var fromPos = -1
    private var toPos = -1
    private var adapter: BotAdapter? = null

    private val viewModel by viewModels<BotViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_bot, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = false

        viewModel.initBotList()

        if (viewModel.jsBotList.value.isNullOrEmpty()
            && viewModel.simpleBotList.value.isNullOrEmpty()
        ) {
            cl_empty.show()
            rv_bot.hide()
        } else {
            cl_empty.hide()
            rv_bot.show()
        }

        adapter = BotAdapter(viewModel.jsBotList.value ?: arrayListOf())
        rv_bot.adapter = adapter
        rv_bot.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = true
        }
        ItemTouchHelper(dragCallback).attachToRecyclerView(rv_bot)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rv_bot.setOnScrollChangeListener { _, _, y, _, oldY ->
                if (y > oldY) {
                    // Down
                    efab_add.shrink()
                }
                if (y < oldY) {
                    // Up
                    efab_add.extend()
                }
            }
        }

        efab_add.setOnClickListener {
            tsl_container.startTransform()
        }

        btn_add.setOnClickListener {
            val botType =
                if (mbtg_container.checkedButtonId == R.id.btn_javascript) BotType.JS else BotType.SIMPLE
            val bot = BotItem(
                tiet_bot_name.text.toString(),
                false,
                false,
                botType,
                "없음",
                BotUtil.getLastIndex(BotType.JS, viewModel.jsBotList.value!!) + 1,
                Utils.makeRandomUUID()
            )
            viewModel.jsBotList.run {
                value?.add(bot)
                postValue(value)
            }
            BotUtil.createNewBot(requireContext(), bot)
            mbtg_container.check(R.id.btn_javascript)
            tiet_bot_name.clear()
            tsl_container.finishTransform()
            UiUtil.snackbar(it, requireContext().getString(R.string.added_new_bot))
        }

        viewModel.jsBotList.observe(viewLifecycleOwner, {
            LogUtils.w(it)
            adapter = BotAdapter(it)
            rv_bot.adapter = adapter
            if (it.isNullOrEmpty()) {
                cl_empty.show()
                rv_bot.hide()
            } else {
                cl_empty.hide()
                rv_bot.show()
            }
        })
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
            dragFrom = -1
        }

        private fun moveItem(oldPos: Int, newPos: Int) {
            val oldItem = viewModel.jsBotList.value!![oldPos]
            val newItem = viewModel.jsBotList.value!![newPos]
            BotUtil.changeBotIndex(oldItem, newPos)
            BotUtil.changeBotIndex(newItem, oldPos)
            viewModel.jsBotList.value!!.run {
                removeAt(oldPos)
                add(oldPos, newItem)
                removeAt(newPos)
                add(newPos, oldItem)
            }
            rv_bot.post {
                adapter?.run {
                    notifyItemChanged(oldPos)
                    notifyItemChanged(newPos)
                }
            }
        }
    }

    override fun onBackPressed(activity: Activity): Boolean {
        when (tsl_container.isTransformed) {
            true -> {
                tsl_container.finishTransform()
            }
            else -> {
                activity.finish()
            }
        }
        return true
    }

}