package com.example.metadescqrscanner

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.sangcomz.stickytimelineview.TimeLineRecyclerView
import xyz.sangcomz.stickytimelineview.callback.SectionCallback
import xyz.sangcomz.stickytimelineview.model.SectionInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HistoryTimeLineActivity : AppCompatActivity() {

    private lateinit var pDialog: ProgressDialog
    private lateinit var api: Api
    private var jwt = ""
    private var id = ""
    private lateinit var username: String
    private var balance: Float = 0.0F
    private var price: Double = 0.0
    private lateinit var pref: SharedPreferences

    val icFinkl: Drawable? by lazy {
        AppCompatResources.getDrawable(this@HistoryTimeLineActivity, R.drawable.ic_finkl)
    }
    val icBuzz: Drawable? by lazy {
        AppCompatResources.getDrawable(this@HistoryTimeLineActivity, R.drawable.ic_buzz)
    }
    val icWannaOne: Drawable? by lazy {
        AppCompatResources.getDrawable(this@HistoryTimeLineActivity, R.drawable.ic_wannaone)
    }
    val icGirlsGeneration: Drawable? by lazy {
        AppCompatResources.getDrawable(this@HistoryTimeLineActivity, R.drawable.ic_girlsgeneration)
    }
    val icSolo: Drawable? by lazy {
        AppCompatResources.getDrawable(this@HistoryTimeLineActivity, R.drawable.ic_solo)
    }

    private lateinit var verticalRecyclerView: TimeLineRecyclerView
    private lateinit var btnBuy: Button

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_time_line)
        verticalRecyclerView = findViewById(R.id.recycler_view)
        btnBuy = findViewById(R.id.btn_buy)

        pref  = applicationContext.getSharedPreferences("MyPref", MODE_PRIVATE)
        username = pref.getString("username", "")!!
        balance = pref.getFloat("balance", 0.0F)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.layout_buy_dialog, null)
