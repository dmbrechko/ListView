package com.example.listview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val list = mutableListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.apply {
            setSupportActionBar(toolbar)
            val adapter = object: ArrayAdapter<User>(this@MainActivity, android.R.layout.simple_list_item_2, list) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    var row = convertView
                    if (row == null) {
                        val inflater = LayoutInflater.from(parent.context)
                        row = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
                    }
                    val user = list[position]
                    row?.findViewById<TextView>(android.R.id.text1)?.text = String
                        .format("Name: %s", user.name)
                    row?.findViewById<TextView>(android.R.id.text2)?.text = String.format(
                        "Age: %s", user.age)
                    return row!!
                }
            }
            listLV.adapter = adapter
            saveBTN.setOnClickListener {
                list.add(User(nameET.text.toString(), ageET.text.toString()))
                adapter.notifyDataSetChanged()
                nameET.text.clear()
                ageET.text.clear()
            }
            listLV.setOnItemClickListener { _, _, position, _ ->
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_exit -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}

data class User(val name: String, val age: String)