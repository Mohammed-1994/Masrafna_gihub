package com.example.masrafna.ui.navigation.notifications

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.masrafna.R
import com.example.masrafna.data.models.NotificationModel
import com.example.masrafna.databinding.NotificationItemBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import org.ocpsoft.prettytime.PrettyTime

private const val TAG = "NotificationAdapter myTag"

class NotificationAdapter(val context: Context, private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    private lateinit var notificationList: List<NotificationModel.Notifications>

    inner class NotificationViewHolder(val binding: NotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = NotificationItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val currentNotification = notificationList[position]

        with(holder) {

            binding.moreBtn.setOnClickListener {
                clickListener.onItemClicked(currentNotification)
            }

            binding.title.text = currentNotification.title
            binding.desc.text = currentNotification.text

            Glide.with(context)

                .load(currentNotification.image)
                .placeholder(R.drawable.sticker)
                .into(binding.image)

            val date = PrettyTime().format(currentNotification.createdAt)
            binding.date.text = date

        }


    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    fun submitNotifications(notificationsList: List<NotificationModel.Notifications>) {
        this.notificationList = notificationsList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClicked(notification: NotificationModel.Notifications)
    }

}