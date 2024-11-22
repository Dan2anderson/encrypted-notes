package com.example.encryptednotes

import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.encryptednotes.databinding.ActivityMainBinding
import com.example.encryptednotes.ui.DeleteDialog
import com.example.encryptednotes.ui.ImportJsonDialog
import com.example.encryptednotes.ui.NewMemoModalFragment
import com.example.encryptednotes.viewmodel.MemoViewModel
import com.example.encryptednotes.viewmodel.MemoViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NewMemoModalFragment.DataCallback, DeleteDialog.DeleteCallback, ImportJsonDialog.JsonCallback {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MemoViewModel by viewModels {
        MemoViewModelFactory((application as MemoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.redDark))
        setSupportActionBar(binding.toolbar)

        // Set the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.redDark)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            val fragmentManager: FragmentManager = supportFragmentManager
            val newMemoModalFragment = NewMemoModalFragment()
            newMemoModalFragment.show(fragmentManager, "NewMemoModalFragment")
        }
    }

    override fun onStart() {
        super.onStart()
        checkAuth()
    }

    private fun checkAuth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            BiometricPrompt.Builder(this)
                .setTitle("Authenticate")
                .setNegativeButton("Cancel", mainExecutor, { _, _ -> })
                .build()
                .authenticate(CancellationSignal(), mainExecutor, object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                    }
                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        showToast(getString(R.string.auth_failed))
                        finishAffinity()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                        super.onAuthenticationError(errorCode, errString)
                        showToast(getString(R.string.auth_failed))
                        finishAffinity()
                    }
                    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                        super.onAuthenticationHelp(helpCode, helpString)
                        showToast(getString(R.string.auth_failed))
                        finishAffinity()
                    }
                })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    var view = menuInflater.inflate(R.menu.menu_main, menu)
    return true
}
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
        R.id.import_notes -> {
            val dialogFragment = ImportJsonDialog()
            dialogFragment.show(supportFragmentManager, "importJsonDialog")
            true
        }
        R.id.export_notes -> {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, viewModel.getAllItemsJson())
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun dataCallback(title: String, subTitle: String, memo: String) {
        viewModel.insertMemo(title, subTitle, memo)
    }

    override fun delete(itemId: Int) {
        viewModel.deleteMemo(itemId)
    }

    override fun import(json: String) {
        val deferred = viewModel.addFromJson(json)
        lifecycleScope.launch {
            val success = deferred.await()
            if(!success){
                showToast(getString(R.string.not_formatted))
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}