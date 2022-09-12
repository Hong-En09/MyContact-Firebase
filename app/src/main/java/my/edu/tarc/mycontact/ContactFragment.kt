package my.edu.tarc.mycontact

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.LinearLayoutManager
import my.edu.tarc.mycontact.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ContactFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val contactViewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Enable menu item
        setHasOptionsMenu(true)

        //OR, for newer versions of IDE
        //val menuHost: MenuHost = requireActivity()
        //menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        Log.d("onCreateView", "FirstFragment")

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when(item.itemId) {
            R.id.action_profile -> {
                val navController = activity?.findNavController(R.id.nav_host_fragment_content_main)
                navController?.navigate(R.id.action_ContactFragment_to_ProfileFragment)
                return true
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.action_save).isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        Log.d("onStart", "FirstFragment")
        super.onStart()
    }

    override fun onResume(){
        Log.d("onResume", "FirstFragment")
        super.onResume()

        //val contactAdapter = ContactAdapter(MainActivity.contactList)
        val contactAdapter = ContactAdapter()

        contactViewModel.contactList.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                Toast.makeText(context, getString(R.string.no_recrd), Toast.LENGTH_SHORT).show()
            }else{
                contactAdapter.setContact(it)
            }
        }

        binding.recycleViewContact.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.recycleViewContact.adapter = contactAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}