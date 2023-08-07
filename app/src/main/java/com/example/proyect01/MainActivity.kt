package com.example.proyect01

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyect01.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

    private fun launchEditFragment(args: Bundle? = null) {
        val fragment = EditDispenserFragment()
        if (args != null) fragment.arguments = args

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()



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

    override fun onCLick(dispenserId: Long) {
        val args = Bundle()//le vamos a pasar los argumentos
        args.putLong(getString(R.string.arg_id), dispenserId)
        launchEditFragment(args)
    }

    override fun onDeleteDispenser(dispenserEntity: DispenserEntity) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_title_delete)
            .setPositiveButton(
                R.string.dialog_delete_confirm,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    doAsync {
                        DispenserApplication.dataBase.dispenserDao()
                            .deleteDispenser(dispenserEntity)
                        uiThread {
                            mAdapter.delete(dispenserEntity)
                        }
                    }
                })
            .setNegativeButton(R.string.dialog_delete_cancel, null)
            .show()

    }

    /*
  MainAux
     */

    override fun hideFab(isVisible: Boolean) {
        if (isVisible) mBbinding.fab.show() else mBbinding.fab.hide()
    }

    override fun addDispenser(dispenserEntity: DispenserEntity) {
        mAdapter.add(dispenserEntity)
    }

    override fun updateDispense(dispenserEntity: DispenserEntity) {
        mAdapter.update(dispenserEntity)
    }
}