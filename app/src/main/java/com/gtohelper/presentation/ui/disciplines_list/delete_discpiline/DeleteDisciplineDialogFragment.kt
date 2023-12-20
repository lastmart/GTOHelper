package com.gtohelper.presentation.ui.disciplines_list.delete_discpiline

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.gtohelper.databinding.DialogFragmentDeleteDisciplineBinding

class DeleteDisciplineDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentDeleteDisciplineBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentDeleteDisciplineBinding.inflate(layoutInflater)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        initButtons()

        return binding.root
    }

    private fun initButtons() {
        binding.buttonCancel.setOnClickListener {
            hideDialogFragment()
        }

        binding.buttonDelete.setOnClickListener {
            Toast.makeText(requireContext(), "Delete", Toast.LENGTH_SHORT).show()
            hideDialogFragment()
        }
    }

    private fun hideDialogFragment() {
        findNavController().navigateUp()
    }
}