//        dialogView.background = D
        val btnPayment = dialogView.findViewById<Button>(R.id.btn_payment)
        val tvPrice = dialogView.findViewById<TextView>(R.id.tv_price)
        tvPrice.text = "$price USD"

        btnBuy.setOnClickListener {
            tvPrice.text = "$price USD"
            val dialog = DialogPlus.newDialog(this)
                .setContentHolder(ViewHolder(dialogView))
                .setContentBackgroundResource(R.drawable.bg_buy_dialog)
                .setOnItemClickListener { dialog, item, view, position -> }
                .setExpanded(true)
                .create()
            dialog.show()
            btnPayment.setOnClickListener {
                if (price <= balance) {
                    changeOwner()
                } else {
                    Toast.makeText(this,"Please charge your wallet.", Toast.LENGTH_LONG).show()
                }

            }
        }

        pDialog = ProgressDialog(this@HistoryTimeLineActivity)
        pDialog.setMessage("Loading...")
        api = RetrofitClientInstance
            .getRetrofitInstance()
            .create(Api::class.java)
        jwt = intent.getStringExtra("jwt").toString()
        id = intent.getStringExtra("id").toString()
        val fromList = intent.getBooleanExtra("FromList",false);
        if (fromList) {
            btnBuy.visibility = View.INVISIBLE
        }

        getDataFromServer()


    }

    private fun changeOwner() {
        pDialog.show()
        val call = api.changeOwner(ChangeOwnerReqBody(id, username), "Bearer $jwt")
        call.enqueue(object : Callback<ChangeOwnerReqResponse?> {
            override fun onResponse(
                call: Call<ChangeOwnerReqResponse?>,
                response: Response<ChangeOwnerReqResponse?>
            ) {
                pDialog.dismiss()
                Log.i("ReqHistory_Success", response.toString())
                Toast.makeText(this@HistoryTimeLineActivity, response.body()!!.result.message, Toast.LENGTH_LONG).show()
                val editor: SharedPreferences.Editor = pref.edit()
                val newBalance = balance - price
                editor.putFloat("balance", newBalance.toFloat())
                val sdf = SimpleDateFormat("dd/M/yyyy")
                val currentDate = sdf.format(Date())
                editor.putString(id, currentDate)
                editor.apply()
                finish()
            }

            override fun onFailure(call: Call<ChangeOwnerReqResponse?>, t: Throwable) {
                pDialog.dismiss()
                val err = "Something went wrong...Please try later!"
                Toast.makeText(this@HistoryTimeLineActivity, t.message, Toast.LENGTH_LONG).show()
                Log.i("ReqHistory_Error", t.message.toString())
                finish()
            }
        })
    }

    private fun getDataFromServer() {
        pDialog.show()
        val call = api.getAssetHistory(id, "Bearer $jwt")
        call.enqueue(object : Callback<HistoryReqResult?> {
            override fun onResponse(
                call: Call<HistoryReqResult?>,
                response: Response<HistoryReqResult?>
            ) {
                pDialog.dismiss()
                Log.i("ReqHistory_Success", response.toString())
                price = response.body()!!.result?.get(0)?.asset?.price!!
                initVerticalRecyclerView(response.body()!!.result)
            }

            override fun onFailure(call: Call<HistoryReqResult?>, t: Throwable) {
                pDialog.dismiss()
                val err = "Something went wrong...Please try later!"
                Toast.makeText(this@HistoryTimeLineActivity, t.message, Toast.LENGTH_LONG).show()
                Log.i("ReqHistory_Error", t.message.toString())
                finish()
            }
        })
    }

    private fun initVerticalRecyclerView(history: List<HistoryItem>) {
        val timeLineList = ArrayList<TimeLineRowData>()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        for (item: HistoryItem in history) {
            Log.i("ListItems", item.asset.txType)
            when(item.asset.txType) {
                "CreateAsset" -> {
                    timeLineList.add(TimeLineRowData("", item.timestamp.split(".")[0], "This asset generated.", "CreateAsset"))
                }
                "PutAttr" -> {
                    timeLineList.add(TimeLineRowData("", item.timestamp.split(".")[0], "-${item.asset.attrs[item.asset.attrs.size - 1].key} \n-${item.asset.attrs[item.asset.attrs.size - 1].value} \n-instruction: ${item.asset.attrs[item.asset.attrs.size - 1].instruction}", "PutAttr"))
                }
                "ChangeOwner" -> {
                    val name = item.asset.owner.split("@")
                    timeLineList.add(TimeLineRowData("", item.timestamp.split(".")[0], "Owner changed to ${name[name.size - 1]}", "ChangeOwner"))
                }
                "SellAsset" -> {
                    timeLineList.add(TimeLineRowData("", item.timestamp.split(".")[0], "${item.asset.owner.split("@")[1]} bought this asset.", "SellAsset"))
                }
                "ChangeStatus" -> {
                    timeLineList.add(TimeLineRowData("", item.timestamp.split(".")[0], "Status changed to ${item.asset.status}.", "ChangeStatus"))
                }
                "SendToShop" -> {
                    timeLineList.add(TimeLineRowData("", item.timestamp.split(".")[0], "This asset sent to shop.", "SendToShop"))
                }
            }
        }

        timeLineList.reverse()
        verticalRecyclerView.adapter = TimeLineAdapter(
            layoutInflater,
            timeLineList,
            R.layout.recycler_row
        )

        //Currently only LinearLayoutManager is supported.
        verticalRecyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        verticalRecyclerView.addItemDecoration(getSectionCallback(timeLineList))
    }

    //Get SectionCallback method for timeline
    private fun getSectionCallback(singerList: List<TimeLineRowData>): SectionCallback {
        return object : SectionCallback {
            //In your data, implement a method to determine if this is a section.
            override fun isSection(position: Int): Boolean =
                singerList[position].debuted != singerList[position - 1].debuted

            //Implement a method that returns a SectionHeader.
            override fun getSectionHeader(position: Int): SectionInfo? {
                val singer = singerList[position]
                return SectionInfo(singer.debuted, singer.group)
            }

        }
    }

    private fun getSectionCallbackWithDrawable(singerList: List<TimeLineRowData>): SectionCallback {
        return object : SectionCallback {
            //In your data, implement a method to determine if this is a section.
            override fun isSection(position: Int): Boolean =
                singerList[position].debuted != singerList[position - 1].debuted

            //Implement a method that returns a SectionHeader.
            override fun getSectionHeader(position: Int): SectionInfo? {
                val singer = singerList[position]
                val dot: Drawable? = when (singer.group) {
                    "FIN.K.L" -> {
                        icFinkl
                    }
                    "Girls' Generation" -> {
                        icGirlsGeneration
                    }
                    "Buzz" -> {
                        icBuzz
                    }
                    "Wanna One" -> {
                        icWannaOne
                    }
                    else -> icSolo
                }
                return SectionInfo(singer.debuted, singer.group, dot)
            }

        }
    }
}