package org.xapps.examples.mlkitexamples.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.xapps.examples.mlkitexamples.R
import org.xapps.examples.mlkitexamples.databinding.ActivityMainBinding


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        navController = (supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment).navController

        val appBarConfigs = AppBarConfiguration.Builder(setOf(
                R.id.textRecognitionOfflineFragment,
                R.id.barcodeScannerOfflineFragment,
                R.id.textRecognitionOnlineFragment,
                R.id.barcodeScannerOnlineFragment,
                R.id.faceDetectionOfflineFragment,
                R.id.faceDetectionOnlineFragment,
                R.id.imageLabelingOfflineFragment
            ))
            .setOpenableLayout(bindings.drawerLayout)
            .build()

        bindings.toolbar.setupWithNavController(navController, appBarConfigs)
        bindings.navigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            navController.popBackStack(destination.id, true)
        }

        bindings.toolbar.setNavigationOnClickListener {
            bindings.drawerLayout.openDrawer(GravityCompat.START)
        }

        bindings.toolbar.title = getString(R.string.text_recognition)
        bindings.navigationView.setCheckedItem(R.id.textRecognitionOfflineFragment)
    }
}