package com.example.masrafna.ui.navigation.services

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.masrafna.R
import com.example.masrafna.data.models.ServicesModel

private const val TAG = "ServicesGridAdapter myTag"
class ServicesGridAdapter(
    val context: Context,
    private val servicesList: ArrayList<ServicesModel>,
    private val itemClicked: OnItemClicked
) :
    BaseAdapter() {
    override fun getCount(): Int {
        return servicesList.size
    }

    override fun getItem(position: Int): ServicesModel {
        return servicesList[position]
    }

    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val service = servicesList[position]
        var view = convertView
        if (view == null) {
            view = LayoutInflater
                .from(context).inflate(R.layout.services_grid_item, parent, false)
        }

        Log.d(TAG, "getView: ")
        view?.findViewById<ImageView>(R.id.image)?.setImageResource(service.image)

        view?.findViewById<TextView>(R.id.title)?.text = service.title
        view?.findViewById<TextView>(R.id.desc)?.text = service.desc

        view?.setOnClickListener { itemClicked.onClicked(service) }

        return view!!

    }

    interface OnItemClicked {
        fun onClicked(service: ServicesModel)
    }
}