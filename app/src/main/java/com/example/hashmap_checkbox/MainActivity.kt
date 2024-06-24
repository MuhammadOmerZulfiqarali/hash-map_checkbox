package com.example.hashmap_checkbox

import android.content.Context
import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private val checkBoxState = HashMap<String, Boolean>()
    private val sharedPreferencesKey = "checkboxPrefs"
    private val hashMapKey = "checkBoxState"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        checkBox1 = findViewById(R.id.checkBox1)
        checkBox2 = findViewById(R.id.checkBox2)

        loadCheckboxState()

        checkBox1.setOnCheckedChangeListener { _, isChecked ->
            checkBoxState["checkBox1"] = isChecked
            saveCheckboxState()
        }

        checkBox2.setOnCheckedChangeListener { _, isChecked ->
            checkBoxState["checkBox2"] = isChecked
            saveCheckboxState()
        }

    }

    private fun saveCheckboxState() {
        val sharedPreferences = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(checkBoxState)
        editor.putString(hashMapKey, json)
        editor.apply()
    }

    private fun loadCheckboxState() {
        val sharedPreferences = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(hashMapKey, "")
        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<HashMap<String, Boolean>>() {}.type
            val loadedMap: HashMap<String, Boolean> = gson.fromJson(json, type)
            checkBoxState.putAll(loadedMap)
        }

        checkBox1.isChecked = checkBoxState["checkBox1"] ?: false
        checkBox2.isChecked = checkBoxState["checkBox2"] ?: false
    }
}



