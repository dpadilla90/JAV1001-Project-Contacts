import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable

/**
 * Retrieves a parcelable array list from the bundle with the specified key.
 *
 * @param key The key used to store the parcelable array list in the bundle.
 * @return The parcelable array list retrieved from the bundle, or null if not found.
 */
inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
}

/**
 * Retrieves a parcelable array list from the intent with the specified key.
 *
 * @param key The key used to store the parcelable array list in the intent.
 * @return The parcelable array list retrieved from the intent, or null if not found.
 */
inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}


