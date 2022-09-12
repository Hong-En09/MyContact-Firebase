package my.edu.tarc.mycontact

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import my.edu.tarc.mycontact.databinding.FragmentSecondBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //Get content from another app
    private val getProfilePic = registerForActivityResult(
        ActivityResultContracts.GetContent()){ uri ->
        if(uri!==null){
            //Set the image to the new image
            binding.imageViewPicture.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enable handling of menu item
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        readProfilePicture()
        return binding.root

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_add).isVisible = false
        menu.findItem(R.id.action_profile).isVisible = false
        menu.findItem(R.id.action_settings).isVisible = false
        menu.findItem(R.id.action_upload).isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Retrieve data from preferences
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE)

        if(preferences != null) {
            if (preferences.contains(getString(R.string.name))) {
                binding.editTextTextName.setText(
                    preferences.getString(
                        getString(R.string.name),
                        ""
                    )
                )
            }
            if (preferences.contains(getString(R.string.phone))) {
                binding.editTextPhone.setText(preferences.getString(getString(R.string.phone), ""))
            }

        }
        binding.imageViewPicture.setOnClickListener {
            getProfilePic.launch("image/*")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when(item.itemId){
            R.id.action_save ->{
                //create an instance of preferences
                val preferences = activity?.getPreferences(Context.MODE_PRIVATE)
                val name = binding.editTextTextName.text.toString()
                val phone = binding.editTextPhone.text.toString()

                if(preferences != null){
                    with(preferences.edit()){
                        putString(getString(R.string.name), name)
                        putString(getString(R.string.phone), phone)
                        apply() //save to the preference file
                    }
                }

                uploadProfilePicture()
            }
        }

        return true
    }

    private fun saveProfilePicture() {
        val filename = "profile.png"
        val file = File(this.context?.filesDir, filename)

        val bd = binding.imageViewPicture.getDrawable() as BitmapDrawable
        val bitmap = bd.bitmap
        val outputStream: OutputStream

        try{
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            outputStream.flush()
            outputStream.close()
        }catch (e: FileNotFoundException){
            e.printStackTrace()
        }
    }

    private fun readProfilePicture(){
        val filename = "profile.png"
        val file = File(this.context?.filesDir, filename)

        try{
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            binding.imageViewPicture.setImageBitmap(bitmap)
        }catch (e: FileNotFoundException){
            e.printStackTrace()
        }
    }

    fun uploadProfilePicture(){
        val fileName = "profile.png"
        val file = File(this.context?.filesDir, fileName)
        val storage = Firebase.storage("gs://contact-list-c0574.appspot.com")

        val myProfileImage = storage.reference.child("images").child("123")

        if(file.exists()){
            try{
                myProfileImage.putFile(Uri.fromFile(file))
            }catch (e: FileNotFoundException){
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}