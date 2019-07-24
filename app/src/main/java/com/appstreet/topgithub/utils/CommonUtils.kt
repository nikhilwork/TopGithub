package com.appstreet.topgithub.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

class CommonUtils {
    companion object{

        /**
         * show short duration toast
         * @param context - Context
         * @param message - The message that will display in toast
         */
        fun showShortToast(context: Context, message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        /**
         * show long duration toast
         * @param context - Context
         * @param message - The message that will display in toast
         */
        fun showLongToast(context: Context, message: String){
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        /**
         * get Network connectivity status
         * @param context
         * @return true or false boolean value
         */
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}