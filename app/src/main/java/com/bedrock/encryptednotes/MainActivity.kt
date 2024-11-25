package com.bedrock.encryptednotes

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bedrock.encryptednotes.data.MemoSharedPrefs
import com.bedrock.encryptednotes.databinding.ActivityMainBinding
import com.bedrock.encryptednotes.ui.DeleteDialog
import com.bedrock.encryptednotes.ui.ImportJsonDialog
import com.bedrock.encryptednotes.ui.NewMemoModalFragment
import com.bedrock.encryptednotes.viewmodel.MemoViewModel
import com.bedrock.encryptednotes.viewmodel.MemoViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NewMemoModalFragment.DataCallback,
    DeleteDialog.DeleteCallback, ImportJsonDialog.JsonCallback {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MemoViewModel by viewModels {
        MemoViewModelFactory((application as MemoApplication).repository)
    }
    private var isAuthenticated = false

    private val deviceCredentialLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Authentication succeeded
                isAuthenticated = true;
                showToast("Authentication succeeded")
            } else {
                // Authentication failed
                isAuthenticated = false;
                showToast("Authentication failed")
                finishAffinity()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.redDark))
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Set the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.redDark)


        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onStart() {
        super.onStart()
        if (MemoSharedPrefs.getIsFirstStart(this)) {
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            MemoSharedPrefs.saveIsFirstStart(this, false)
            navController.navigate(R.id.action_FirstFragment_to_tutorialFragment)

        } else {
            if (!isAuthenticated) {
                checkAuth()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isAuthenticated = false
    }

    private fun checkAuth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val biometricManager = getSystemService(BIOMETRIC_SERVICE) as BiometricManager
            when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {// or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    BiometricPrompt.Builder(this)
                        .setAllowedAuthenticators(
                            BiometricManager.Authenticators.BIOMETRIC_WEAK or
                                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
                        )
                        .setTitle(getString(R.string.auth_title))
                        .setSubtitle(getString(R.string.pin_sub))
                        .build()
                        .authenticate(
                            CancellationSignal(),
                            mainExecutor,
                            object : BiometricPrompt.AuthenticationCallback() {
                                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                    super.onAuthenticationSucceeded(result)
                                }

                                override fun onAuthenticationFailed() {
                                    super.onAuthenticationFailed()
                                    showToast(getString(R.string.auth_failed))
                                    finishAffinity()
                                }

                                override fun onAuthenticationError(
                                    errorCode: Int,
                                    errString: CharSequence?
                                ) {
                                    super.onAuthenticationError(errorCode, errString)
                                    showToast(getString(R.string.auth_failed))
                                    finishAffinity()
                                }

                                override fun onAuthenticationHelp(
                                    helpCode: Int,
                                    helpString: CharSequence?
                                ) {
                                    super.onAuthenticationHelp(helpCode, helpString)
                                    showToast(getString(R.string.auth_failed))
                                    finishAffinity()
                                }
                            })
                }

                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
                BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED,
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE,
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    checkPin()
                }
            }
        } else {
            checkPin()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
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
            if (!success) {
                showToast(getString(R.string.not_formatted))
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun checkPin() {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        val intent = keyguardManager.createConfirmDeviceCredentialIntent(
            getString(R.string.auth_title),
            getString(R.string.pin_sub)
        )
        deviceCredentialLauncher.launch(intent)
    }
}