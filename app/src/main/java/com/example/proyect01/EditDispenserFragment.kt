package com.example.proyect01

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.TimePicker
import android.widget.Toast
import com.example.proyect01.databinding.FragmentEditDispenserBinding
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EditDispenserFragment : Fragment() {
    private lateinit var mBinding: FragmentEditDispenserBinding

    private var mActivity: MainActivity? = null

    //variables para sber si si edita o se crea
    private var mEditMode: Boolean = false
    private var mCreateMode: Boolean = false

    //
    private var mDispenserEntity: DispenserEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentEditDispenserBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getLong(getString(R.string.arg_id), 0)
        if (id != null && id != 0L) {
            mEditMode = true
            getDispenser(id)
        } else {
            Toast.makeText(activity, id.toString(), Toast.LENGTH_SHORT).show()
        }
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.edit_dispenser_title_add)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }

            R.id.action_save -> {
                val dispenser =
                    DispenserEntity(
                        name = mBinding.etName.text.toString().trim(),
                        referencia = mBinding.etDescription.text.toString().trim(),
                        direccion = mBinding.etDireccion.text.toString().trim()

                    )
                doAsync {
                    dispenser.id =
                        DispenserApplication.dataBase.dispenserDao().addDispenser(dispenser)
                    uiThread {
                        mActivity?.addDispenser(dispenser)
                        hideKeyBoard()
//                        Snackbar.make(
//                            mBinding.root, R.string.edit_dispenser_message_save_success,
//                            Snackbar.LENGTH_SHORT
//                        ).show()
                        Toast.makeText(
                            mActivity,
                            R.string.edit_dispenser_message_save_success,
                            Toast.LENGTH_SHORT
                        ).show()
                        mActivity?.onBackPressed()
                    }
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    //Oculta el teclado
    private fun hideKeyBoard() {
        val inm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            inm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
    }

    private fun getDispenser(id: Long) {
        doAsync {
            mDispenserEntity = DispenserApplication.dataBase.dispenserDao().getDispenserById(id)
            uiThread {
                if (mDispenserEntity != null) setUiDispenser(mDispenserEntity!!)
            }
        }
    }

    private fun setUiDispenser(dispenserEntity: DispenserEntity) {
        with(mBinding) {
            etName.setText(dispenserEntity.name)
            etDescription.setText(dispenserEntity.referencia)
            etDireccion.setText(dispenserEntity.direccion)

        }
    }

    override fun onDestroyView() {
        hideKeyBoard()
        super.onDestroyView()
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        mActivity?.hideFab(true)

        setHasOptionsMenu(false)
        super.onDestroy()
    }
}