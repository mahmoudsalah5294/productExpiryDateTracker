package com.mahmoudsalah.goodsexpirydatetracker.ui.barcode_scanner

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.mahmoudsalah.goodsexpirydatetracker.data.local.ProductsDao
import com.mahmoudsalah.goodsexpirydatetracker.data.local.ProductsDataBase
import com.mahmoudsalah.goodsexpirydatetracker.databinding.FragmentBarcodeScannerBinding
import com.mahmoudsalah.goodsexpirydatetracker.notification.MyAlarm
import kotlinx.coroutines.*


private const val CAMERA_REQUEST_CODE = 101
class BarcodeScannerFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner
    private var _binding: FragmentBarcodeScannerBinding? = null
    private lateinit var database:ProductsDao
    private  var sharedPref: SharedPreferences? = null
    private var productDetails:List<String> = listOf()
    private lateinit var alarm: MyAlarm
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var presenter:BarcodeScannerPresenterInterface
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPref = requireActivity().getSharedPreferences("roundRobin",
            Context.MODE_PRIVATE)
        alarm = MyAlarm(requireContext())
        _binding = FragmentBarcodeScannerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        database = ProductsDataBase.getProductsData(requireContext()).productsDao()
        presenter = BarcodeScannerPresenter(database,sharedPref,alarm)

        setPermissions()
        startScan()


        return root
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun startScan(){
        codeScanner = CodeScanner(requireContext(),binding.scannerView)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false


            decodeCallback = DecodeCallback {
                runOnUiThread {

                    productDetails = prepareData(it.text)
                    if (!productDetails.isNullOrEmpty() && productDetails.size == 3) {
                        binding.detailsTxt.text = "${productDetails[0]}\n${productDetails[1]}\n${productDetails[2]}"
                    } else {
                        binding.detailsTxt.text = it.text
                    }

                }

            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                   Toast.makeText(requireContext(), "${it.message}",Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.saveBtn.setOnClickListener{
                GlobalScope.launch(Dispatchers.IO) {
                    val result = presenter.saveProducts(productDetails)
                    if (result != 0L) {
                        withContext(Dispatchers.Main) {
                            binding.detailsTxt.text = ""
                        }
                    }
                }

        }

        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }



    private fun setPermissions(){
        val permission = ContextCompat.checkSelfPermission(requireContext(),
            android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(requireContext(),"camera permission",Toast.LENGTH_SHORT).show()
                }else{
                    //successful
                }
            }
        }
    }
    private fun prepareData(txt:String):List<String>{
        return txt.split(":")
    }

}



fun Fragment?.runOnUiThread(action: () -> Unit){
    this ?: return
    if (!isAdded) return  //Fragment not attached to activity
    activity?.runOnUiThread(action)
}
