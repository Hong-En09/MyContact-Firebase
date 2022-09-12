package my.edu.tarc.mycontact

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import my.edu.tarc.mycontact.databinding.ActivityMainBinding
import my.edu.tarc.mycontact.entity.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactViewModel: ContactViewModel
    //private var contactViewModel: ContactViewModel by viewModels<>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize view model
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        Log.d("onCreate", "Main")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "Main")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_add -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_ContactFragment_to_addContactFragment)
                true
            }
            R.id.action_profile ->{
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_ContactFragment_to_ProfileFragment)
                true
            }
            R.id.action_upload -> {
                contactViewModel.syncContact()
                true
            }
            R.id.action_settings -> true

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    //Public Variable
    //companion object{
        //val contactList = ArrayList<Contact>()
    //}

    //no need public variable when we have contactViewModel
}