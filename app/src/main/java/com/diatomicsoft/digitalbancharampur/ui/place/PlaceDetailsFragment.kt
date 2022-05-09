package com.diatomicsoft.digitalbancharampur.ui.place

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
import com.diatomicsoft.digitalbancharampur.databinding.FragmentPlaceDetailsBinding
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.NetworkConnectionInterceptor
import com.diatomicsoft.digitalbancharampur.model.repository.PlaceRepository
import com.diatomicsoft.digitalbancharampur.util.BASEURL
import com.diatomicsoft.digitalbancharampur.util.Coroutines.main

class PlaceDetailsFragment : Fragment() {
    lateinit var binding: FragmentPlaceDetailsBinding
    lateinit var model: PlaceViewModel
    lateinit var repository: PlaceRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val args: PlaceDetailsFragmentArgs by navArgs()
        binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false)
        val interceptor = NetworkConnectionInterceptor(requireContext())
        val api = Api(interceptor)
        repository = PlaceRepository(api, requireContext())
        val factory = PlaceViewModelFactory(repository, "place")
        model = ViewModelProvider(this, factory).get(PlaceViewModel::class.java)
        model.getUser(args.place.contributorId)
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

        if(args.place.mobile!="0") binding.llCall.visibility = View.VISIBLE


        binding.place = args.place
        binding.cvCall.setOnClickListener { model.dialToPhone(args.place.mobile, activity as MainActivity) }
        binding.lifecycleOwner = this
        return binding.root
    }

}