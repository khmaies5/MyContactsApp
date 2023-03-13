package com.khmaies.mycontacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.khmaies.mycontacts.databinding.FragmentAddContactBinding
import com.khmaies.mycontacts.model.Contact
import com.khmaies.mycontacts.viewmodel.ContactViewModel
import com.khmaies.mycontacts.viewmodel.ContactViewModelFactory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddContactFragment : Fragment() {

    private var _binding: FragmentAddContactBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: ContactViewModel by viewModels {
        ContactViewModelFactory.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            submitButton.setOnClickListener {
                val contact = Contact(
                    name = nameInput.text.toString(),
                    lastName = lastNameInput.text.toString(),
                    number = phoneNumberInput.text.toString()
                )
                viewModel.insert(contact)
                findNavController().navigate(R.id.action_AddContactFragment_to_ContactsFragment)

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}