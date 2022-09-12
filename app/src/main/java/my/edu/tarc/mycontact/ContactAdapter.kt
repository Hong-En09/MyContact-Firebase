package my.edu.tarc.mycontact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.mycontact.entity.Contact

//an object linking data source to recycleView
class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    //Cache copy of data
    private var dataSet = emptyList<Contact>()

    //a class representing each record
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        //view (parameter) refers to the layout hosting each record
        val textName: TextView = view.findViewById(R.id.textViewName)
        val textPhone: TextView = view.findViewById<TextView>(R.id.textViewPhone)

        init {
            view.setOnClickListener{
                //todo: handle click event
            }
        }
    }

    internal fun setContact(contact: List<Contact>){
        dataSet = contact
        notifyDataSetChanged() //refresh the RecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //insert data from a record to the layout (ViewHolder)
        val contact = dataSet[position]
        holder.textName.text = contact.name
        holder.textPhone.text = contact.phone
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}