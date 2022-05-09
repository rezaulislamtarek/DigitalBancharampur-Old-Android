package com.diatomicsoft.digitalbancharampur.ui.ambulance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.diatomicsoft.digitalbancharampur.MainActivity
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.databinding.FragmentAmbulanceDetailsBinding
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.NetworkConnectionInterceptor
import com.diatomicsoft.digitalbancharampur.model.repository.AmbulanceRepository
import com.diatomicsoft.digitalbancharampur.util.BASEURL

class AmbulanceDetailsFragment : Fragment() {
    lateinit var binding: FragmentAmbulanceDetailsBinding
    lateinit var model: AmbulanceViewModel
    private val args : AmbulanceDetailsFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAmbulanceDetailsBinding.inflate(inflater,container,false)
        val interceptor = NetworkConnectionInterceptor(requireContext())
        val api = Api(interceptor)
        val repository = AmbulanceRepository(api, requireContext())
        val factory = AmbulanceViewModelFactory(repository)
        model = ViewModelProvider(this, factory).get(AmbulanceViewModel::class.java)
        val fragmentContext = this
        binding.apply {
            ambulance = args.ambulance
            lifecycleOwner = fragmentContext
            cvCall.setOnClickListener { model.dialToPhone(args.ambulance.mobile, activity as MainActivity) }
        }




        model.getUser(args.ambulance.contributorId)
        model.user.observe(viewLifecycleOwner, Observer {
            binding.image.apply {
                Glide.with(this)
                    .load(BASEURL + it.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(this)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }

            binding.tvUserName.text = it.name
            binding.tvAddress.text = it.address

        })


        return  binding.root
    }
}