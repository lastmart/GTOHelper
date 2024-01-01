//package com.gtohelper.presentation.ui.disciplines_list.add_results
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.findNavController
//import com.gtohelper.databinding.FragmentAddResultsBinding
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class AddResultsFragment : Fragment() {
//
//    private val viewModel: AddResultsViewModel by viewModels()
//    private val binding by lazy { FragmentAddResultsBinding.inflate(layoutInflater) }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return binding.myCompose.apply {
//            setContent {
//                AddResultsScreen(navController = findNavController(), resultsViewModel = viewModel)
//            }
//        }
//    }
//}