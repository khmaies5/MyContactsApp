package com.khmaies.mycontacts

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.khmaies.mycontacts.adapter.ContactAdapter
import com.khmaies.mycontacts.databinding.FragmentContactsBinding
import com.khmaies.mycontacts.helper.SwipeHelper
import com.khmaies.mycontacts.model.Contact
import com.khmaies.mycontacts.viewmodel.ContactViewModel
import com.khmaies.mycontacts.viewmodel.ContactViewModelFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ContactsFragment : Fragment(), ContactAdapter.OnItemClickListener,
    SearchView.OnQueryTextListener, MenuProvider {

    private var _binding: FragmentContactsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: ContactViewModel by viewModels {
        ContactViewModelFactory.getInstance()
    }


    var list = mutableListOf<Contact>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.contactsRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ContactAdapter(this@ContactsFragment)
        }
        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.contactsRecyclerview) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                return listOf(deleteButton(position))
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.contactsRecyclerview)

        return binding.root
    }


    private fun deleteButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Delete",
            14.0f,
            R.color.home_color_red,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    showDeleteAlertDialog(position)
                }
            }
        )
    }

    private fun showDeleteAlertDialog(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val contact = viewModel.allContacts.value?.get(position)
        builder.setTitle("Alert")
        builder.setMessage(
            "Are you sure you want to delete ${
                getString(
                    R.string.full_name,
                    contact?.name,
                    contact?.lastName
                )
            } ?"
        )

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            contact?.let {
                viewModel.delete(it)
            }
            dialog.dismiss()
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.allContacts.observe(viewLifecycleOwner) { contacts ->
            contacts?.let {
                (binding.contactsRecyclerview.adapter as ContactAdapter).updateList(it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(contact: Contact) {
        val action =
            ContactsFragmentDirections.actionContactsFragmentToContactDetailFragment(contact)
        findNavController().navigate(action)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        if (query?.isNotEmpty() == true) {
            viewModel.search(query.toString())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.search(newText.toString()).observe(viewLifecycleOwner) { contacts ->
            (binding.contactsRecyclerview.adapter as ContactAdapter).updateList(contacts)
        }
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)

        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}