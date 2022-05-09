package com.diatomicsoft.digitalbancharampur.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.diatomicsoft.digitalbancharampur.MainActivity
import com.diatomicsoft.digitalbancharampur.databinding.FragmentSignupBinding
import com.diatomicsoft.digitalbancharampur.model.data.Auth
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.NetworkConnectionInterceptor
import com.diatomicsoft.digitalbancharampur.model.repository.AuthRepository
import com.diatomicsoft.digitalbancharampur.util.toastSnack
import com.diatomicsoft.digitalbancharampur.util.Coroutines.main
import com.diatomicsoft.digitalbancharampur.util.hide
import com.diatomicsoft.digitalbancharampur.util.show

class SignupFragment : AuthBaseFragment(), AuthListener {
    lateinit var binding: FragmentSignupBinding
    lateinit var model: AuthViewModel
    lateinit var repository: AuthRepository
    val args: SignupFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        val interceptor = NetworkConnectionInterceptor(requireContext())
        val api = Api(interceptor)
        repository = AuthRepository(api, requireContext())
        val factory = AuthViewModelFactory(repository)
        model = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        model.authListener = this
        binding.vm = model
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val act = activity as MainActivity
        binding.ivImagePicker.setOnClickListener { pickImage(act) }
        act.imageUrl.observe(viewLifecycleOwner, Observer {
            model.imageUri.value = it
            binding.ivImagePicker.apply {
                setImageURI(it)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            model.imageFile.value = act.getFile(it, repository.getContributorId())
        })
        binding.btnSignup.setOnClickListener { main { model.signUp() } }
    }

    private fun pickImage(act: MainActivity) {
        act.getContent.launch("image/*")
    }

    override fun onStarted() {
        binding.pbar.show()
    }

    override fun onError(error: String) {
        binding.rl.toastSnack("Error: $error")
        binding.pbar.hide()
    }

    override fun onSuccess(auth: Auth) {
        /*
        main { model.login() }
        var nav: NavDirections
        if (args.type == "Car") {
            nav =
                SignupFragmentDirections.actionSignupFragmentToAddCarFragment()
        } else {
            nav =
                SignupFragmentDirections.actionSignupFragmentToAddPlaceFragment(args.type)
        }
        navigateToTargetFragment(args.type, nav)
        Log.d("Signup", auth.message)
         */
        binding.pbar.hide()
        activity?.onBackPressed()
    }
}