package com.diatomicsoft.digitalbancharampur.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.databinding.FragmentLoginBinding
import com.diatomicsoft.digitalbancharampur.model.data.Auth
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.NetworkConnectionInterceptor
import com.diatomicsoft.digitalbancharampur.model.repository.AuthRepository
import com.diatomicsoft.digitalbancharampur.ui.home.HomeFragmentDirections
import com.diatomicsoft.digitalbancharampur.ui.place.PlaceFragmentArgs
import com.diatomicsoft.digitalbancharampur.ui.place.PlaceFragmentDirections
import com.diatomicsoft.digitalbancharampur.util.Coroutines.main
import com.diatomicsoft.digitalbancharampur.util.hide
import com.diatomicsoft.digitalbancharampur.util.show
import com.diatomicsoft.digitalbancharampur.util.toast
import com.diatomicsoft.digitalbancharampur.util.toastSnack

class LoginFragment : AuthBaseFragment(), AuthListener {
    private val args: LoginFragmentArgs by navArgs()
    lateinit var binding: FragmentLoginBinding
    lateinit var model: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val interceptor = NetworkConnectionInterceptor(requireContext())
        val api = Api(interceptor)
        val repository = AuthRepository(api, requireContext())
        val factory = AuthViewModelFactory(repository)
        model = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        model.authListener = this

        binding.vm = model
        binding.lifecycleOwner = this

        binding.btnLogIn.setOnClickListener {
            main {
                model.login()
            }
        }

        binding.btnSignup.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToSignupFragment(
                    args.type
                )
            )
        }


        model.authResponse.observe(viewLifecycleOwner, Observer {
            // Toast.makeText(requireContext(),"Response "+it.message,Toast.LENGTH_SHORT).show()
            if (it.auth) {
                var nav: NavDirections? = null
                if(args.type == "Ambulance"){
                    nav = LoginFragmentDirections.actionLoginFragmentToAddAmbulanceFragment()
                }
                else if (args.type == "Car") {
                    nav =
                        LoginFragmentDirections.actionLoginFragmentToAddCarFragment()
                } else {
                    nav =
                        LoginFragmentDirections.actionLoginFragmentToAddPlaceFragment(args.type)
                }

                navigateToTargetFragment(args.type, nav)
            }
        })

        return binding.root
    }

    override fun onStarted() {
        binding.pbar.show()
    }

    override fun onError(error: String) {
        binding.rl.toastSnack(error)
        binding.pbar.hide()
    }

    override fun onSuccess(auth: Auth) {
        binding.pbar.hide()
    }
}