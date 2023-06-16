package com.capstone.bangkit.calendivity.presentation.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.databinding.FragmentLoginBinding
import com.capstone.bangkit.calendivity.presentation.di.AuthTokenViewModel
import com.capstone.bangkit.calendivity.presentation.utils.Status
import com.capstone.bangkit.calendivity.presentation.utils.Utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val viewModelAuthToken by viewModels<AuthTokenViewModel>()
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // animation Forward and Backward
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        val window: Window = activity?.window!!
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.md_theme_light_tertiaryContainer)

        // sign in google
        _binding?.btnSignin?.setOnClickListener {
            signinGoogle()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun signinGoogle() {
        // create google consent with calendar scope using google mobile service
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope(Utils.SCOPE_URI_CALENDAR))
            .requestServerAuthCode(Utils.SERVER_AUTH)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val signInIntent = mGoogleSignInClient!!.signInIntent
        resultLauncher.launch(signInIntent)
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            val authCode = account.serverAuthCode

            authCode?.let { viewModelAuthToken.getAuthToken(it) }

            viewModelAuthToken.res.observe(this) {
                // show progress indicator
                Utils.showLoading(binding.progressIndicator, it.status == Status.LOADING)
                Utils.showLoading(binding.overlay, it.status == Status.LOADING)
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { value ->
                            // save is login, access token and refresh token using pref data store
                            saveUserPref(value.accessToken!!, value.refreshToken!!)

                            // if user not configure the multiform go to multiform page otherwise goto Homepage
                            findNavController().navigate(
                                R.id.action_loginFragment_to_multiStepForm
                            )
                        }
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                        // show message when something error
                        Toast.makeText(
                            requireActivity(),
                            resources.getString(R.string.koneksi_error),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Timber.w("signInResult:failed code=" + e.statusCode)

            // show to user if google sign in is failed
            Toast.makeText(
                requireActivity(),
                resources.getString(R.string.koneksi_error),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // TODO : Sementara pake shared pref nnti di update lagi
    private fun saveUserPref(aksesToken: String, refreshToken: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(Utils.SHARED_PREF, Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putBoolean("isLogin", true)
        edit.putString("akses_token", aksesToken)
        edit.putString("refresh_token", refreshToken)
        edit.apply()
    }
}