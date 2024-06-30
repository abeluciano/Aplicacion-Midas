/**
 * Adaptador para una lista de usuarios en un RecyclerView.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta clase UsuariosAdapter es un adaptador personalizado para mostrar una lista de objetos
 * Usuarios en un RecyclerView. Se encarga de crear y vincular las vistas para cada usuario en
 * la lista y maneja los eventos de clic en cada elemento para seleccionar un usuario.
 */

package com.example.midas.Administrador.AdministradorAdapterUsers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Administrador.DataClassAdmin.Usuarios
import com.example.midas.R
import android.widget.Filter
import android.widget.Filterable

class UsuariosAdapter(
    private var usersList: MutableList<Usuarios>,
    private val onAccountSelected: (Usuarios) -> Unit
) : RecyclerView.Adapter<UsuariosViewHolder>(), Filterable {

    private var usersListFiltered = usersList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuariosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users_item, parent, false)
        return UsuariosViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuariosViewHolder, position: Int) {
        val usuarios = usersListFiltered[position]
        holder.render(usuarios)
        holder.itemView.setOnClickListener {
            onAccountSelected(usuarios)
        }
    }

    override fun getItemCount(): Int {
        return usersListFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence.toString()
                usersListFiltered = if (charString.isEmpty()) {
                    usersList
                } else {
                    val filteredList = usersList.filter {
                        it.nameUser.contains(charString, ignoreCase = true) ||
                                it.idUser.contains(charString, ignoreCase = true)
                    }.toMutableList()
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = usersListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                usersListFiltered = filterResults?.values as MutableList<Usuarios>
                notifyDataSetChanged()
            }
        }
    }
}
