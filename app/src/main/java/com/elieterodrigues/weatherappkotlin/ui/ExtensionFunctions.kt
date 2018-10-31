package com.elieterodrigues.weatherappkotlin.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.elieterodrigues.weatherappkotlin.R
import java.text.DateFormat
import java.util.*


fun Context.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

operator fun ViewGroup.get(position: Int): View = getChildAt(position)

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun ImageView.loadIconUrl(url : String) {
    Glide.with(context)
            .load(url)
            .apply(RequestOptions()
                    .placeholder(R.drawable.abc_ic_star_black_36dp)
                    .error(R.drawable.ic_launcher_foreground))
            .into(this)
}

fun View.visible() {
    visibility = View.VISIBLE
}

var TextView.textColor: Int
    get() = currentTextColor
    set(v) = setTextColor(v)

val View.ctx: Context
    get() = context

fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)

fun View.slideExit() {
    if (translationY == 0f) animate().translationY(-height.toFloat())
}

fun View.slideEnter() {
    if (translationY < 0f) animate().translationY(0f)
}

//In Java, when we call startActivity, we need to specify the destination class as a parameter.
//In Kotlin, we can simplify it by adding the type to the function:
inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T: Activity> Context.startActivity( vararg params: Pair<String, String>) {
    val intent = Intent(this, T::class.java)
    params.forEach {
        intent.putExtra(it.first, it.second)
    }
    startActivity (intent)
}
//inline fun <T> with(t: T, body: T.() -> Unit) { t.body() }
//This function gets an object of type T and a function that will be used as an extension function.
//The implementation just takes the object and lets it execute the function. As the second parameter
//of the function is another function, it can be brought out of the parentheses, so we can create a
//block of code where we can use this and the public properties and functions of the object directly:

//using generics to return the objects casted to the type of the variable that the result is assigned to.
inline fun <reified T : View> Activity.findView(id: Int) = findViewById<T>(id)

fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}

