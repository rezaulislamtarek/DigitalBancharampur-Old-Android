package com.diatomicsoft.digitalbancharampur.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.adapter.ItemAdapter
import com.diatomicsoft.digitalbancharampur.adapter.PlaceAdapter
import com.diatomicsoft.digitalbancharampur.adapter.PlaceAdapterHorizontal
import com.diatomicsoft.digitalbancharampur.databinding.FragmentHomeBinding
import com.diatomicsoft.digitalbancharampur.interfaceutil.ItemClickListener
import com.diatomicsoft.digitalbancharampur.model.data.Item
import com.diatomicsoft.digitalbancharampur.model.data.Places
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.NetworkConnectionInterceptor
import com.diatomicsoft.digitalbancharampur.model.repository.HomeRepository
import com.diatomicsoft.digitalbancharampur.util.Coroutines.main
import com.diatomicsoft.digitalbancharampur.util.FLAG_HORIZONTAL
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.diatomicsoft.digitalbancharampur.interfaceutil.ResponseListener
import com.diatomicsoft.digitalbancharampur.model.data.CommonResponse
import com.diatomicsoft.digitalbancharampur.ui.place.PlaceFragmentDirections

class HomeFragment : Fragment(), ItemClickListener, ResponseListener {
    var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    lateinit var nav: NavDirections
    lateinit var listener: ItemClickListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = Color.WHITE
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        activity?.actionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.white
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val interceptor = NetworkConnectionInterceptor(requireContext())
        val api = Api(interceptor)
        val repository = HomeRepository(api,requireContext())
        val factory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        binding?.vm = viewModel
        binding?.lifecycleOwner = this
        listener = this
        binding?.rvItems.also {
            it?.adapter = ItemAdapter(viewModel.getItems(), this)
            it?.layoutManager =
                GridLayoutManager(requireContext(),4)
                //LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.placeList.observe(viewLifecycleOwner, Observer {
            binding?.shimmerPlace?.visibility = View.GONE
            initRecyclerView(it, binding!!.rvPlace, 0)
        })

        viewModel.hospitalList.observe(viewLifecycleOwner, Observer {
            binding?.shimmerHospital?.visibility = View.GONE
            initRecyclerView(it, binding!!.rvHospital, 1)
        })

        binding?.ibPlace?.setOnClickListener {
            navigateToTargetFragment("place","Place")
        }
        binding?.ibHospital?.setOnClickListener {
            navigateToTargetFragment("hospital","Hospital")
        }
    }


    override fun itemClickListener(view: View, item: Item) {
        navigateToTargetFragment(item.type, item.title)
    }

    override fun placeClickListener(view: View, item: Places) {

        val navController = findNavController()
        navController.graph.findNode(R.id.placeDetailsFragment)?.label = item.title
        var nav: NavDirections = HomeFragmentDirections.actionHomeFragmentToPlaceDetailsFragment(item)
        navController.navigate(
            nav
        )


    }

    private fun navigateToTargetFragment(type: String, title: String) {
        val navController = findNavController()

        when (type) {
            "car" -> {
                navController.graph.findNode(R.id.carFragment)?.label = title
                nav = HomeFragmentDirections.actionHomeFragmentToCarFragment()
            }
            "ambulance" -> {
                navController.graph.findNode(R.id.ambulanceFragment)?.label = title
                nav = HomeFragmentDirections.actionHomeFragmentToAmbulanceFragment()
            }
            "Blood Bank" -> {
                Toast.makeText(requireContext(), "This fetcher is under development...", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {

                navController.graph.findNode(R.id.placeFragment)?.label = title
                nav = HomeFragmentDirections.actionHomeFragmentToPlaceFragment(type)
            }
        }
        navController.navigate(
            nav
        )
    }

    private fun initRecyclerView(data: List<Places>, rv: RecyclerView, flag: Int) {
        binding?.apply {
            rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rv.adapter = PlaceAdapterHorizontal(data, flag, listener)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        requireActivity().window.statusBarColor = Color.WHITE
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        val color = 0xFF228B26
        requireActivity().window.statusBarColor = color.toInt()
    }

    override fun onStarted() {

    }

    override fun onError(message: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(response: CommonResponse) {

    }

}