package com.example.batoboapp.ui.insert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.batoboapp.R
import com.example.batoboapp.databinding.ActivityJasaAddUpdateBinding
import com.example.batoboapp.database.Jasa
import com.example.batoboapp.helper.DateHelper
import com.example.batoboapp.helper.ViewModelFactory

class JasaAddUpdateActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_JASA = "extra_jasa"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    private var isEdit = false
    private var jasa: Jasa? = null

    private lateinit var jasaAddUpdateViewModel: JasaAddUpdateViewModel
    private var _activityJasaAddUpdateBinding: ActivityJasaAddUpdateBinding? = null
    private val binding get() = _activityJasaAddUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityJasaAddUpdateBinding = ActivityJasaAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        jasaAddUpdateViewModel = obtainViewModel(this@JasaAddUpdateActivity)

        jasa = intent.getParcelableExtra(EXTRA_JASA)
        if (jasa != null) {
            isEdit = true
        } else {
            jasa = Jasa()
        }
        val actionBarTitle: String
        val btnTitle: String
        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (jasa != null) {
                jasa?.let { jasa ->
                    binding?.edtTitle?.setText(jasa.title)
                    binding?.edtDescription?.setText(jasa.description)
                    binding?.edtPhone?.setText(jasa.phone)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.btnSubmit?.text = btnTitle

        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.edtTitle?.text.toString().trim()
            val description = binding?.edtDescription?.text.toString().trim()
            val phone = binding?.edtPhone?.text.toString().trim()
            when {
                title.isEmpty() -> {
                    binding?.edtTitle?.error = getString(R.string.empty)
                }
                description.isEmpty() -> {
                    binding?.edtDescription?.error = getString(R.string.empty)
                }
                phone.isEmpty() -> {
                    binding?.edtPhone?.error = getString(R.string.empty)
                }
                else -> {
                    jasa.let { jasa ->
                        jasa?.title = title
                        jasa?.description = description
                        jasa?.phone = phone
                    }
                    if (isEdit) {
                        jasaAddUpdateViewModel.update(jasa as Jasa)
                        showToast(getString(R.string.changed))
                    } else {
                        jasa.let { jasa ->
                            jasa?.date = DateHelper.getCurrentDate()
                        }
                        jasaAddUpdateViewModel.insert(jasa as Jasa)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityJasaAddUpdateBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    jasaAddUpdateViewModel.delete(jasa as Jasa)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): JasaAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(JasaAddUpdateViewModel::class.java)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}