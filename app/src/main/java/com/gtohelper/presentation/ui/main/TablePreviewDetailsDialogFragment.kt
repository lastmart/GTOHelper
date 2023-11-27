package com.gtohelper.presentation.ui.main

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout.LayoutParams
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.gtohelper.databinding.DialogFragmentTablePreviewDetailsBinding


class TablePreviewDetailsDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentTablePreviewDetailsBinding
    private lateinit var title: String
    private lateinit var description: String

    companion object {
        const val TITLE_ARG = "title"
        const val DESCRIPTION_ARG = "description"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogFragmentTablePreviewDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        title = arguments?.getString(TITLE_ARG) ?: "Unknown"
        description = arguments?.getString(DESCRIPTION_ARG) ?: "Unknown"

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE);
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpFields()

        binding.buttonView.setOnClickListener {
            Toast.makeText(requireContext(), "View pressed", Toast.LENGTH_SHORT).show()
            hideDialogFragment()
        }

        binding.buttonDelete.setOnClickListener {
            Toast.makeText(requireContext(), "Delete pressed", Toast.LENGTH_SHORT).show()
            hideDialogFragment()
        }

        binding.buttonEdit.setOnClickListener {
            Toast.makeText(requireContext(), "Edit pressed", Toast.LENGTH_SHORT).show()
            hideDialogFragment()
        }

        binding.buttonDownload.setOnClickListener {
            Toast.makeText(requireContext(), "Download pressed", Toast.LENGTH_SHORT).show()
            hideDialogFragment()
        }
    }

    private fun setUpFields() {
        binding.editTextTitle.editText?.setText(title)
        binding.editTextDescription.editText?.setText(description)
    }

    private fun hideDialogFragment() {
        findNavController().navigateUp()
    }

    override fun onResume() {
        super.onResume()
        dialog!!.window!!.apply {
            setLayout(800, LayoutParams.WRAP_CONTENT)
            //    setGravity(Gravity.CENTER)
        }
    }
}

val Int.dp get() = (this / Resources.getSystem().displayMetrics.density).toInt()
