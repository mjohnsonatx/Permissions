package com.example.permissions

import android.Manifest
import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {

    private val cameraAndLocationResultLauncher : ActivityResultLauncher <Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){
                permissions -> permissions.entries.forEach {
            val permissionName = it.key
            val isGranted = it.value
            if (isGranted) {
                if(permissionName == Manifest.permission.ACCESS_FINE_LOCATION){
                    Toast.makeText(this,
                        "Permission granted for fine location", Toast.LENGTH_SHORT).show()
                }else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) {
                    Toast.makeText(this,
                        "Permission granted for course location", Toast.LENGTH_SHORT).show()}
                else {
                    Toast.makeText(this,
                        "Permission granted for camera", Toast.LENGTH_SHORT).show()
                }

            }else{
                if(permissionName == Manifest.permission.ACCESS_FINE_LOCATION){
                    Toast.makeText(this,
                        "Permission denied for fine location", Toast.LENGTH_SHORT).show()
                }else if(permissionName==Manifest.permission.ACCESS_COARSE_LOCATION){
                    Toast.makeText(this,
                        "Permission denied for course location", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,
                        "Permission denied for camera", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCameraPermission : Button = findViewById(R.id.btnCameraPermission)

        btnCameraPermission.setOnClickListener{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                showRationaleDialog("Camera access required to use camera.",
                    "Camera cannot be used because Camera access is denied.")
            }else{
                cameraAndLocationResultLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                )
            }
        }
    }

    private fun showRationaleDialog(
        title: String,
        message: String,
    ){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel"){ dialog, _ -> dialog.dismiss()
            }
        builder.create().show()
    }
}