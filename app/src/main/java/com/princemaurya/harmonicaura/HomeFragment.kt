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
import com.princemaurya.harmonicaura.auth.FirebaseAuthManager
import com.princemaurya.harmonicaura.utils.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.princemaurya.harmonicaura.adapters.DateAdapter
import com.princemaurya.harmonicaura.utils.HapticUtils
import java.util.*

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
        
        // Display user name
        displayUserName(view)
        
        // Apply entrance animations
        applyEntranceAnimations(view)
        
        // Find the chakra card view
        view.findViewById<CardView>(R.id.chakra_card)?.setOnClickListener {
            AnimationUtils.animateCardClick(it as CardView) {
                // Navigate to ChakraFragment
                findNavController().navigate(R.id.action_homeFragment_to_chakraFragment)
            }
        }

        // Find the healing card view
        view.findViewById<CardView>(R.id.healing_card)?.setOnClickListener {
            AnimationUtils.animateCardClick(it as CardView) {
                // Navigate to HealingFrequenciesFragment
                findNavController().navigate(R.id.action_homeFragment_to_healingFrequenciesFragment)
            }
        }

        // Find the mindfulness card view
        view.findViewById<CardView>(R.id.mindfulness_card)?.setOnClickListener {
            AnimationUtils.animateCardClick(it as CardView) {
                // Navigate to MeditationsFragment
                findNavController().navigate(R.id.action_homeFragment_to_meditationsFragment)
            }
        }

        // Find the insights section
        view.findViewById<LinearLayout>(R.id.insights_section)?.setOnClickListener {
            AnimationUtils.createRippleEffect(it) {
                // Navigate to InsightsFragment
                findNavController().navigate(R.id.action_homeFragment_to_insightsFragment)
            }
        }

        // Find the personalize and explore text views
        view.findViewById<TextView>(R.id.personalize_text)?.setOnClickListener {
            AnimationUtils.animateButtonClick(it) {
                // Navigate to UserProfileFragment
                findNavController().navigate(R.id.action_homeFragment_to_userProfileFragment)
            }
        }

        view.findViewById<TextView>(R.id.explore_text)?.setOnClickListener {
            AnimationUtils.animateButtonClick(it) {
                // Navigate to StatsFragment
                findNavController().navigate(R.id.action_homeFragment_to_statsFragment)
            }
        }

        setupDatePicker(view)
    }

    private fun applyEntranceAnimations(view: View) {
        // Animate welcome section
        val welcomeSection = view.findViewById<LinearLayout>(R.id.welcome_section)
        welcomeSection?.let {
            AnimationUtils.animateFadeIn(it, 800)
        }
        
        // Animate cards with staggered delay
        val chakraCard = view.findViewById<CardView>(R.id.chakra_card)
        val healingCard = view.findViewById<CardView>(R.id.healing_card)
        val mindfulnessCard = view.findViewById<CardView>(R.id.mindfulness_card)
        
        chakraCard?.let {
            AnimationUtils.animateSlideUp(it, 200)
        }
        
        healingCard?.let {
            AnimationUtils.animateSlideUp(it, 400)
        }
        
        mindfulnessCard?.let {
            AnimationUtils.animateSlideUp(it, 600)
        }
        
        // Animate insights section
        val insightsSection = view.findViewById<LinearLayout>(R.id.insights_section)
        insightsSection?.let {
            AnimationUtils.animateSlideUp(it, 800)
        }
    }

    private fun displayUserName(view: View) {
        val authManager = FirebaseAuthManager.getInstance(requireContext())
        val currentUser = authManager.getCurrentUser()
        
        val userNameTextView = view.findViewById<TextView>(R.id.user_name_text)
        
        if (currentUser != null) {
            // Try to get display name first, then email, then "User"
            val displayName = currentUser.displayName
            val email = currentUser.email
            
            val userName = when {
                !displayName.isNullOrBlank() -> displayName
                !email.isNullOrBlank() -> email.substringBefore('@') // Use part before @ from email
                else -> "User"
            }
            
            userNameTextView.text = userName
        } else {
            // If no user is logged in, show "Guest"
            userNameTextView.text = "Guest"
        }
    }

    private fun setupDatePicker(view: View) {
        val datePicker = view.findViewById<RecyclerView>(R.id.date_picker)
        datePicker.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        
        val dateAdapter = DateAdapter { selectedDate ->
            // Handle date selection
            HapticUtils.performViewHapticFeedback(view, HapticUtils.HapticType.LIGHT)
            // TODO: Update UI based on selected date
        }
        
        datePicker.adapter = dateAdapter
        
        // Scroll to today's date (middle of the list)
        datePicker.post {
            val middlePosition = dateAdapter.itemCount / 2
            datePicker.smoothScrollToPosition(middlePosition)
        }

        // Add entrance animation
        AnimationUtils.animateSlideUp(datePicker, 400)
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