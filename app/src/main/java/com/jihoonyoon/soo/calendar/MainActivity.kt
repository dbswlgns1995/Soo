package com.jihoonyoon.soo.calendar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.extensions.OnCalendarPageChangedListener
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.jihoonyoon.soo.R
import com.jihoonyoon.soo.notepad.activities.home.HomeActivity
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "myLog.mainActivity"
    }

    private var showMemoLayout = false

    private var showCalendarType = false

    private var currentDate = Calendar.getInstance()


    private lateinit var memoImageButton: ImageButton
    private lateinit var calendarView: CalendarView
    private lateinit var dietSwitch: Switch

    /**
     * 스케쥴 레이아웃
     */
    private lateinit var memoLayout: LinearLayout
    private lateinit var memoDateTextView: TextView
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button
    private lateinit var scheduleEditText: EditText

    /**
     * 다이어트 레이아웃
     */
    private lateinit var dietLinearLayout: LinearLayout
    private lateinit var menu1EditText: EditText
    private lateinit var menu2EditText: EditText
    private lateinit var menu3EditText: EditText

    /**
     * 배경색 그룹
     */
    private lateinit var backgroundColorGroup: ThemedToggleButtonGroup
    private lateinit var backgroundColor1: ThemedButton
    private lateinit var backgroundColor2: ThemedButton
    private lateinit var backgroundColor3: ThemedButton
    private lateinit var backgroundColor4: ThemedButton

    /**
     * 글자색 그룹
     */
    private lateinit var textColorGroup: ThemedToggleButtonGroup
    private lateinit var textColor1: ThemedButton
    private lateinit var textColor2: ThemedButton
    private lateinit var textColor3: ThemedButton


//    private lateinit var dietDao: DietDao
//    private lateinit var scheduleDao: ScheduleDao

    var sdf = SimpleDateFormat("yyyy년 M월 d일", Locale.KOREA)

    var events: ArrayList<EventDay> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        appDatabase = AppDatabase.getInstance(this)!!
