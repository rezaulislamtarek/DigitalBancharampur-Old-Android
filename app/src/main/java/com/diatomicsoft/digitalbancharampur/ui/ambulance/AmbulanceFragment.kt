package com.diatomicsoft.digitalbancharampur.ui.ambulance

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.adapter.AmbulanceAdapter
import com.diatomicsoft.digitalbancharampur.adapter.PlaceAdapterHorizontal
import com.diatomicsoft.digitalbancharampur.databinding.FragmentAmbulanceBinding
import com.diatomicsoft.digitalbancharampur.interfaceutil.AmbulanceClickListener
import com.diatomicsoft.digitalbancharampur.interfaceutil.ResponseListener
import com.diatomicsoft.digitalbancharampur.model.data.Ambulance
import com.diatomicsoft.digitalbancharampur.model.data.CommonResponse
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.NetworkConnectionInterceptor
import com.diatomicsoft.digitalbancharampur.model.repository.AmbulanceRepository
import com.diatomicsoft.digitalbancharampur.util.toastSnack

class AmbulanceFragment : Fragment(), AmbulanceClickListener, ResponseListener {
    lateinit var binding: FragmentAmbulanceBinding
    lateinit var repository: AmbulanceRepository
    lateinit var model: AmbulanceViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAmbulanceBinding.inflate(layoutInflater, container, false)
        val interceptor = NetworkConnectionInterceptor(requireContext())
        val api = Api(interceptor)
        repository = AmbulanceRepository(api, requireContext())
        val factory = AmbulanceViewModelFactory(repository)
        model = ViewModelProvider(this, factory).get(AmbulanceViewModel::class.java)
        binding.vm = model
        binding.lifecycleOwner = this
        model.ambulanceListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        model.ambulanceList.observe(viewLifecycleOwner, Observer {
            binding.shimmer.visibility = View.GONE
            initRecyclerView(it)
        })

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView(data: List<Ambulance>) {
        binding.rvAmbulance.also {
            it.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            it.adapter = AmbulanceAdapter(data, this)
        }
    }

    override fun ambulanceClickListener(view: View, item: Ambulance) {
        findNavController().navigate(
            AmbulanceFragmentDirections.actionAmbulanceFragmentToAmbulanceDetailsFragment(
                item
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == R.id.addMenu) {
            if (repository.getAuth()) findNavController().navigate(AmbulanceFragmentDirections.actionAmbulanceFragmentToAddAmbulanceFragment())
            else {
                findNavController().navigate(AmbulanceFragmentDirections.actionAmbulanceFragmentToLoginFragment("Ambulance"))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStarted() {
        binding.shimmer.visibility = View.VISIBLE
        binding.shimmer.startShimmer()
    }

    override fun onError(message: String) {
        binding.shimmer.visibility = View.GONE
        binding.rl.toastSnack(message)
    }

    override fun onSuccess(response: CommonResponse) {

    }
}