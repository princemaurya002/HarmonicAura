package com.princemaurya.harmonicaura

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ChakraFragment : Fragment() {

    private var currentChakraDay = 1
    private val totalChakraDays = 7

    // Chakra data
    private data class ChakraInfo(
        val name: String,
        val description: String,
        val frequency: Int,
        val color: String,
        val emotionalSymptoms: List<String>,
        val physicalManifestations: List<String>,
        val affirmation: String,
        val practices: List<String>
    )

    private val chakraData = mapOf(
        "Root Chakra" to ChakraInfo(
            name = "Root Chakra",
            description = "Grounding · Stability · Security",
            frequency = 396,
            color = "#FF5252",
            emotionalSymptoms = listOf(
                "Anxiety",
                "Fear",
                "Insecurity",
                "Financial worries",
                "Feeling disconnected"
            ),
            physicalManifestations = listOf(
                "Lower back pain",
                "Digestive issues",
                "Immune system problems",
                "Fatigue",
                "Weight issues"
            ),
            affirmation = "I am safe. I am secure. I am grounded.",
            practices = listOf(
                "Grounding meditation",
                "Yoga poses: Mountain, Warrior I",
                "Foods: Root vegetables, red fruits",
                "Walking barefoot on earth",
                "Red color therapy"
            )
        ),
        "Sacral Chakra" to ChakraInfo(
            name = "Sacral Chakra",
            description = "Creativity · Passion · Joy",
            frequency = 417,
            color = "#FF9800",
            emotionalSymptoms = listOf(
                "Emotional instability",
                "Creativity blocks",
                "Relationship issues",
                "Guilt",
                "Lack of passion"
            ),
            physicalManifestations = listOf(
                "Lower abdominal pain",
                "Reproductive issues",
                "Urinary problems",
                "Lower back pain",
                "Hip pain"
            ),
            affirmation = "I am creative. I am passionate. I am joyful.",
            practices = listOf(
                "Creative expression",
                "Yoga poses: Pigeon, Bound Angle",
                "Foods: Orange fruits, nuts",
                "Water therapy",
                "Orange color therapy"
            )
        ),
        "Solar Plexus Chakra" to ChakraInfo(
            name = "Solar Plexus Chakra",
            description = "Power · Confidence · Will",
            frequency = 528,
            color = "#FFC107",
            emotionalSymptoms = listOf(
                "Low self-esteem",
                "Lack of confidence",
                "Control issues",
                "Anger",
                "Indecision"
            ),
            physicalManifestations = listOf(
                "Digestive problems",
                "Stomach issues",
                "Liver problems",
                "Pancreatic issues",
                "Middle back pain"
            ),
            affirmation = "I am confident. I am powerful. I am worthy.",
            practices = listOf(
                "Power poses",
                "Yoga poses: Boat, Warrior III",
                "Foods: Yellow foods, grains",
                "Sun therapy",
                "Yellow color therapy"
            )
        ),
        "Heart Chakra" to ChakraInfo(
            name = "Heart Chakra",
            description = "Love · Compassion · Harmony",
            frequency = 639,
            color = "#4CAF50",
            emotionalSymptoms = listOf(
                "Difficulty giving/receiving love",
                "Grief",
                "Jealousy",
                "Loneliness",
                "Bitterness"
            ),
            physicalManifestations = listOf(
                "Heart problems",
                "Circulation issues",
                "Upper back pain",
                "Shoulder pain",
                "Respiratory issues"
            ),
            affirmation = "I am love. I am compassion. I am harmony.",
            practices = listOf(
                "Heart-opening meditation",
                "Yoga poses: Camel, Bridge",
                "Foods: Green vegetables, green tea",
                "Nature walks",
                "Green color therapy"
            )
        ),
        "Throat Chakra" to ChakraInfo(
            name = "Throat Chakra",
            description = "Expression · Truth · Communication",
            frequency = 741,
            color = "#2196F3",
            emotionalSymptoms = listOf(
                "Difficulty expressing truth",
                "Fear of speaking",
                "Communication issues",
                "Lying",
                "Gossiping"
            ),
            physicalManifestations = listOf(
                "Sore throat",
                "Thyroid issues",
                "Neck pain",
                "Voice problems",
                "Dental issues"
            ),
            affirmation = "I speak my truth. I express myself clearly. I am heard.",
            practices = listOf(
                "Chanting and singing",
                "Yoga poses: Shoulder Stand, Fish",
                "Foods: Blue fruits, herbal teas",
                "Voice exercises",
                "Blue color therapy"
            )
        ),
        "Third Eye Chakra" to ChakraInfo(
            name = "Third Eye Chakra",
            description = "Intuition · Wisdom · Insight",
            frequency = 852,
            color = "#673AB7",
            emotionalSymptoms = listOf(
                "Lack of clarity",
                "Poor intuition",
                "Confusion",
                "Nightmares",
                "Overthinking"
            ),
            physicalManifestations = listOf(
                "Headaches",
                "Eye problems",
                "Sinus issues",
                "Brain fog",
                "Sleep problems"
            ),
            affirmation = "I trust my intuition. I am wise. I see clearly.",
            practices = listOf(
                "Meditation",
                "Yoga poses: Child's Pose, Forward Fold",
                "Foods: Purple foods, dark chocolate",
                "Moon gazing",
                "Indigo color therapy"
            )
        ),
        "Crown Chakra" to ChakraInfo(
            name = "Crown Chakra",
            description = "Spirituality · Enlightenment · Unity",
            frequency = 963,
            color = "#9C27B0",
            emotionalSymptoms = listOf(
                "Spiritual disconnection",
                "Lack of purpose",
                "Depression",
                "Isolation",
                "Material attachment"
            ),
            physicalManifestations = listOf(
                "Headaches",
                "Neurological issues",
                "Dizziness",
                "Sensitivity to light/sound",
                "Sleep disorders"
            ),
            affirmation = "I am connected to the divine. I am one with all. I am peace.",
            practices = listOf(
                "Spiritual meditation",
                "Yoga poses: Headstand, Lotus",
                "Foods: Fasting, pure water",
                "Stargazing",
                "Violet/white color therapy"
            )
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chakra, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up chakra card click listeners
        setupChakraCards(view)
        
        // Set up scan button
        view.findViewById<Button>(R.id.start_scan_button)?.setOnClickListener {
            showChakraScanDialog()
        }

        // Set up journey button
        view.findViewById<Button>(R.id.start_journey_button)?.setOnClickListener {
            startDailyHealing()
        }

        // Set up journal save button
        view.findViewById<Button>(R.id.save_journal_button)?.setOnClickListener {
            saveJournalEntry(view)
        }

        // Set up waitlist button
        view.findViewById<Button>(R.id.join_waitlist_button)?.setOnClickListener {
            showWaitlistDialog()
        }

        // Update progress bar
        updateJourneyProgress(view)
    }

    private fun setupChakraCards(view: View) {
        chakraData.forEach { (chakraName, info) ->
            // Set up card click listener
            view.findViewById<View>(getChakraCardId(chakraName))?.setOnClickListener {
                showChakraDetails(info)
            }

            // Set up heal button click listener
            view.findViewById<Button>(getChakraHealButtonId(chakraName))?.setOnClickListener {
                startHealingSession(info)
            }
        }
    }

    private fun getChakraCardId(chakraName: String): Int {
        return when (chakraName) {
            "Root Chakra" -> R.id.root_chakra_card
            "Sacral Chakra" -> R.id.sacral_chakra_card
            "Solar Plexus Chakra" -> R.id.solar_plexus_chakra_card
            "Heart Chakra" -> R.id.heart_chakra_card
            "Throat Chakra" -> R.id.throat_chakra_card
            "Third Eye Chakra" -> R.id.third_eye_chakra_card
            "Crown Chakra" -> R.id.crown_chakra_card
            else -> throw IllegalArgumentException("Unknown chakra: $chakraName")
        }
    }

    private fun getChakraHealButtonId(chakraName: String): Int {
        return when (chakraName) {
            "Root Chakra" -> R.id.root_chakra_heal_button
            "Sacral Chakra" -> R.id.sacral_chakra_heal_button
            "Solar Plexus Chakra" -> R.id.solar_plexus_chakra_heal_button
            "Heart Chakra" -> R.id.heart_chakra_heal_button
            "Throat Chakra" -> R.id.throat_chakra_heal_button
            "Third Eye Chakra" -> R.id.third_eye_chakra_heal_button
            "Crown Chakra" -> R.id.crown_chakra_heal_button
            else -> throw IllegalArgumentException("Unknown chakra: $chakraName")
        }
    }

    private fun showChakraDetails(info: ChakraInfo) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(info.name)
            .setMessage("""
                ${info.description}
                
                Frequency: ${info.frequency} Hz
                
                Emotional Symptoms of Imbalance:
                ${info.emotionalSymptoms.joinToString("\n") { "• $it" }}
                
                Physical Manifestations:
                ${info.physicalManifestations.joinToString("\n") { "• $it" }}
                
                Daily Affirmation:
                "${info.affirmation}"
                
                Recommended Practices:
                ${info.practices.joinToString("\n") { "• $it" }}
            """.trimIndent())
            .setPositiveButton("Start Healing") { _, _ ->
                startHealingSession(info)
            }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun startHealingSession(info: ChakraInfo) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Start ${info.name} Healing Session")
            .setMessage("""
                This session will play a ${info.frequency}Hz frequency for 15 minutes to help balance your ${info.name}.
                
                Find a quiet place, sit comfortably, and focus on your breath.
                
                The session will automatically end after 15 minutes.
            """.trimIndent())
            .setPositiveButton("Begin Session") { _, _ ->
                ChakraHealingService.startService(requireContext(), info.name, info.frequency)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showChakraScanDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Chakra Balance Scan")
            .setMessage("""
                This quick scan will help assess your current chakra balance.
                
                Take a moment to breathe deeply and answer these questions honestly:
                
                1. Do you feel physically grounded and secure? (Root)
                2. Are you able to express your creativity freely? (Sacral)
                3. Do you feel confident in your personal power? (Solar Plexus)
                4. Is your heart open to giving and receiving love? (Heart)
                5. Can you express your truth clearly? (Throat)
                6. Do you trust your intuition? (Third Eye)
                7. Do you feel connected to something greater than yourself? (Crown)
            """.trimIndent())
            .setPositiveButton("Start Scan") { _, _ ->
                // TODO: Implement actual scan logic with scoring
                Toast.makeText(
                    requireContext(),
                    "Scanning your chakra balance...",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun startDailyHealing() {
        if (currentChakraDay <= totalChakraDays) {
            val chakraName = when (currentChakraDay) {
                1 -> "Root Chakra"
                2 -> "Sacral Chakra"
                3 -> "Solar Plexus Chakra"
                4 -> "Heart Chakra"
                5 -> "Throat Chakra"
                6 -> "Third Eye Chakra"
                7 -> "Crown Chakra"
                else -> "Unknown Chakra"
            }
            
            chakraData[chakraName]?.let { info ->
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Day $currentChakraDay: ${info.name}")
                    .setMessage("""
                        Ready to begin today's healing journey?
                        
                        This session will include:
                        • Guided meditation
                        • ${info.frequency}Hz healing frequency
                        • Energy balancing exercises
                        
                        Duration: 15 minutes
                        
                        Daily Affirmation:
                        "${info.affirmation}"
                    """.trimIndent())
                    .setPositiveButton("Begin Session") { _, _ ->
                        ChakraHealingService.startService(requireContext(), info.name, info.frequency)
                        currentChakraDay++
                        updateJourneyProgress(requireView())
                    }
                    .setNegativeButton("Not Now", null)
                    .show()
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Congratulations! You've completed the 7-day journey!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun saveJournalEntry(view: View) {
        val journalInput = view.findViewById<EditText>(R.id.journal_input)
        val entry = journalInput.text.toString()
        
        if (entry.isNotBlank()) {
            // TODO: Implement actual journal saving logic
            Toast.makeText(
                requireContext(),
                "Journal entry saved successfully!",
                Toast.LENGTH_SHORT
            ).show()
            
            // Clear input
            journalInput.text.clear()
            
            // Update prompt for next day
            updateJournalPrompt(view)
        } else {
            Toast.makeText(
                requireContext(),
                "Please write something before saving",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateJournalPrompt(view: View) {
        val promptText = view.findViewById<TextView>(R.id.journal_prompt)
        val prompts = listOf(
            "What made you feel grounded today?",
            "How did you express your creativity today?",
            "What empowered you today?",
            "How did you share love today?",
            "What truth did you express today?",
            "What insights did you receive today?",
            "How did you connect with your higher self today?"
        )
        
        promptText.text = prompts[(currentChakraDay - 1) % prompts.size]
    }

    private fun showWaitlistDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Join the Waitlist")
            .setMessage("""
                Be among the first to experience our revolutionary biofeedback technology!
                
                You'll receive:
                • Early access to the wearable
                • Exclusive beta testing opportunities
                • Special launch pricing
                
                We'll notify you when it's ready!
            """.trimIndent())
            .setPositiveButton("Join Waitlist") { _, _ ->
                // TODO: Implement actual waitlist signup
                Toast.makeText(
                    requireContext(),
                    "Thank you for joining the waitlist!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("Maybe Later", null)
            .show()
    }

    private fun updateJourneyProgress(view: View) {
        val progressBar = view.findViewById<android.widget.ProgressBar>(R.id.journey_progress)
        val progress = (currentChakraDay - 1) * 100 / totalChakraDays
        progressBar.progress = progress
    }

    override fun onDestroy() {
        super.onDestroy()
        ChakraHealingService.stopService(requireContext())
    }
} 