package com.diatomicsoft.digitalbancharampur.ui.car

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.diatomicsoft.digitalbancharampur.MainActivity
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.databinding.FragmentAddCarBinding
import com.diatomicsoft.digitalbancharampur.interfaceutil.ResponseListener
import com.diatomicsoft.digitalbancharampur.model.data.CommonResponse
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.NetworkConnectionInterceptor
import com.diatomicsoft.digitalbancharampur.model.repository.CarRepository
import com.diatomicsoft.digitalbancharampur.util.Coroutines.main
import com.diatomicsoft.digitalbancharampur.util.hide
import com.diatomicsoft.digitalbancharampur.util.show
import com.diatomicsoft.digitalbancharampur.util.toastSnack
import java.util.*
import android.widget.TimePicker





class AddCarFragment : Fragment(), ResponseListener {
    lateinit var binding: FragmentAddCarBinding
    lateinit var model: CarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddCarBinding.inflate(inflater, container, false)
        val interceptor = NetworkConnectionInterceptor(requireContext())
        val api = Api(interceptor)
        val repository = CarRepository(api, requireContext())
        val factory = CarViewModelFactory(repository)
        model = ViewModelProvider(this, factory).get(CarViewModel::class.java)
        binding.vm = model
        binding.lifecycleOwner = this
        model.listener = this
        val act = activity as MainActivity
        binding.ivImagePicker.setOnClickListener {
            act.getContent.launch("image/*")
        }
        binding.etStartTime.setOnClickListener { getTime() }

        act.imageUrl.observe(viewLifecycleOwner, Observer {
            binding.ivImagePicker.setImageURI(it)
            binding.ivImagePicker.scaleType = ImageView.ScaleType.CENTER_CROP
            model.file.value = act.getFile(it, repository.getContributorId())
        })


        binding.btnDone.setOnClickListener {
            main {
                model.addCar()
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        findNavController().popBackStack(R.id.carFragment, false)
    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onError(message: String) {
        binding.rl.toastSnack(message)
        binding.progressBar.hide()
    }

    override fun onSuccess(response: CommonResponse) {
        binding.progressBar.hide()
        binding.rl.toastSnack(response.message)
        activity?.onBackPressed()
    }

    fun getTime(){
       /*
        val mcurrentTime: Calendar = Calendar.getInstance()
        val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute: Int = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(requireContext(),
            { timePicker, selectedHour, selectedMinute -> binding.etStartTime.setText("$selectedHour:$selectedMinute") },
            hour,
            minute,
            false
        ) //Yes 24 hour time
        mTimePicker.show() */



        val calendar: Calendar = Calendar.getInstance()
        var time: String
        val timePickerDialog = TimePickerDialog(requireContext(),

            { view, hourOfDay, minute ->
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.HOUR, hourOfDay)
                var hour: String
                if(calendar.get(Calendar.HOUR) == 0){
                    hour = 12.toString()
                }else {
                    hour = calendar.get(Calendar.HOUR).toString()
                }
                time = hour+":" + calendar.get(Calendar.MINUTE)+" " + if (calendar.get(Calendar.AM_PM) === Calendar.AM) "AM" else "PM"
                Log.e("time", time)

                binding.etStartTime.setText(time)

            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
        )
        timePickerDialog.show()



    }

}