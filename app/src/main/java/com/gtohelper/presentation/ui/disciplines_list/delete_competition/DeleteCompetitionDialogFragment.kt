package com.gtohelper.presentation.ui.disciplines_list.delete_competition

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gtohelper.databinding.DialogFragmentDeleteCompetitionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteCompetitionDialogFragment : DialogFragment() {

    companion object {
        const val COMPETITION_ARG = "COMPETITION"
        const val DELETE_RESULT = "DELETE_RESULT"
    }

    private val viewModel: DeleteCompetitionViewModel by viewModels()

    private lateinit var binding: DialogFragmentDeleteCompetitionBinding
    private lateinit var competitionName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentDeleteCompetitionBinding.inflate(layoutInflater)

        setTransparentBackground()
        initArgs()
        initButtons()

        return binding.root
    }

    private fun setTransparentBackground() {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
    }

    private fun initArgs() {
        competitionName = arguments?.getString(COMPETITION_ARG)!!
    }

    private fun initButtons() {
        binding.buttonCancel.setOnClickListener {
            hideDialogFragment()
        }

        binding.buttonDelete.setOnClickListener {
            val bundle = Bundle().apply {
                putBoolean(DELETE_RESULT, true)
            }
            parentFragmentManager.setFragmentResult(DELETE_RESULT, bundle)

            hideDialogFragment()
        }
    }

    private fun hideDialogFragment() {
        findNavController().navigateUp()
    }
}