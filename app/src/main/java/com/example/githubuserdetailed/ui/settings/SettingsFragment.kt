package com.example.githubuserdetailed.ui.settings

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.githubuserdetailed.AlarmReceiver
import com.example.githubuserdetailed.R
import com.example.githubuserdetailed.databinding.FragmentSettingsBinding
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment(), View.OnClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        alarmReceiver = AlarmReceiver()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.settingsToolbar)
        context?.getColor(R.color.design_default_color_background)?.let {
            binding.settingsToolbar.setTitleTextColor(
                it
            )
        }
        binding.settingsToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.settingsToolbar.setNavigationOnClickListener{
            it.setOnClickListener {
                (activity as AppCompatActivity).onBackPressed()
            }
        }
        binding.btnReminderTime.setOnClickListener(this)
        binding.btnSetReminder.setOnClickListener(this)
        binding.btnDeleteReminder.setOnClickListener(this)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnReminderTime -> {
                val c = Calendar.getInstance()
                val mHour = c[Calendar.HOUR_OF_DAY]
                val mMinute = c[Calendar.MINUTE]

                // Launch Time Picker Dialog

                // Launch Time Picker Dialog
                val timePickerDialog = TimePickerDialog(
                    context,
                    { view, hourOfDay, minute ->
                        binding.timeLabel.text = ("$hourOfDay:$minute")
                    },
                    mHour,
                    mMinute,
                    false
                )
                timePickerDialog.show()
            }
            R.id.btnSetReminder -> {
                val repeatTime = binding.timeLabel.text.toString()
                activity?.applicationContext?.let { it1 ->
                    alarmReceiver.setRepeatingAlarm(
                        it1, AlarmReceiver.TYPE_REPEATING,
                        repeatTime
                    )
                    Log.i("AlarmReceiver", "Alarm set time: $repeatTime")
                }
            }

            R.id.btnDeleteReminder -> {
                activity?.applicationContext?.let { alarmReceiver.cancelAlarm(it, AlarmReceiver.TYPE_REPEATING) }
            }
        }
    }
}