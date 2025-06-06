package com.princemaurya.harmonicaura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.cardview.widget.CardView
import android.widget.LinearLayout
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Find the chakra card view
        view.findViewById<CardView>(R.id.chakra_card)?.setOnClickListener {
            // Navigate to ChakraFragment
            findNavController().navigate(R.id.action_homeFragment_to_chakraFragment)
        }

        // Find the healing card view
        view.findViewById<CardView>(R.id.healing_card)?.setOnClickListener {
            // Navigate to HealingFrequenciesFragment
            findNavController().navigate(R.id.action_homeFragment_to_healingFrequenciesFragment)
        }

        // Find the mindfulness card view
        view.findViewById<CardView>(R.id.mindfulness_card)?.setOnClickListener {
            // Navigate to MeditationsFragment
            findNavController().navigate(R.id.action_homeFragment_to_meditationsFragment)
        }

        // Find the insights section
        view.findViewById<LinearLayout>(R.id.insights_section)?.setOnClickListener {
            // Navigate to InsightsFragment
            findNavController().navigate(R.id.action_homeFragment_to_insightsFragment)
        }

        // Find the personalize and explore text views
        view.findViewById<TextView>(R.id.personalize_text)?.setOnClickListener {
            // Navigate to ProfileFragment
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        view.findViewById<TextView>(R.id.explore_text)?.setOnClickListener {
            // Navigate to StatsFragment
            findNavController().navigate(R.id.action_homeFragment_to_statsFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}