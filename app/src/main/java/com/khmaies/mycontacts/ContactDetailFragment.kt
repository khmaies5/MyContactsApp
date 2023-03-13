package com.khmaies.mycontacts

import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.khmaies.mycontacts.databinding.FragmentContactDetailBinding
import com.khmaies.mycontacts.util.TextAvatar
import com.khmaies.mycontacts.viewmodel.ContactViewModel
import com.khmaies.mycontacts.viewmodel.ContactViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [ContactDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactDetailFragment : Fragment() {

    private var _binding: FragmentContactDetailBinding? = null

    private val args: ContactDetailFragmentArgs by navArgs()

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
        // Inflate the layout for this fragment
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)

        binding.apply {
            nameTextView.text = args.contact.name
            lastNameTextView.text = args.contact.lastName
            phoneNumberTextView.text = args.contact.number
            imageView.setImageBitmap(TextAvatar.createAvatarBitmap("${args.contact.name.first()}${args.contact.lastName.first()}"))
            callButton.setOnClickListener {
                val intent = Intent(ACTION_DIAL)
                intent.data = Uri.parse("tel:${args.contact.number}")
                startActivity(intent)
            }
        }

        return binding.root
    }
}