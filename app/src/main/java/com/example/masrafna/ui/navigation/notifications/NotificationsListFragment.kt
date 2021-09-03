package com.example.masrafna.ui.navigation.notifications

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.api.Status
import com.example.masrafna.data.models.NotificationModel
import com.example.masrafna.databinding.FragmentNotificationsListBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.example.masrafna.ui.navigation.profile.ProfileViewModel

private const val TAG = "NotificationsFr myTag"

class NotificationsListFragment : Fragment(), NotificationAdapter.OnItemClickListener {

    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var binding: FragmentNotificationsListBinding
    private val profileViewModel: ProfileViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(profileViewModel) {
            networkStatus.observe(this@NotificationsListFragment, {

                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) View.VISIBLE else View.GONE
                if (it.status == Status.CODE_401) {

                    /**
                     *  this means that the token is invalid and the user have to login again
                     *  to get new valid token.
                     */

                    Log.e(TAG, "onCreate: ${it.msg}")
                }
            })

            notificationResponse.observe(this@NotificationsListFragment, {
                if (it != null)
                populateNotifications(it.notifications)
            })
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        profileViewModel.getNotifications()
    }

    private fun setupToolbar() {

        with(binding) {

            toolbar.drawerIcon.setOnClickListener {
                (requireContext() as NavigationDrawerActivity)
                    .binding.drawerLayout.open()
            }

            toolbar.navigateUp.setOnClickListener {
                findNavController().navigateUp()
            }

            toolbar.title.text = getString(R.string.notifications)

            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }

    private fun populateNotifications(notifications: List<NotificationModel.Notifications>) {
        notificationAdapter = NotificationAdapter(requireContext(), this)
        notificationAdapter.submitNotifications(notifications)
        with(binding.notificationRv) {
            adapter = notificationAdapter

        }
    }

    override fun onItemClicked(notification: NotificationModel.Notifications) {
        Log.d(TAG, "onItemClicked: ${notification.id}")
    }


}