package de.example.challenge.flickrapp.adapters.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import de.example.challenge.flickrapp.R

class SpinnerAdapter(
    context: Context,
    resource: Int,
    private val objects: Array<String>,
    private var onSpinnerItemListener: OnSpinnerItemListener
) : ArrayAdapter<String>(context, resource, objects) {

    var selectedPosition: Int = 0

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    private fun getCustomView(position: Int, viewGroup: ViewGroup): View {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(android.R.layout.simple_spinner_dropdown_item, viewGroup, false)
        val text: CheckedTextView = view.findViewById(android.R.id.text1)
        text.text = objects[position]
        if (position == selectedPosition) {
            text.setTextColor(context.resources.getColor(R.color.pink_flickr))
        }
        view.setOnTouchListener { _ , _ ->
            selectedPosition = position
            onSpinnerItemListener.onItemClicked(position)
            false
        }
        return view
    }

}