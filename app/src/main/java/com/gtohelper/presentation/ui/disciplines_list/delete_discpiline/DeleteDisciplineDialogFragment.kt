package com.gtohelper.presentation.ui.disciplines_list.delete_discpiline

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
import com.gtohelper.databinding.DialogFragmentDeleteDisciplineBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeleteDisciplineDialogFragment() : DialogFragment() {

    companion object {
        const val DISCIPLINE_ARG = "DISCIPLINE"
        const val DELETE_RESULT = "DELETE_RESULT"
    }

    private val viewModel: DeleteDisciplineViewModel by viewModels()

    private lateinit var binding: DialogFragmentDeleteDisciplineBinding
    private lateinit var disciplineName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentDeleteDisciplineBinding.inflate(layoutInflater)

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
        disciplineName = arguments?.getString(DISCIPLINE_ARG)!!
    }

    private fun initButtons() {
        binding.buttonCancel.setOnClickListener {
            hideDialogFragment()
        }

        binding.buttonDelete.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                viewModel.deleteDisciplineByName(disciplineName)
            }

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