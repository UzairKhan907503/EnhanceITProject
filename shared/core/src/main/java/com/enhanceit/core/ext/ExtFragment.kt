package com.enhanceit.core.ext

import android.app.Activity
import android.content.DialogInterface
import androidx.fragment.app.Fragment
import com.enhanceit.core.utils.AutoCleanedValue


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.enhanceit.shared.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.progressBar(): AlertDialog {
    return MaterialAlertDialogBuilder(requireContext()).apply {
        setView(R.layout.loader)
    }.create().apply {
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}

fun <T : Any> Fragment.autoCleaned(initializer: (() -> T)? = null): AutoCleanedValue<T> {
    return AutoCleanedValue(this, initializer)
}

fun Fragment.launchWhenStarted(action : suspend () -> Unit){
    lifecycleScope.launchWhenStarted {
        action.invoke()
    }
}

fun Fragment.showError(message : String){
    AlertDialog.Builder(requireContext())
        .setTitle("Error")
        .setMessage(message)
        .setPositiveButton("Ok") { _, _ ->

        }
        .setNegativeButton("Cancel"){_,_ ->

        }
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show()
}
fun Fragment.hideKeyboard(){
    val imm: InputMethodManager =
        requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = requireActivity().currentFocus
    if (view == null) {
        view = View(requireActivity())
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}