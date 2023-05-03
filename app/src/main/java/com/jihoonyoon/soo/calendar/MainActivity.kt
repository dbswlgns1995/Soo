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
import com.jihoonyoon.soo.notepad.database.DietDao
import com.jihoonyoon.soo.notepad.database.ScheduleDao
import com.jihoonyoon.soo.notepad.models.*
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.sdsmdg.tastytoast.TastyToast
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup
import java.text.SimpleDateFormat
import java.util.*
import com.jihoonyoon.soo.notepad.models.Diet


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

    var sdf = SimpleDateFormat("yyyy년 M월 d일", Locale.KOREA)

    var events: ArrayList<EventDay> = ArrayList()

    private val backgroundColorList = listOf(
        R.color.white,
        R.color.pastel_blue,
        R.color.pastel_yellow,
        R.color.pastel_red
    )
    private val backgroundColorId = listOf(
        R.id.backgroundColor1,
        R.id.backgroundColor2,
        R.id.backgroundColor3,
        R.id.backgroundColor4,
    )

    private val textColorList = listOf(R.color.black, R.color.red, R.color.blue)
    private  val textColorId = listOf(R.id.textColor1, R.id.textColor2, R.id.textColor3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        initViews()
        setEvent()
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

        backgroundColorGroup.selectButton(R.id.backgroundColor1)

        textColorGroup.selectButton(R.id.textColor1)

        initDietSwitch()
        initSaveButton()
        initClearButton()
    }

    // 식단 스위치
    private fun initDietSwitch() {
        dietLinearLayout.isVisible = false
        scheduleEditText.isVisible = true

        dietSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
            dietLinearLayout.isVisible = isChecked
            scheduleEditText.isVisible = !isChecked

            initSelectDayUI()

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
                initSelectDayUI()
            }
        })

