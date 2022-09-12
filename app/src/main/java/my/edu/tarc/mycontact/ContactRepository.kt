package my.edu.tarc.mycontact

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.edu.tarc.mycontact.entity.Contact

class ContactRepository(private val contactDao: ContactDao) {
    //TODO: Link to DAO (Data Access Object) and Create a cache copy of UI data
    val allContact: LiveData<List<Contact>> = contactDao.getAll()

    @WorkerThread
    suspend fun  insert(contact: Contact){
        contactDao.insert(contact)
    }

    @WorkerThread
    suspend fun  delete(contact: Contact){
        contactDao.delete(contact)
    }

    @WorkerThread
    suspend fun  update(contact: Contact){
        contactDao.update(contact)
    }

    fun syncContact(id: String, contactList: List<Contact>){
        val database: DatabaseReference
        database = Firebase.database.reference

        for(contact in contactList.listIterator()){
            database.child("contact").child(id)
                .child(contact.phone)
                .child("phone")
                .setValue(contact.phone)

            database.child("contact").child(id)
                .child(contact.phone)
                .child("name")
                .setValue(contact.name)
        }
    }


}