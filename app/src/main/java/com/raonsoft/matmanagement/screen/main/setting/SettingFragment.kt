package com.raonsoft.matmanagement.screen.main.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.data.url.DefaultUrl
import com.raonsoft.matmanagement.data.url.DefaultUrl.DEFAULT_IMAGE_URL
import com.raonsoft.matmanagement.databinding.FragmentSettingBinding
import com.raonsoft.matmanagement.extention.load
import com.raonsoft.matmanagement.screen.login.LoginActivity
import com.raonsoft.matmanagement.screen.nfcWrite.NFCWriteActivity
import com.raonsoft.matmanagement.util.AccountInfoSingleton


class SettingFragment : Fragment() {

    private var binding: FragmentSettingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() = with(binding!!) {

        AccountInfoSingleton.armyunit_name?.let {
            groupNameTxv.text = it
        }

        AccountInfoSingleton.account_profile?.let {

            Glide.with(profileImb.context)
                .load(DefaultUrl.DEFAULT_IMAGE_URL + it)
                .apply(RequestOptions().circleCrop())
                .error(R.drawable.ic_default_profile).into(profileImb)
        }

        AccountInfoSingleton.armyunit_idx?.let {
            groupNumTxv.text = "소속번호 : $it"
        }


        groupNumTxv.text

        changeInfoTxv.setOnClickListener {
            val action = R.id.action_setting_to_changeInfo
            findNavController().navigate(action)
        }

        logoutTxv.setOnClickListener {
//            requireActivity().onBackPressed()
            startActivity(
                Intent(requireActivity(), LoginActivity::class.java)
                    .apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
            )

            requireActivity().finish()
        }

        nfcTagRegisterTxv.setOnClickListener {
            requireActivity().startActivity(Intent(requireActivity(), NFCWriteActivity::class.java))
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}