package com.skyd.imomoe.ext.theme

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.TypedValue
import androidx.annotation.StyleRes
import androidx.lifecycle.MutableLiveData
import com.skyd.imomoe.R
import com.skyd.imomoe.ext.editor
import com.skyd.imomoe.ext.sharedPreferences


private val map = hashMapOf(
    "Pink" to R.style.Theme_Anime_Pink,
    "Dynamic" to R.style.Theme_Anime_Dynamic,
    "Blue" to R.style.Theme_Anime_Blue,
    "Lemon" to R.style.Theme_Anime_Lemon,
    "Purple" to R.style.Theme_Anime_Purple,
)

private fun getKeyByValue(v: Int): String? {
    for (key in map.keys) {
        if (map[key] == v) {
            return key
        }
    }
    return null
}

var appThemeRes: MutableLiveData<Int> = object : MutableLiveData<Int>(
    // getOrDefault method was added in API level 24
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        map.getOrDefault(
            sharedPreferences().getString("themeRes", null),
            R.style.Theme_Anime_Pink
        )
    } else {
        val v = sharedPreferences().getString("themeRes", null)
        if (map[v] != null) {
            map[v]
        } else {
            R.style.Theme_Anime_Pink
        }
    }
) {
    override fun postValue(@StyleRes value: Int?) {
        sharedPreferences().editor {
            putString(
                "themeRes",
                getKeyByValue(value ?: R.style.Theme_Anime_Pink)
            )
        }
        super.postValue(value)
    }

    override fun setValue(value: Int?) {
        sharedPreferences().editor {
            putString(
                "themeRes",
                getKeyByValue(value ?: R.style.Theme_Anime_Pink)
            )
        }
        super.setValue(value)
    }
}

fun Context.getAttrColor(attr: Int): Int {
    val typedValue = TypedValue()
    val typedArray: TypedArray = obtainStyledAttributes(typedValue.data, intArrayOf(attr))
    val color = typedArray.getColor(0, 0)
    typedArray.recycle()
    return color
}