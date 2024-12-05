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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private var list = mutableListOf<User>()
    private lateinit var adapter: ArrayAdapter<User>
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
        viewModel.list.observe(this) { list ->
            setList(list)
        }
        binding.apply {
            setSupportActionBar(toolbar)
            adapter = object: ArrayAdapter<User>(this@MainActivity, android.R.layout.simple_list_item_2, list) {
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
                if (nameET.text.isBlank() || ageET.text.isBlank()) {
                    Toast.makeText(this@MainActivity,
                        R.string.fill_all_fields, Toast.LENGTH_SHORT)
                        .show()
                }
                viewModel.addItem(User(nameET.text.toString(), ageET.text.toString()))
                nameET.text.clear()
                ageET.text.clear()
            }
            listLV.setOnItemClickListener { _, _, position, _ ->
                viewModel.removeItem(position)
            }
        }
    }

    fun setList(list: List<User>) {
        adapter.clear()
        adapter.addAll(list)
        adapter.notifyDataSetChanged()
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