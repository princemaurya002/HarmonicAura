package com.princemaurya.harmonicaura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.children
import com.princemaurya.harmonicaura.utils.AnimationUtils
import com.princemaurya.harmonicaura.utils.HapticUtils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PenFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_pen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyAnimations(view)
    }

    private fun applyAnimations(view: View) {
        // Animate header
        view.findViewById<TextView>(R.id.title_moments)?.let {
            AnimationUtils.animateFadeIn(it, 300L)
        }

        // Animate moments container
        view.findViewById<CardView>(R.id.moments_container)?.let { container ->
            AnimationUtils.animateSlideUp(container, 400L)
            
            // Animate each moment with staggered delay
            container.findViewById<LinearLayout>(R.id.moments_list)?.let { momentsList ->
                momentsList.children.forEachIndexed { index, moment ->
                    AnimationUtils.animateSlideUp(moment, 500L + (index * 100L))
                    
                    // Add click animation and haptic feedback
                    moment.setOnClickListener {
                        AnimationUtils.animateButtonClick(it) {
                            HapticUtils.performViewHapticFeedback(it, HapticUtils.HapticType.LIGHT)
                            // Handle moment click
                        }
                    }
                }
            }
        }

        // Add ripple effect to action buttons
        view.findViewById<ViewGroup>(R.id.moments_container)?.let { container ->
            container.children.forEach { child ->
                if (child is LinearLayout) {
                    child.children.forEach { button ->
                        button.setOnClickListener {
                            AnimationUtils.createRippleEffect(it) {
                                HapticUtils.performViewHapticFeedback(it, HapticUtils.HapticType.LIGHT)
                                // Handle button click
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PenFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}