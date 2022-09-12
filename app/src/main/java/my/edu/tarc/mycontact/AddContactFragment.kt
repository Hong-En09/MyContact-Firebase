package my.edu.tarc.mycontact

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import my.edu.tarc.mycontact.databinding.FragmentAddContactBinding
import my.edu.tarc.mycontact.entity.Contact


class AddContactFragment : Fragment() {

    private var _binding: FragmentAddContactBinding? = null
    //Create a reference to view model
    private val contactViewModel:ContactViewModel by activityViewModels()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_add).isVisible = false
        menu.findItem(R.id.action_profile).isVisible = false
        menu.findItem(R.id.action_settings).isVisible = false
        menu.findItem(R.id.action_upload).isVisible = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        _binding = FragmentAddContactBinding.inflate( inflater, container, false)
        return binding.root
        return inflater.inflate(R.layout.fragment_add_contact2, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_save -> {
                //todo: add contact record to a data storage
                val name = binding.editTextTextPersonName.text.toString()
                val phone = binding.editTextPhone2.text.toString()
                val newContact = Contact(name, phone)

                contactViewModel.insert(newContact)

                Toast.makeText(context, "Profile Saved",
                Toast.LENGTH_SHORT).show()

                //Return back to the First Fragment
                //val navController = activity?.findNavController(R.id.nav_host_fragment_content_main)
                //navController?.navigateUp()
                findNavController().navigateUp()

                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}