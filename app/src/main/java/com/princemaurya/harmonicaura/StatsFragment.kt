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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatsFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyAnimations(view)
    }

    private fun applyAnimations(view: View) {
        // Animate section titles
        view.findViewById<TextView>(R.id.title_chakra)?.let {
            AnimationUtils.animateFadeIn(it, 300L)
        }

        // Animate "Balance and harmony" section
        view.findViewById<TextView>(R.id.title_balance)?.let {
            AnimationUtils.animateFadeIn(it, 400L)
        }
        view.findViewById<LinearLayout>(R.id.balance_cards)?.let { container ->
            container.children.forEachIndexed { index, child ->
                if (child is CardView) {
                    AnimationUtils.animateSlideUp(child, 500L + (index * 100L))
                    child.setOnClickListener {
                        AnimationUtils.animateCardClick(it as CardView)
                        // Handle card click
                    }
                } else if (child is LinearLayout) {
                    child.children.forEach { innerChild ->
                        if (innerChild is CardView) {
                            AnimationUtils.animateSlideUp(innerChild, 500L + (index * 100L))
                            innerChild.setOnClickListener {
                                AnimationUtils.animateCardClick(it as CardView)
                                // Handle card click
                            }
                        }
                    }
                }
            }
        }

        // Animate "Guided sessions" section
        view.findViewById<TextView>(R.id.title_guided)?.let {
            AnimationUtils.animateFadeIn(it, 800L)
        }
        view.findViewById<LinearLayout>(R.id.guided_cards)?.let { container ->
            container.children.forEachIndexed { index, child ->
                if (child is CardView) {
                    AnimationUtils.animateSlideUp(child, 900L + (index * 100L))
                    child.setOnClickListener {
                        AnimationUtils.animateCardClick(it as CardView)
                        // Handle card click
                    }
                } else if (child is LinearLayout) {
                    child.children.forEach { innerChild ->
                        if (innerChild is CardView) {
                            AnimationUtils.animateSlideUp(innerChild, 900L + (index * 100L))
                            innerChild.setOnClickListener {
                                AnimationUtils.animateCardClick(it as CardView)
                                // Handle card click
                            }
                        }
                    }
                }
            }
        }

        // Animate "Personal growth" section
        view.findViewById<TextView>(R.id.title_personal)?.let {
            AnimationUtils.animateFadeIn(it, 1200L)
        }
        view.findViewById<LinearLayout>(R.id.personal_cards)?.let { container ->
            container.children.forEachIndexed { index, child ->
                if (child is CardView) {
                    AnimationUtils.animateSlideUp(child, 1300L + (index * 100L))
                    child.setOnClickListener {
                        AnimationUtils.animateCardClick(it as CardView)
                        // Handle card click
                    }
                } else if (child is LinearLayout) {
                    child.children.forEach { innerChild ->
                        if (innerChild is CardView) {
                            AnimationUtils.animateSlideUp(innerChild, 1300L + (index * 100L))
                            innerChild.setOnClickListener {
                                AnimationUtils.animateCardClick(it as CardView)
                                // Handle card click
                            }
                        }
                    }
                }
            }
        }

        // Add click animations to all cards within stats_container (if not already handled by section-specific loops)
        // This part needs to iterate carefully to avoid re-adding listeners or redundant animations.
        // Given the complex nested structure, direct targeting within sections is more robust.
        // The previous broad iteration is removed to prevent errors and ensure proper animation calls.
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StatsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}