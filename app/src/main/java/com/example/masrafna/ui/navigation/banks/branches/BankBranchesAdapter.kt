package com.example.masrafna.ui.navigation.banks.branches

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.masrafna.data.models.BankBranche
import com.example.masrafna.databinding.BranchesItemBinding

class BankBranchesAdapter(val context: Context) :

    RecyclerView.Adapter<BankBranchesAdapter.BranchViewHolder>() {

    lateinit var branches: MutableList<BankBranche.Payload.Data?>
    var bankName = ""

    inner class BranchViewHolder(val binding: BranchesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val view = BranchesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BranchViewHolder(view)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {

        with(holder.binding) {
            var branch = branches[position]!!

            branchName.text = branch.name
            addressText.text = branch.address
            phoneText.text = branch.phone
            bankName.text = this@BankBranchesAdapter.bankName
        }
    }

    override fun getItemCount(): Int {
        return branches.size
    }


}