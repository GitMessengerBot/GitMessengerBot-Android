package com.sungbin.gitkakaobot.ui.fragment

import androidx.fragment.app.Fragment
import com.sungbin.gitkakaobot.ui.activity.DashboardActivity


/**
 * Created by SungBin on 2021-01-09.
 */

// todo: `open` vs `abstract` 각각 언제 써야 하는 걸 까?
// todo: 탭 전환 애니메이션 살리기 -> 생명주기 써서 하면 될 거 같은데...
abstract class BaseFragment : Fragment() {

    val vm = (requireContext() as DashboardActivity).vm

}