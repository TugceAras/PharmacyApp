package com.tugcearas.pharmacyapp.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.huawei.hms.common.ApiException
import com.huawei.hms.support.account.AccountAuthManager
import com.huawei.hms.support.account.request.AccountAuthParams
import com.huawei.hms.support.account.request.AccountAuthParamsHelper
import com.huawei.hms.support.account.service.AccountAuthService
import com.tugcearas.pharmacyapp.R
import com.tugcearas.pharmacyapp.databinding.FragmentLoginScreenBinding
import com.tugcearas.pharmacyapp.util.extensions.click
import com.tugcearas.pharmacyapp.util.extensions.toastMessage

class LoginScreen : Fragment() {

    private lateinit var binding: FragmentLoginScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        binding.btnLogin.click { huaweiSignIn() }
        return binding.root
    }

    private fun huaweiSignIn(){
        val authParams : AccountAuthParams =
            AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setIdToken().createParams()

        val service : AccountAuthService = AccountAuthManager.getService(requireActivity(), authParams)
        startActivityForResult(service.signInIntent, 8888)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 8888) {
            val authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data)
            if (authAccountTask.isSuccessful) {
                val authAccount = authAccountTask.result
                Log.i(TAG, "idToken:" + authAccount.idToken)
                Log.i(TAG, "accountFlag:" + authAccount.accountFlag)
                findNavController().navigate(R.id.action_loginScreen_to_homeScreen)
                requireContext().toastMessage(getString(R.string.login_successfully))
            } else {
                Log.e(TAG, "sign in failed : " + (authAccountTask.exception as ApiException).statusCode)
                requireContext().toastMessage(getString(R.string.login_unsuccessfully))
            }
        }
    }
}