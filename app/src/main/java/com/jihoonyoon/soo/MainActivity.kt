package com.jihoonyoon.soo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.jihoonyoon.soo.notepad.activities.home.HomeActivity


class MainActivity : AppCompatActivity() {

    // todo viewModel에서 false true 변경 시 show / hide
    private var showMemoLayout = false

    // todo viewModel 에서 type 변경  Type - diet / memo
    private var showCalendarType = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews(){
        setCalendarView()

        findViewById<ImageButton>(R.id.memoImageButton).setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }


    }

    private fun setCalendarView() {
        findViewById<CalendarView>(R.id.calendarView).setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {

                findViewById<LinearLayout>(R.id.memoLayout).isVisible = !showMemoLayout
                showMemoLayout = !showMemoLayout

                findViewById<TextView>(R.id.memoDateTextView).text = eventDay.calendar.toString()
                // todo 데이터 가져오기

            }
        })
    }
}