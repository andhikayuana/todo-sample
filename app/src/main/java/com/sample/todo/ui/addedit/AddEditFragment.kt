package com.sample.todo.ui.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.sample.todo.core.BaseFragment
import com.sample.todo.databinding.AddEditFragmentBinding
import com.sample.todo.ui.message.MessageManager
import com.sample.todo.ui.message.setUpSnackbar
import com.sample.todo.util.extension.hideKeyboard
import com.sample.todo.util.extension.observeEvent
import javax.inject.Inject

class AddEditFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: AddEditViewModel.Factory
    @Inject
    lateinit var messageManager: MessageManager
    private lateinit var binding: AddEditFragmentBinding
    private val addEditViewModel: AddEditViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddEditFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = addEditViewModel
            lifecycleOwner = viewLifecycleOwner
            state = withState(addEditViewModel) { it }
        }
        setUpSnackbar(
            messageManager = messageManager,
            snackbarMessage = addEditViewModel.snackBarMessage,
            fadingSnackbar = binding.snackBar
        )
        addEditViewModel.apply {
            navigateUpEvent.observeEvent(viewLifecycleOwner) {
                binding.root.hideKeyboard()
                findNavController().navigateUp()
            }
        }
        return binding.root
    }

    override fun invalidate() {
        withState(addEditViewModel) {
            binding.state = it
        }
    }
}