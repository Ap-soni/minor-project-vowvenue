package login

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.minor1.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class bottomsheet : BottomSheetDialogFragment() {

    companion object {
        private const val STORAGE_PERMISSION_CODE = 101
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View=inflater.inflate(R.layout.fragment_bottomsheet, container, false)
        // Inflate the layout for this fragment

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestStoragePermission()
    }


    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
        } else {
            // Permission is already granted
            Toast.makeText(requireContext(), "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(requireContext(), "Storage Permission Granted", Toast.LENGTH_SHORT).show()
                    // Permission granted, proceed with the required action
                } else {
                    Toast.makeText(requireContext(), "Storage Permission Denied", Toast.LENGTH_SHORT).show()
                    // Permission denied, handle accordingly
                }
            }
            // Handle other permissions if needed
        }
    }
}