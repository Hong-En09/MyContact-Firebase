package my.edu.tarc.mycontact.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Contact (val name: String,
               @PrimaryKey val phone: String) {

}