//        calendarView.setOnPreviousPageChangeListener(object : OnCalendarPageChangedListener,
//            OnCalendarPageChangeListener {
//            override fun onChange() {
//                Log.d(TAG, "prev: ${sdf.format(calendarView.currentPageDate.time).toString()}")
//            }
//
//            override fun invoke(p1: Int) {
//            }
//
//        })
//
//        calendarView.setOnForwardPageChangeListener(object : OnCalendarPageChangedListener,
//            OnCalendarPageChangeListener {
//            override fun onChange() {
//                Log.d(TAG, "forw: ${sdf.format(calendarView.currentPageDate.time).toString()}")
//            }
//
//            override fun invoke(p1: Int) {
//            }
//
//        })
    }

    private fun initSaveButton() {
        saveButton.setOnClickListener {

            val backgroundColor = when (backgroundColorGroup.selectedButtons[0].id) {
                R.id.backgroundColor1 -> backgroundColorList[0]
                R.id.backgroundColor2 -> backgroundColorList[1]
                R.id.backgroundColor3 -> backgroundColorList[2]
                R.id.backgroundColor4 -> backgroundColorList[3]
                else -> backgroundColorList[0]
            }

            val textColor = when (textColorGroup.selectedButtons[0].id) {
                R.id.textColor1 -> textColorList[0]
                R.id.textColor2 -> textColorList[1]
                R.id.textColor3 -> textColorList[2]
                else -> textColorList[0]
            }

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

                val currentSchedule =
                    ScheduleDao.getScheduleByCalendar(setSaveCalendar(currentDate))

                if (currentSchedule == null) {
                    // todo 없으면 insert
                    Log.d(TAG, "없엉 $currentSchedule")

                    val schedule = Schedule()

                    schedule.calendar = setSaveCalendar(currentDate)
                    schedule.text = scheduleEditText.text.toString()
                    schedule.backgroundColor = backgroundColor
                    schedule.textColor = textColor

                    schedule.save()

                    TastyToast.makeText(
                        this,
                        "저장 완료",
                        TastyToast.LENGTH_SHORT,
                        TastyToast.SUCCESS
                    )

                    setEvent()

                } else {
                    // todo 있으면 update
                    Log.d(TAG, "삭제후 업데이트 $currentSchedule")

                    ScheduleDao.removeScheduleByCalendar(setSaveCalendar(currentDate))

                    val schedule = Schedule()
                    schedule.calendar = setSaveCalendar(currentDate)
                    schedule.text = scheduleEditText.text.toString()
                    schedule.backgroundColor = backgroundColor
                    schedule.textColor = textColor
                    schedule.save()

                    TastyToast.makeText(
                        this,
                        "수정 완료",
                        TastyToast.LENGTH_SHORT,
                        TastyToast.SUCCESS
                    )
                    setEvent()
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

                val currentDiet =
                    DietDao.getDietByCalendar(setSaveCalendar(currentDate))

                if (currentDiet == null) {
                    // todo 없으면 insert
                    Log.d(TAG, "없엉 $currentDiet")

                    val diet = Diet()

                    diet.calendar = setSaveCalendar(currentDate)
                    diet.menu1 = menu1EditText.text.toString()
                    diet.menu2 = menu2EditText.text.toString()
                    diet.menu3 = menu3EditText.text.toString()
                    diet.backgroundColor = backgroundColor
                    diet.textColor = textColor

                    diet.save()

                    TastyToast.makeText(
                        this,
                        "저장 완료",
                        TastyToast.LENGTH_SHORT,
                        TastyToast.SUCCESS
                    )

                    setEvent()

                } else {
                    // todo 있으면 update
                    Log.d(TAG, "삭제후 업데이트 $currentDiet")

                    DietDao.removeDietByCalendar(setSaveCalendar(currentDate))

                    val diet = Diet()

                    diet.calendar = setSaveCalendar(currentDate)
                    diet.menu1 = menu1EditText.text.toString()
                    diet.menu2 = menu2EditText.text.toString()
                    diet.menu3 = menu3EditText.text.toString()
                    diet.backgroundColor = backgroundColor
                    diet.textColor = textColor

                    diet.save()

                    TastyToast.makeText(
                        this,
                        "수정 완료",
                        TastyToast.LENGTH_SHORT,
                        TastyToast.SUCCESS
                    )
                    setEvent()
                }

            }
        }
    }

    private fun initClearButton() {
        clearButton.setOnClickListener {
            if (!dietSwitch.isChecked) {
                // todo 스케쥴

                Log.d(TAG, "스케쥴")

                val currentSchedule =
                    ScheduleDao.getScheduleByCalendar(setSaveCalendar(currentDate))

                if (currentSchedule != null) {
                    // 해당 스케쥴 있으면
                    Log.d(TAG, "있음 $currentSchedule")
                    ScheduleDao.removeScheduleByCalendar(setSaveCalendar(currentDate))

                    TastyToast.makeText(
                        this,
                        "삭제 완료",
                        TastyToast.LENGTH_SHORT,
                        TastyToast.SUCCESS
                    )

                    scheduleEditText.text.clear()
                    backgroundColorGroup.selectButton(backgroundColorId[0])
                    textColorGroup.selectButton(textColorId[0])

                    setEvent()

                } else {
                    Log.d(TAG, "없음 $currentSchedule")
                    scheduleEditText.text.clear()
                    backgroundColorGroup.selectButton(backgroundColorId[0])
                    textColorGroup.selectButton(textColorId[0])
                }

            } else {
                // todo 식단

                Log.d(TAG, "식단")

                val currentDiet =
                    DietDao.getDietByCalendar(setSaveCalendar(currentDate))

                if (currentDiet != null) {
                    // 해당 스케쥴 있으면
                    Log.d(TAG, "있음 $currentDiet")
                    DietDao.removeDietByCalendar(setSaveCalendar(currentDate))

                    TastyToast.makeText(
                        this,
                        "삭제 완료",
                        TastyToast.LENGTH_SHORT,
                        TastyToast.SUCCESS
                    )

                    menu1EditText.text.clear()
                    menu2EditText.text.clear()
                    menu3EditText.text.clear()
                    backgroundColorGroup.selectButton(backgroundColorId[0])
                    textColorGroup.selectButton(textColorId[0])

                    setEvent()

                } else {
                    Log.d(TAG, "없음 $currentDiet")
                    menu1EditText.text.clear()
                    menu2EditText.text.clear()
                    menu3EditText.text.clear()
                    backgroundColorGroup.selectButton(backgroundColorId[0])
                    textColorGroup.selectButton(textColorId[0])
                }

            }
        }
    }

    // calendar set 18:00:00
    private fun setSaveCalendar(calendar: Calendar): Calendar {
        val result = calendar.clone() as Calendar
        result.set(Calendar.HOUR_OF_DAY, 18)
        result.set(Calendar.MINUTE, 0)
        result.set(Calendar.SECOND, 0)
        result.set(Calendar.MILLISECOND, 0)
        return result
    }

    private fun initSelectDayUI(){
        if (!dietSwitch.isChecked) {
            // todo 스케쥴
            val currentSchedule =
                ScheduleDao.getScheduleByCalendar(setSaveCalendar(currentDate))

            if (currentSchedule != null) {
                scheduleEditText.setText(currentSchedule.text)
                backgroundColorGroup.selectButton(backgroundColorId[backgroundColorList.indexOf(currentSchedule.backgroundColor)])
                textColorGroup.selectButton(textColorId[textColorList.indexOf(currentSchedule.textColor)])
            } else {
                scheduleEditText.text.clear()
                backgroundColorGroup.selectButton(backgroundColorId[0])
                textColorGroup.selectButton(textColorId[0])
            }

        } else {
            // todo 식단
            val currentDiet =
                DietDao.getDietByCalendar(setSaveCalendar(currentDate))

            if (currentDiet != null) {
                menu1EditText.setText(currentDiet.menu1)
                menu2EditText.setText(currentDiet.menu2)
                menu3EditText.setText(currentDiet.menu3)
                backgroundColorGroup.selectButton(backgroundColorId[backgroundColorList.indexOf(currentDiet.backgroundColor)])
                textColorGroup.selectButton(textColorId[textColorList.indexOf(currentDiet.textColor)])
            } else {
                menu1EditText.text.clear()
                menu2EditText.text.clear()
                menu3EditText.text.clear()
                backgroundColorGroup.selectButton(backgroundColorId[0])
                textColorGroup.selectButton(textColorId[0])
            }
        }


    }

    private fun setEvent() {
        events.clear()

        ScheduleDao.getAllSchedule().let {
            Log.d(TAG, "setEvent: ${it.count()}")
            it.forEach { schedule ->
                events.add(
                    EventDay(
                        false,
                        schedule.calendar,
                        schedule.text,
                        schedule.backgroundColor,
                        schedule.textColor
                    )
                )
            }
        }

        DietDao.getAllDiet().let {
            Log.d(TAG, "setEvent: ${it.count()}")
            it.forEach { diet ->
                events.add(
                    EventDay(
                        true,
                        diet.calendar,
                        diet.menu1,
                        diet.menu2,
                        diet.menu3,
                        diet.backgroundColor,
                        diet.textColor
                    )
                )
            }
        }

        Log.d(TAG, "setEvent: ${events.count()}")
        
        calendarView.setEvents(events)

    }
}