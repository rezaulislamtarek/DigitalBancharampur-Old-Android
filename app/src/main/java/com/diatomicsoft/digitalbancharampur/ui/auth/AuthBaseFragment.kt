package com.diatomicsoft.digitalbancharampur.ui.auth

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.diatomicsoft.digitalbancharampur.R
import com.diatomicsoft.digitalbancharampur.model.network.Api
import com.diatomicsoft.digitalbancharampur.model.network.NetworkConnectionInterceptor

open class AuthBaseFragment : Fragment() {

    fun navigateToTargetFragment(type: String, nav: NavDirections) {
        var navController = findNavController()
        if (type == "Car") {
            navController.graph.findNode(R.id.addCarFragment)?.label = "Add Car"
            navController.navigate(
                nav
            )
            return
        }
        if (type == "Ambulance") {
            navController.graph.findNode(R.id.addAmbulanceFragment)?.label = "Add Ambulance"
            navController.navigate(
                nav
            )
            return
        }

        navController.graph.findNode(R.id.addPlaceFragment)?.label = "Add $type"
        navController.navigate(
            nav
        )

    }
}