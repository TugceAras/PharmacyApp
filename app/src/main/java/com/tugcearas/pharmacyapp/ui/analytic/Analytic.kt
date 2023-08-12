package com.tugcearas.pharmacyapp.ui.analytic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.type.HAEventType
import com.huawei.hms.analytics.type.HAParamType
import com.huawei.hms.analytics.type.ReportPolicy
import com.tugcearas.pharmacyapp.R

class Analytic : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val context = requireContext().applicationContext
        val instance = HiAnalytics.getInstance(context)

        // Set the ON_MOVE_BACKGROUND_POLICY and ON_SCHEDULED_TIME_POLICY
        val moveBackgroundPolicy = ReportPolicy.ON_MOVE_BACKGROUND_POLICY
        val scheduledTimePolicy = ReportPolicy.ON_SCHEDULED_TIME_POLICY
        scheduledTimePolicy.threshold = 600
        val reportPolicies = HashSet<ReportPolicy>()
        with(reportPolicies){
            add(scheduledTimePolicy)
            add(moveBackgroundPolicy)
        }
        instance.setReportPolicies(reportPolicies)

        val bundle = Bundle()
        with(bundle){
            putString("exam_difficulty", "high")
            putString("exam_level", "1-1")
            putString("exam_time", "20190520-08")
        }
        instance.onEvent("begin_examination", bundle)

        val bundlePredefined = Bundle()
        with(bundlePredefined){
            putString(HAParamType.PRODUCTID, "item_ID")
            putString(HAParamType.PRODUCTNAME, "name")
            putString(HAParamType.CATEGORY, "category")
            putLong(HAParamType.QUANTITY, 100L)
            putDouble(HAParamType.PRICE, 10.01)
            putDouble(HAParamType.REVENUE, 10.0)
            putString(HAParamType.CURRNAME, "currency")
            putString(HAParamType.PLACEID, "location_ID")
        }
        instance.onEvent(HAEventType.ADDPRODUCT2WISHLIST, bundlePredefined)

        return inflater.inflate(R.layout.fragment_analytic, container, false)
    }
}