package com.example.metadescqrscanner

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.integration.android.IntentIntegrator
import com.gordonwong.materialsheetfab.MaterialSheetFab
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var pDialog: ProgressDialog
    private lateinit var icNotFound: ImageView
    private lateinit var api: Api
    private lateinit var assetRecycler: RecyclerView
    private lateinit var materialSheetFab: MaterialSheetFab<Fab>
    private var jwt = ""
    private lateinit var username: String
    private lateinit var pref: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref  = applicationContext.getSharedPreferences("MyPref", MODE_PRIVATE)
        username = pref.getString("username", "")!!
        val qrButton: CardView = findViewById(R.id.qr_button)
        assetRecycler = findViewById(R.id.recycler_chicken)
        icNotFound = findViewById(R.id.img_notFound)
        qrButton.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
            intentIntegrator.initiateScan()
        }

        initFabButton()

        pDialog = ProgressDialog(this@MainActivity)
        pDialog.setMessage("Loading...")
        api = RetrofitClientInstance
            .getRetrofitInstance()
            .create(Api::class.java)

    }

    override fun onResume() {
        super.onResume()
        getJwt()
    }

    private fun initFabButton() {
        val fab: Fab = findViewById<View>(R.id.fab) as Fab
        val sheetView = findViewById<View>(R.id.fab_sheet)
        val overlay = findViewById<View>(R.id.overlay)
        val sheetColor = resources.getColor(R.color.white)
        val fabColor = resources.getColor(R.color.purple_200)

        fab.setColorFilter(Color.WHITE);

        val btnWallet: LinearLayout = findViewById<View>(R.id.menu_btn_wallet) as LinearLayout
        btnWallet.setOnClickListener {
            startActivity(Intent(this, WalletActivity::class.java))
            materialSheetFab.hideSheet()
        }

        materialSheetFab = MaterialSheetFab(
            fab, sheetView, overlay,
            sheetColor, fabColor
        )
    }

    override fun onBackPressed() {
        if (materialSheetFab.isSheetVisible) {
            materialSheetFab.hideSheet()
        } else {
            super.onBackPressed()
        }
    }

    private fun getJwt(){
        pDialog.show()
        val call = api.authentication(AuthReqBody("username@Admin","12345678"))
        call.enqueue(object : Callback<AuthResponse?> {
            override fun onResponse(call: Call<AuthResponse?>, response: Response<AuthResponse?>) {

                Log.i("ReqJwt_Success", response.body()!!.token)
                jwt = response.body()!!.token
                getAllAssets()

            }

            override fun onFailure(call: Call<AuthResponse?>, t: Throwable) {
                pDialog.dismiss()
                val err = "Something went wrong...Please try later!"
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                Log.i("ReqJwt_Error", t.message.toString())
            }
        })
    }

    private fun getAllAssets() {
        val call = api.getAllAssets(username, "Bearer $jwt")
        call.enqueue(object : Callback<GetAllAssetsResponse?> {
            override fun onResponse(
                call: Call<GetAllAssetsResponse?>,
                response: Response<GetAllAssetsResponse?>
            ) {
                pDialog.dismiss()
                Log.i("ReqGetAllChickens_Success", response.toString())
                val assets: List<Asset> = response.body()!!.result
                if (assets.isEmpty()) {
                    icNotFound.visibility = View.VISIBLE
                } else {
                    icNotFound.visibility = View.GONE
                    assetRecycler.layoutManager = LinearLayoutManager(
                        this@MainActivity
                    )
                    assetRecycler.adapter = AssetAdapter(
                        layoutInflater,
                        assets,
                        R.layout.recycler_asset_item,
                        jwt,
                        pref
                    )
                }
//                Toast.makeText(this@MainActivity, response.body()!!.result, Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<GetAllAssetsResponse?>, t: Throwable) {
                pDialog.dismiss()
                val err = "Something went wrong...Please try later!"
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                Log.i("ReqGetAllChickens_Error", t.message.toString())
                finish()
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        if (result != null && result.contents != null) {

            Log.i("QrCode", result.contents)
            val intent = Intent(this, HistoryTimeLineActivity::class.java)
            intent.putExtra("jwt", jwt)
            intent.putExtra("id", result.contents)
            startActivity(intent)

        }
    }

}