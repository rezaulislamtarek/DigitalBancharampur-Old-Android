package com.diatomicsoft.digitalbancharampur.ui.car

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.adapter.CarAdapter
import com.diatomicsoft.digitalbancharampur.databinding.FragmentCarBinding
import com.diatomicsoft.digitalbancharampur.interfaceutil.CarItemClickListener
import com.diatomicsoft.digitalbancharampur.model.data.Car
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.NetworkConnectionInterceptor
import com.diatomicsoft.digitalbancharampur.model.repository.CarRepository

class CarFragment : Fragment() , CarItemClickListener{
    val TAG = "CarFragment"
    lateinit var repository: CarRepository
    lateinit var binding: FragmentCarBinding
    lateinit var viewModel: CarViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCarBinding.inflate(inflater,container,false);
        val interceptor = NetworkConnectionInterceptor(requireContext());
        val api = Api(interceptor)
        repository = CarRepository(api,requireContext())
        val factory = CarViewModelFactory(repository)
        viewModel = ViewModelProvider(this,factory).get(CarViewModel::class.java)
        binding?.vm = viewModel
        binding.lifecycleOwner = this

        binding.shimmer.visibility = View.VISIBLE
        binding.shimmer.startShimmer()

        viewModel.carList.observe(viewLifecycleOwner, Observer {
            binding.shimmer.visibility = View.GONE
            initRecyclerView(it)
        })
        return binding?.root
    }

    private fun initRecyclerView(data: List<Car>){
        val listener = this
        binding?.apply {
            rvCar.layoutManager = LinearLayoutManager(requireContext())
            rvCar.adapter = CarAdapter(data,listener)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.addMenu){
            var nav: NavDirections
            val navController = findNavController()
            navController.graph.findNode(R.id.addCarFragment)?.label = "Add Car"
            if(repository.getAuth()) nav = CarFragmentDirections.actionCarFragmentToAddCarFragment()
            else nav = CarFragmentDirections.actionCarFragmentToLoginFragment("Car")
            navController.navigate(
                nav
            )
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun itemClickListener(view: View, item: Car) {
        findNavController().navigate(CarFragmentDirections.actionCarFragmentToCarDetailsFragment(item))
    }


}