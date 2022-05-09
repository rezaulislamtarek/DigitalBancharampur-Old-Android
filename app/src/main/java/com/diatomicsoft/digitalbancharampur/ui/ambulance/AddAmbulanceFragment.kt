package com.diatomicsoft.digitalbancharampur.ui.ambulance

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.diatomicsoft.digitalbancharampur.MainActivity
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.databinding.FragmentAddAmbulanceBinding
import com.diatomicsoft.digitalbancharampur.interfaceutil.ResponseListener
import com.diatomicsoft.digitalbancharampur.model.data.CommonResponse
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.NetworkConnectionInterceptor
import com.diatomicsoft.digitalbancharampur.model.repository.AmbulanceRepository
import com.diatomicsoft.digitalbancharampur.util.Coroutines.main
import com.diatomicsoft.digitalbancharampur.util.hide
import com.diatomicsoft.digitalbancharampur.util.show
import com.diatomicsoft.digitalbancharampur.util.toastSnack


class AddAmbulanceFragment : Fragment(), ResponseListener {

    lateinit var binding: FragmentAddAmbulanceBinding
    lateinit var model: AmbulanceViewModel
    lateinit var repository: AmbulanceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAmbulanceBinding.inflate(inflater, container, false)
        val interceptor = NetworkConnectionInterceptor(requireContext())
        val api = Api(interceptor)
        repository = AmbulanceRepository(api,requireContext())
        val factory = AmbulanceViewModelFactory(repository)
        model = ViewModelProvider(this, factory).get(AmbulanceViewModel::class.java)
        model.ambulanceListener = this
        binding.vm = model
        binding.lifecycleOwner = this
        binding.btnAddDone.setOnClickListener {
            main {
                model.addAmbulance()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val act = activity as MainActivity
        binding.ivImagePickerAmbu.setOnClickListener {
            imagePick(act)
        }

        act.imageUrl.observe(viewLifecycleOwner, Observer {
            binding.ivImagePickerAmbu.setImageURI(it)
            binding.ivImagePickerAmbu.scaleType = ImageView.ScaleType.CENTER_CROP
            model.imageUrl.value = it
            model.file.value = act.getFile(it, repository.getContributorId())

        })
    }

    private fun imagePick(act: MainActivity) {
        act.getContent.launch("image/*")
    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onError(message: String) {
        binding.rl.toastSnack(message)
        binding.progressBar.hide()
    }

    override fun onSuccess(response: CommonResponse) {
        binding.rl.toastSnack(response.message)
        binding.progressBar.hide()
        activity?.onBackPressed()
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "onDestroy()")
        findNavController().popBackStack(R.id.ambulanceFragment, false)
    }


}