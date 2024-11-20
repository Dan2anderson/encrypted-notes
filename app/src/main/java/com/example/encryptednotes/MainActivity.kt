package com.example.encryptednotes

import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.encryptednotes.databinding.ActivityMainBinding
import com.example.encryptednotes.ui.DeleteDialog
import com.example.encryptednotes.ui.ImportJsonDialog
import com.example.encryptednotes.ui.NewMemoModalFragment
import com.example.encryptednotes.viewmodel.MemoViewModel
import com.example.encryptednotes.viewmodel.MemoViewModelFactory

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
                        finishAffinity()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                        super.onAuthenticationError(errorCode, errString)
                        finishAffinity()
                    }
                    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                        super.onAuthenticationHelp(helpCode, helpString)
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
        R.id.action_settings -> {
            val dialogFragment = ImportJsonDialog()
            dialogFragment.show(supportFragmentManager, "importJsonDialog")
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
        viewModel.addFromJson(json)
    }
}