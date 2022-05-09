package com.diatomicsoft.digitalbancharampur.ui.place

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.adapter.PlaceAdapter
import com.diatomicsoft.digitalbancharampur.databinding.FragmentPlaceBinding
import com.diatomicsoft.digitalbancharampur.interfaceutil.ItemClickListener
import com.diatomicsoft.digitalbancharampur.model.data.Item
import com.diatomicsoft.digitalbancharampur.model.data.Places
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.NetworkConnectionInterceptor
import com.diatomicsoft.digitalbancharampur.model.repository.PlaceRepository
import com.diatomicsoft.digitalbancharampur.ui.auth.LoginFragmentDirections
import com.diatomicsoft.digitalbancharampur.ui.home.HomeFragmentDirections
import com.diatomicsoft.digitalbancharampur.util.FLAG_VERTICAL
import kotlinx.coroutines.launch

class PlaceFragment : Fragment(), ItemClickListener {
    lateinit var binding: FragmentPlaceBinding
    lateinit var viewModel: PlaceViewModel
    lateinit var repository: PlaceRepository
    private val args: PlaceFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlaceBinding.inflate(inflater, container, false)
        val interceptor = NetworkConnectionInterceptor(requireContext())
        val api = Api(interceptor)
        repository = PlaceRepository(api, requireContext())
        val factory = PlaceViewModelFactory(repository, args.type)
        viewModel = ViewModelProvider(this, factory).get(PlaceViewModel::class.java)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        binding.shimmer.visibility = View.VISIBLE
        binding.shimmer.startShimmer()


        viewModel.placeList.observe(viewLifecycleOwner, Observer {
            binding.shimmer.visibility = View.GONE
            initRecyclerView(it)
        })

        return binding.root
    }


    private fun initRecyclerView(data: List<Places>) {
        val listener: ItemClickListener = this
        binding?.apply {
            rv.layoutManager = LinearLayoutManager(requireContext())
            rv.adapter = PlaceAdapter(data, listener)
        }
    }

    override fun itemClickListener(view: View, item: Item) {

    }

    override fun placeClickListener(view: View, item: Places) {
        val navController = findNavController()
        navController.graph.findNode(R.id.placeDetailsFragment)?.label = item.title
        var nav: NavDirections =
            PlaceFragmentDirections.actionPlaceFragmentToPlaceDetailsFragment(item)
        navController.navigate(
            nav
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.placeadd, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.addPlaceMenu) {
            if (repository.getAuth()) {

                val navController = findNavController()
                navController.graph.findNode(R.id.addPlaceFragment)?.label = "Add "+args.type
                var nav: NavDirections = PlaceFragmentDirections.actionPlaceFragmentToAddPlaceFragment(args.type)
                navController.navigate(
                    nav
                )
            }
            else {
                findNavController().navigate(
                    PlaceFragmentDirections.actionPlaceFragmentToLoginFragment(
                        args.type
                    )
                )
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}