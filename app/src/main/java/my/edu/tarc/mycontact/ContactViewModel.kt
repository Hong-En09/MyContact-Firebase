package my.edu.tarc.mycontact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.edu.tarc.mycontact.database.ContactDatabase
import my.edu.tarc.mycontact.entity.Contact

class ContactViewModel (application: Application)
    : AndroidViewModel (application){
        //TODO: Create an instance of dataset and repository
        var contactList: LiveData<List<Contact>>
        private val contactRepository: ContactRepository

        init {
            // Create an instance of DAO
            val contactDao = ContactDatabase.getDatabase(application).contactDao()

            //Link DAO to Repository
            contactRepository = ContactRepository(contactDao)

            //Retrieve data from repository
            contactList = contactRepository.allContact

        }

    fun insert(contact: Contact) = viewModelScope.launch {
        contactRepository.insert(contact)
    }

    fun delete(contact: Contact) = viewModelScope.launch {
        contactRepository.delete(contact)
    }

    fun update(contact: Contact) = viewModelScope.launch {
        contactRepository.update(contact)
    }

    fun syncContact(){
        contactRepository.syncContact("123", contactList.value!!.toList())
    }

}