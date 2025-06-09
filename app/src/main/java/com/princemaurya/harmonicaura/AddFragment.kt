package com.princemaurya.harmonicaura

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.princemaurya.harmonicaura.adapters.AuraDateAdapter
import com.princemaurya.harmonicaura.utils.AnimationUtils
import com.princemaurya.harmonicaura.utils.HapticUtils
import java.util.Calendar
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {
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
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyEntranceAnimations(view)

        // Energy centers tags
        val energyCenters = listOf(
            "Calmness", "Serenity", "Balance",
            "Clarity", "Relaxation", "Mindfulness",
            "Peace", "Tranquility", "Inner harmony"
        )
        val flexboxEnergyCenters = view.findViewById<FlexboxLayout>(R.id.flexbox_energy_centers)
        energyCenters.forEachIndexed { index, tagText ->
            val tagView = LayoutInflater.from(context).inflate(R.layout.item_energy_center_tag, flexboxEnergyCenters, false) as TextView
            tagView.text = tagText
            flexboxEnergyCenters.addView(tagView)

            AnimationUtils.animateScaleIn(tagView)

            tagView.setOnClickListener {
                AnimationUtils.animateButtonClick(it) {
                    HapticUtils.performViewHapticFeedback(it, HapticUtils.HapticType.LIGHT)
                    // Handle tag click
                }
            }
        }

        // Aura date picker
        val recyclerAuraDates = view.findViewById<RecyclerView>(R.id.recycler_aura_dates)
        recyclerAuraDates.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val auraDateAdapter = AuraDateAdapter {
            // Handle aura date selection
        }
        recyclerAuraDates.adapter = auraDateAdapter

        // Scroll to today's date (middle of the list)
        recyclerAuraDates.post {
            val today = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0) }.time
            val todayPosition = auraDateAdapter.dates.indexOfFirst { 
                val cal = Calendar.getInstance().apply { time = it; set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0) }
                cal.time == today
            }
            if (todayPosition != -1) {
                recyclerAuraDates.scrollToPosition(todayPosition)
            }
        }

        // Vibration icons
        val vibrationIcons = listOf(
            R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground
        )
        val layoutVibrationIcons = view.findViewById<LinearLayout>(R.id.layout_vibration_icons)
        vibrationIcons.forEachIndexed { index, iconResId ->
            val iconView = LayoutInflater.from(context).inflate(R.layout.item_vibration_icon, layoutVibrationIcons, false) as ImageView
            iconView.setImageResource(iconResId)
            layoutVibrationIcons.addView(iconView)

            AnimationUtils.animateScaleIn(iconView)

            iconView.setOnClickListener {
                AnimationUtils.animateButtonClick(it) {
                    HapticUtils.performViewHapticFeedback(it, HapticUtils.HapticType.LIGHT)
                    // Handle icon click
                }
            }
        }

        // Alignment grid dates
        val gridAlignmentDates = view.findViewById<GridLayout>(R.id.grid_alignment_dates)
        val alignmentDates = listOf(
            "30", "31", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10", "11",
            "12", "13", "14", "15", "16"
        )
        alignmentDates.forEachIndexed { index, dateText ->
            val dateView = LayoutInflater.from(context).inflate(R.layout.item_alignment_date, gridAlignmentDates, false) as TextView
            dateView.text = dateText
            gridAlignmentDates.addView(dateView)

            // Set selected state for specific dates (e.g., 12, 13, 14, 15)
            if (dateText in listOf("12", "13", "14", "15")) {
                dateView.background = context?.getDrawable(R.drawable.bg_alignment_selected)
                dateView.setTextColor(resources.getColor(R.color.dark_gray, null))
            } else {
                dateView.background = context?.getDrawable(R.drawable.bg_alignment_unselected)
                dateView.setTextColor(resources.getColor(R.color.grey_text, null))
            }

            AnimationUtils.animateScaleIn(dateView)

            dateView.setOnClickListener {
                AnimationUtils.animateButtonClick(it) { 
                    HapticUtils.performViewHapticFeedback(it, HapticUtils.HapticType.LIGHT)
                    // Handle alignment date click
                }
            }
        }

        // Explore button
        val buttonExplore = view.findViewById<Button>(R.id.button_explore)
        buttonExplore.setOnClickListener {
            AnimationUtils.animateButtonClick(it) {
                HapticUtils.performViewHapticFeedback(it, HapticUtils.HapticType.MEDIUM)
                // Handle explore button click (e.g., navigate)
            }
        }
    }

    private fun applyEntranceAnimations(view: View) {
        // Animate section titles
        view.findViewById<TextView>(R.id.title_chakra_visualization)?.let {
            AnimationUtils.animateFadeIn(it, 300L)
        }
        view.findViewById<TextView>(R.id.title_energy_centers)?.let {
            AnimationUtils.animateFadeIn(it, 400L)
        }
        view.findViewById<TextView>(R.id.title_aura)?.let {
            AnimationUtils.animateFadeIn(it, 500L)
        }
        view.findViewById<TextView>(R.id.title_vibratio)?.let {
            AnimationUtils.animateFadeIn(it, 600L)
        }
        view.findViewById<TextView>(R.id.title_alignment_grid)?.let {
            AnimationUtils.animateFadeIn(it, 700L)
        }

        // Animate the main layout elements with staggered delay for entrance
        // Individual elements are animated in their respective sections.
        // No direct animation for the entire main_layout to avoid double animations.
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}