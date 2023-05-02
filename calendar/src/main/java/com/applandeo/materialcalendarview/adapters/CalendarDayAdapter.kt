package com.applandeo.materialcalendarview.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.R
import com.applandeo.materialcalendarview.exceptions.InvalidCustomLayoutException
import com.applandeo.materialcalendarview.utils.*
import kotlinx.android.synthetic.main.calendar_view_day.view.*
import java.util.*


private const val INVISIBLE_IMAGE_ALPHA = 0.12f

/**
 * This class is responsible for loading a one day cell.
 *
 *
 * Created by Applandeo team
 */
class CalendarDayAdapter(
    context: Context,
    private val calendarPageAdapter: CalendarPageAdapter,
    private val calendarProperties: CalendarProperties,
    dates: MutableList<Date>,
    pageMonth: Int
) : ArrayAdapter<Date>(context, calendarProperties.itemLayoutResource, dates) {

    private val pageMonth = if (pageMonth < 0) 11 else pageMonth

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val dayView = view
                ?: LayoutInflater.from(context).inflate(
                    calendarProperties.itemLayoutResource,
                    parent,
                    false
                )

        val day = GregorianCalendar().apply { time = getItem(position) }

        // todo 여기서 dayIcon 대신 textView 올려서
        //dayView.dayIcon?.loadIcon(day)


        if (calendarProperties.isDiet) {
            dayView.dietLayout.visibility = View.VISIBLE
            dayView.dayText.visibility = View.GONE

            calendarProperties.eventDays.lastOrNull { it.calendar == day }?.let { eventDay ->
                // diet
                if(!eventDay.isDiet) return@let
                dayView.menu1Text.text = eventDay.menu1
                dayView.menu2Text.text = eventDay.menu2
                dayView.menu3Text.text = eventDay.menu3

                val textColor = ContextCompat.getColor(context, eventDay.textColor)
                dayView.menu1Text.setTextColor(textColor)
                dayView.menu2Text.setTextColor(textColor)
                dayView.menu3Text.setTextColor(textColor)

                val backgroundColor = ContextCompat.getColor(context, eventDay.backgroundColor)
                dayView.mainLayout.setBackgroundColor(backgroundColor)
            }
        } else {
            dayView.dietLayout.visibility = View.GONE
            dayView.dayText.visibility = View.VISIBLE

            calendarProperties.eventDays.firstOrNull { it.calendar == day }?.let { eventDay ->
                // schedule
                if(eventDay.isDiet) return@let
                dayView.dayText.text = eventDay.text
                val textColor = ContextCompat.getColor(context, eventDay.textColor)
                dayView.dayText.setTextColor(textColor)
                val backgroundColor = ContextCompat.getColor(context, eventDay.backgroundColor)
                dayView.mainLayout.setBackgroundColor(backgroundColor)
            }
        }

        val dayLabel = dayView.dayLabel ?: throw InvalidCustomLayoutException

        setLabelColors(dayLabel, day)
        dayLabel.typeface = calendarProperties.typeface
        dayLabel.text = day[Calendar.DAY_OF_MONTH].toString()

        return dayView
    }

    private fun setLabelColors(dayLabel: TextView, day: Calendar) {
        when {
            // Setting not current month day color
            !day.isCurrentMonthDay() && !calendarProperties.selectionBetweenMonthsEnabled ->
                dayLabel.setDayColors(calendarProperties.anotherMonthsDaysLabelsColor)

            // Setting view for all SelectedDays
            day.isSelectedDay() -> {
                calendarPageAdapter.selectedDays
                        .firstOrNull { selectedDay -> selectedDay.calendar == day }
                        ?.let { selectedDay -> selectedDay.view = dayLabel }
                setSelectedDayColors(dayLabel, day, calendarProperties)
            }

            // Setting not current month day color only if selection between months is enabled for range picker
            !day.isCurrentMonthDay() && calendarProperties.selectionBetweenMonthsEnabled -> {
                if (SelectedDay(day) !in calendarPageAdapter.selectedDays) {
                    dayLabel.setDayColors(calendarProperties.anotherMonthsDaysLabelsColor)
                }
            }

            // Setting disabled days color
            !day.isActiveDay() -> dayLabel.setDayColors(calendarProperties.disabledDaysLabelsColor)

            // Setting custom label color for event day
            day.isEventDayWithLabelColor() -> setCurrentMonthDayColors(
                day,
                dayLabel,
                calendarProperties
            )

            // Setting current month day color
            else -> setCurrentMonthDayColors(day, dayLabel, calendarProperties)
        }
    }

    private fun Calendar.isSelectedDay() = calendarProperties.calendarType != CalendarView.CLASSIC
            && SelectedDay(this) in calendarPageAdapter.selectedDays
            && if (!calendarProperties.selectionBetweenMonthsEnabled) this[Calendar.MONTH] == pageMonth else true

    private fun Calendar.isEventDayWithLabelColor() = this.isEventDayWithLabelColor(
        calendarProperties
    )

    private fun Calendar.isCurrentMonthDay() = this[Calendar.MONTH] == pageMonth
            && !(calendarProperties.minimumDate != null
            && this.before(calendarProperties.minimumDate)
            || calendarProperties.maximumDate != null
            && this.after(calendarProperties.maximumDate))

    private fun Calendar.isActiveDay() = this !in calendarProperties.disabledDays

    private fun ImageView.loadIcon(day: Calendar) {
        if (!calendarProperties.eventsEnabled) {
            visibility = View.GONE
            return
        }

        calendarProperties.eventDays.firstOrNull { it.calendar == day }?.let { eventDay ->
            loadImage(eventDay.imageDrawable)
            // If a day doesn't belong to current month then image is transparent
            if (!day.isCurrentMonthDay() || !day.isActiveDay()) {
                alpha = INVISIBLE_IMAGE_ALPHA
            }
        }
    }
}