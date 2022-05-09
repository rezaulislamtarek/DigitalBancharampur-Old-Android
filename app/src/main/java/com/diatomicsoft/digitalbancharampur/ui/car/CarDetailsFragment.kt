package com.diatomicsoft.digitalbancharampur.ui.car

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.diatomicsoft.digitalbancharampur.MainActivity
import com.diatomicsoft.digitalbancharampur.databinding.FragmentCarDetailsBinding
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.NetworkConnectionInterceptor
import com.diatomicsoft.digitalbancharampur.model.repository.CarRepository
import com.diatomicsoft.digitalbancharampur.util.BASEURL

class CarDetailsFragment : Fragment() {
    private val args: CarDetailsFragmentArgs by navArgs()
    lateinit var binding: FragmentCarDetailsBinding
    lateinit var model: CarViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarDetailsBinding.inflate(inflater, container, false)
        val interceptor = NetworkConnectionInterceptor(requireContext())
        val api = Api(interceptor)
        val repository = CarRepository(api,requireContext())
        val factory = CarViewModelFactory(repository)
        model = ViewModelProvider(this,factory).get(CarViewModel::class.java)
        model.getUser(args.car.contributorId)
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
        binding.car = args.car
        binding.apply {
            cvCallDriver.setOnClickListener {
                Toast.makeText(requireContext(), "Click with ${args.car.driverNumber}", Toast.LENGTH_SHORT).show()
                model.dialToPhone(args.car.driverNumber, activity as MainActivity)
            }
            cvCallOwner.setOnClickListener { model.dialToPhone(args.car.ownerNumber, activity as MainActivity) }
        }
        return binding.root
    }

}