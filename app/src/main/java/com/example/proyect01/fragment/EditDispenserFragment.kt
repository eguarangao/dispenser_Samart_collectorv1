package com.example.proyect01.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.proyect01.entity.DispenserEntity
import com.example.proyect01.activity.MainActivity
import com.example.proyect01.R
import com.example.proyect01.database.DispenserApplication
import com.example.proyect01.databinding.FragmentEditDispenserBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
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
            mEditMode = false
            mDispenserEntity = DispenserEntity(name = "", referencia = "", direccion = "")
        }
        setupActionBar()

        setUpTextFielps()

    }

    private fun setupActionBar() {
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title =
            if (mEditMode) getString(R.string.edit_dispenser_message_edit)
            else getString(R.string.edit_dispenser_title_add)
        setHasOptionsMenu(true)
    }

    private fun setUpTextFielps() {
        with(mBinding) {
            etName.addTextChangedListener { validateFields(tilName) }
            etDescription.addTextChangedListener { validateFields(tilDescription) }
            etDireccion.addTextChangedListener { validateFields(tilDireccion) }
        }

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
                if (mDispenserEntity != null && validateFields(
                        mBinding.tilDireccion,
                        mBinding.tilDescription,
                        mBinding.tilName
                    )
                ) {
                    /* val dispenser =
                     DispenserEntity(
                         name = mBinding.etName.text.toString().trim(),
                         referencia = mBinding.etDescription.text.toString().trim(),
                         direccion = mBinding.etDireccion.text.toString().trim()

                     )*/
                    with(mDispenserEntity!!) {
                        name = mBinding.etName.text.toString().trim()
                        referencia = mBinding.etDescription.text.toString().trim()
                        direccion = mBinding.etDireccion.text.toString().trim()
                    }
                    doAsync {
                        if (mEditMode) DispenserApplication.dataBase.dispenserDao()
                            .updateDispenser(mDispenserEntity!!)
                        else mDispenserEntity!!.id = DispenserApplication.dataBase.dispenserDao()
                            .addDispenser(mDispenserEntity!!)

                        uiThread {
                            hideKeyBoard()

                            if (mEditMode) {
                                mActivity?.updateDispense(mDispenserEntity!!)
                                Snackbar.make(
                                    mBinding.root,
                                    R.string.edit_dispenser_message_update_success,
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            } else {
                                mActivity?.addDispenser(mDispenserEntity!!)

                                Toast.makeText(
                                    mActivity,
                                    R.string.edit_dispenser_message_save_success,
                                    Toast.LENGTH_SHORT
                                ).show()
                                mActivity?.onBackPressed()
                            }

                        }
                    }
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun validateFields(vararg textFields: TextInputLayout): Boolean {
        var isValid = true
        for (textField in textFields) {
            if (textField.editText?.text.toString().trim().isEmpty()) {
                textField.error = getString(R.string.helper_required)
                isValid = false
            } else textField.error = null
        }
        if (!isValid) Snackbar.make(
            mBinding.root,
            R.string.edit_dispenser_message_Valid,
            Snackbar.LENGTH_SHORT
        ).show()
        return isValid
    }

    private fun validateFields(): Boolean {
        var isValid = true
        if (mBinding.etDireccion.text.toString().trim().isEmpty()) {
            mBinding.tilDireccion.error = getString(R.string.helper_required)
            mBinding.etDireccion.requestFocus()
            isValid = false
        }
        if (mBinding.etDescription.text.toString().trim().isEmpty()) {
            mBinding.tilDescription.error = getString(R.string.helper_required)
            mBinding.etDescription.requestFocus()
            isValid = false
        }
        if (mBinding.etName.text.toString().trim().isEmpty()) {
            mBinding.tilName.error = getString(R.string.helper_required)
            mBinding.etName.requestFocus()
            isValid = false
        }
        return isValid
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
            etName.text = dispenserEntity.name.editable()
            etDescription.text = dispenserEntity.referencia.editable()
            etDireccion.text = dispenserEntity.direccion.editable()
        }
    }

    private fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

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