package com.example.parkingsystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkingsystem.FirebaseConst
import com.example.parkingsystem.R
import com.example.parkingsystem.adapter.CarAdapter
import com.example.parkingsystem.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_car_list.*
import kotlinx.android.synthetic.main.layout_car_item.*

class UserCarList : AppCompatActivity() {

    private val db by lazy{ FirebaseFirestore.getInstance()}
    private val auth by lazy{ FirebaseAuth.getInstance()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_car_list)
        (this as AppCompatActivity).supportActionBar?.title = "Машины пользователя"
        initCarList(auth.currentUser!!.uid)
    }
    private fun initCarList( uid:String){
        own_car_list.layoutManager = LinearLayoutManager(this)
        db.collection(FirebaseConst.USER_COLLECTION)
            .document(uid)
            .addSnapshotListener { snapshot, e ->
                if (e != null){
                    Log.d("tagtagtag", e.message)
                    return@addSnapshotListener
                }
                val user = snapshot?.toObject(User::class.java)
                if(user != null){
                    profile_phone.text = user.phone
                    profile_email.text = user.username
                    own_car_list.adapter = CarAdapter(user.carList)
                }
            }

        designRecyclerView()
    }

    private fun designRecyclerView(){
        (own_car_list.layoutManager as LinearLayoutManager).reverseLayout = true
        (own_car_list.layoutManager as LinearLayoutManager).stackFromEnd = true
        val mDividerItemDecoration = DividerItemDecoration(
            own_car_list.context,
            (own_car_list.layoutManager as LinearLayoutManager).getOrientation()
        )
        own_car_list.addItemDecoration(mDividerItemDecoration)
    }



}
