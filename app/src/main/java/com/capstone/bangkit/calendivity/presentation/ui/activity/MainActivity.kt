package com.capstone.bangkit.calendivity.presentation.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        // splash screen
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // define view binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // splash screen animation
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            splashScreenViewProvider.iconView
                .animate()
                .setDuration(
                    350
                ).translationY(-0f)
                .alpha(0f)
                .withEndAction {
                    // After the fade out, remove the
                    // splash and set content view
                    splashScreenViewProvider.remove()
                }.start()
        }

        setContentView(binding.root)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val a = this.findViewById<ConstraintLayout>(R.id.coba)

        a?.visibility = View.INVISIBLE
//        val fragment =
//            this.supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as? NavHostFragment
//        val currentFragment =
//            fragment?.childFragmentManager?.fragments?.get(0) as? IOnBackPressed
//        if (currentFragment != null) {
//            currentFragment.onBackPressed().takeIf { !it }?.let {
//                super.onBackPressed()
//            }
//        } else {
//            val fragments =
//                this.supportFragmentManager.findFragmentById(R.id.multi_form)
//            (fragments as? IOnBackPressed)?.onBackPressed()?.let {
//                if (it) {
//                    Timber.d("test")
//                    MaterialAlertDialogBuilder(this)
//                        .setTitle(resources.getString(R.string.biodata_title))
//                        .setMessage(resources.getString(R.string.biodata_message))
//                        .setNeutralButton(resources.getString(R.string.btn_tidak)) { _, _ ->
//
//                        }.setPositiveButton(resources.getString(R.string.btn_ya)) { _, _ ->
//                            super.onBackPressed()
//                        }
//                } else {
//                    super.onBackPressed()
//                }
//            }
//            super.onBackPressed()
//        }
    }
}
