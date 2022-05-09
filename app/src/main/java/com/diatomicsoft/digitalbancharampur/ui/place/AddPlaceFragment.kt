package com.diatomicsoft.digitalbancharampur.ui.place

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
import androidx.navigation.fragment.navArgs
import com.diatomicsoft.digitalbancharampur.MainActivity
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.databinding.FragmentAddPlaceBinding
import com.diatomicsoft.digitalbancharampur.interfaceutil.ResponseListener
import com.diatomicsoft.digitalbancharampur.model.data.CommonResponse
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.NetworkConnectionInterceptor
import com.diatomicsoft.digitalbancharampur.model.repository.PlaceRepository
import com.diatomicsoft.digitalbancharampur.util.hide
import com.diatomicsoft.digitalbancharampur.util.show
import com.diatomicsoft.digitalbancharampur.util.toastSnack

class AddPlaceFragment : Fragment(), ResponseListener {
    val TAG = "AddPlaceFragment"
    val args: AddPlaceFragmentArgs by navArgs()
    lateinit var binding: FragmentAddPlaceBinding
    lateinit var viewModel: PlaceViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddPlaceBinding.inflate(inflater, container, false)
        val interceptor = NetworkConnectionInterceptor(requireContext())
        val api = Api(interceptor)
        val repository = PlaceRepository(api,requireContext())
        val factory = PlaceViewModelFactory(repository, args.type)
        viewModel = ViewModelProvider(this, factory).get(PlaceViewModel::class.java)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        viewModel.listener = this

        val act = activity as MainActivity

        binding.ivImagePicker.setOnClickListener {
            imagePicker(act)
        }

        act.imageUrl.observe(viewLifecycleOwner, Observer {
            viewModel.imageUri.value = it
            viewModel.file.value = act.getFile(it, repository.getContributorId())
        })

        viewModel.imageUri.observe(viewLifecycleOwner, Observer {
            binding.ivImagePicker.setImageURI(it)
            binding.ivImagePicker.scaleType = ImageView.ScaleType.CENTER_CROP
        })
        return binding.root
    }

    private fun imagePicker(act: MainActivity) {
        act.getContent.launch("image/*")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
        findNavController().popBackStack(R.id.placeFragment, false)
    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onError(message: String) {
        binding.rl.toastSnack(message)
        Log.d("ERROR",message)
        binding.progressBar.hide()
    }

    override fun onSuccess(response: CommonResponse) {
        binding.rl.toastSnack(response.message)
        binding.progressBar.hide()
        activity?.onBackPressed()
    }

}