//        dietDao = appDatabase.dietDao()
//        scheduleDao = appDatabase.scheduleDao()

        bindViews()
        initViews()
        bindData()
    }

    private fun bindViews() {
        calendarView = findViewById(R.id.calendarView)
        memoImageButton = findViewById(R.id.memoImageButton)
        dietSwitch = findViewById(R.id.dietSwitch)

        saveButton = findViewById(R.id.saveButton)
        clearButton = findViewById(R.id.clearButton)

        memoLayout = findViewById(R.id.memoLayout)
        memoDateTextView = findViewById(R.id.memoDateTextView)
        scheduleEditText = findViewById(R.id.scheduleEditText)

        dietLinearLayout = findViewById(R.id.dietLinearLayout)
        menu1EditText = findViewById(R.id.menu1EditText)
        menu2EditText = findViewById(R.id.menu2EditText)
        menu3EditText = findViewById(R.id.menu3EditText)

        backgroundColorGroup = findViewById(R.id.backgroundGroup)
        backgroundColor1 = findViewById(R.id.backgroundColor1)
        backgroundColor2 = findViewById(R.id.backgroundColor2)
        backgroundColor3 = findViewById(R.id.backgroundColor3)
        backgroundColor4 = findViewById(R.id.backgroundColor4)

        textColorGroup = findViewById(R.id.textColorGroup)
        textColor1 = findViewById(R.id.textColor1)
        textColor2 = findViewById(R.id.textColor2)
        textColor3 = findViewById(R.id.textColor3)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews() {
        setCalendarView()

        memoImageButton.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }

        backgroundColorGroup.setOnSelectListener {
            when (it.id) {
                R.id.backgroundColor1 -> {
                    Log.d(TAG, "1")
                }
                R.id.backgroundColor2 -> {
                    Log.d(TAG, "2")
                }
                R.id.backgroundColor3 -> {
                    Log.d(TAG, "3")
                }
                R.id.backgroundColor4 -> {
                    Log.d(TAG, "4")
                }
            }
        }
        backgroundColorGroup.selectButton(R.id.backgroundColor1)

        textColorGroup.setOnSelectListener {
            when (it.id) {
                R.id.textColor1 -> {
                    Log.d(TAG, "1")
                }
                R.id.textColor2 -> {
                    Log.d(TAG, "2")
                }
                R.id.textColor3 -> {
                    Log.d(TAG, "3")
                }
            }
        }
        textColorGroup.selectButton(R.id.textColor1)

        initDietSwitch()
        initSaveButton()
    }

    // 식단 스위치
    private fun initDietSwitch() {
        dietLinearLayout.isVisible = false
        scheduleEditText.isVisible = true

        dietSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
            dietLinearLayout.isVisible = isChecked
            scheduleEditText.isVisible = !isChecked

            calendarView.showDietView(isChecked)
        }
    }

    private fun setCalendarView() {
        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                // todo 같은 날 두번 클릭 시 들어가게금
                if (currentDate == eventDay.calendar && showMemoLayout) {
                    memoLayout.isVisible = false
                    showMemoLayout = false
                    return
                } else if (currentDate == eventDay.calendar && !showMemoLayout) {
                    // todo 같은 날 초기
                    memoLayout.isVisible = true
                    showMemoLayout = true
                    memoDateTextView.text = sdf.format(eventDay.calendar.time).toString()
                } else if (currentDate != eventDay.calendar && showMemoLayout) {
                    // todo 다른 날
                    // 데이터 가져오기
                    memoDateTextView.text = sdf.format(eventDay.calendar.time).toString()
                } else if (currentDate != eventDay.calendar && !showMemoLayout) {
                    // todo 다른 날
                    // 데이터 가져오기
                    memoLayout.isVisible = true
                    showMemoLayout = true
                    memoDateTextView.text = sdf.format(eventDay.calendar.time).toString()
                }
                currentDate = eventDay.calendar
            }
        })

        calendarView.setOnPreviousPageChangeListener(object : OnCalendarPageChangedListener,
            OnCalendarPageChangeListener {
            override fun onChange() {
                Log.d(TAG, "prev: ${sdf.format(calendarView.currentPageDate.time).toString()}")
            }

            override fun invoke(p1: Int) {
            }

        })

        calendarView.setOnForwardPageChangeListener(object : OnCalendarPageChangedListener,
            OnCalendarPageChangeListener {
            override fun onChange() {
                Log.d(TAG, "forw: ${sdf.format(calendarView.currentPageDate.time).toString()}")
            }

            override fun invoke(p1: Int) {
            }

        })
    }

    private fun initSaveButton() {
        saveButton.setOnClickListener {
            if (!dietSwitch.isChecked) {
                Log.d(TAG, "inactivate: ")
                // todo 스케쥴
                if (scheduleEditText.text.isBlank()) {
                    TastyToast.makeText(
                        this,
                        "내용 없음",
                        TastyToast.LENGTH_SHORT,
                        TastyToast.ERROR
                    )
                    return@setOnClickListener
                }


            } else {
                Log.d(TAG, "activate: ")
                // todo 다이어트
                if (menu1EditText.text.isBlank() && menu2EditText.text.isBlank() && menu3EditText.text.isBlank()) {
                    TastyToast.makeText(
                        this,
                        "내용 없음",
                        TastyToast.LENGTH_SHORT,
                        TastyToast.ERROR
                    )
                    return@setOnClickListener
                }

            }
        }
    }

    private fun bindData() {
        setEvent()
    }

    private fun setEvent() {
        events.clear()

//        CoroutineScope(Dispatchers.IO).launch {
//            scheduleDao.selectSchedule().forEach {
//                events.add(
//                    EventDay(
//                        false,
//                        it.calendar,
//                        it.text,
//                        it.backgroundColor,
//                        it.textColor
//                    )
//                )
//            }
//
//            dietDao.selectDiet().forEach {
//                events.add(
//                    EventDay(
//                        true,
//                        it.calendar,
//                        it.menu1,
//                        it.menu2,
//                        it.menu3,
//                        it.backgroundColor,
//                        it.textColor
//                    )
//                )
//            }
//
//            withContext(Dispatchers.IO) {
//                calendarView.setEvents(events)
//            }
//        }
    }
}