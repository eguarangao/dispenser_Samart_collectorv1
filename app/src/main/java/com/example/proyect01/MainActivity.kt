package com.example.proyect01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyect01.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), OnClickListener, MainAux {
    private lateinit var mBbinding: ActivityMainBinding
    private lateinit var mAdapter: DispenserDapter
    private lateinit var mGridLayout: GridLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBbinding.root)


        mBbinding.fab.setOnClickListener { launchEditFragment() }
        setupRecyclerView()
    }

    private fun launchEditFragment() {
        val fragment = EditDispenserFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

       // mBbinding.fab.hide()
        hideFab()
    }

    private fun setupRecyclerView() {
        mAdapter = DispenserDapter(mutableListOf(), this)
        mGridLayout = GridLayoutManager(this, 2)
        getDispenser()

        mBbinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

    private fun getDispenser() {
        doAsync {
            val dispenseres = DispenserApplication.dataBase.dispenserDao().getAllDispenser()
            uiThread { mAdapter.setDispenseres(dispenseres) }
        }

    }

    override fun onCLick(dispenserEntity: DispenserEntity) {
        TODO("Not yet implemented")
    }

    override fun onDeleteDispenser(dispenserEntity: DispenserEntity) {
        doAsync {
            DispenserApplication.dataBase.dispenserDao().deleteDispenser(dispenserEntity)
            uiThread {
                mAdapter.delete(dispenserEntity)
            }
        }
    }

    /*
  MainAux
     */

    override fun hideFab(isVisible: Boolean) {
   if(isVisible)mBbinding.fab.show()else mBbinding.fab.hide()
    }


}