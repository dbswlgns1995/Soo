package com.applandeo.materialcalendarview

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.RestrictTo
import com.applandeo.materialcalendarview.utils.EventImage
import com.applandeo.materialcalendarview.utils.setMidnight
import java.util.*

/**
 * This class represents an event of a day. An instance of this class is returned when user click
 * a day cell. This class can be overridden to make calendar more functional. A list of instances of
 * this class can be passed to CalendarView object using setEvents() method.
 *
 *
 * @param calendar      Calendar object which represents a date of the event
 * @param drawable Drawable which will be displayed in a day cell
 * @param drawableRes Drawable resource which will be displayed in a day cell
 * @param labelColor Color which will be displayed as row label text color
 *
 * Created by Applandeo Team.
 */

data class EventDay(val calendar: Calendar) {
    //An object which contains image to display in the day row
    internal var imageDrawable: EventImage = EventImage.EmptyEventImage
    internal var text: String = ""
    internal var backgroundColor: Int = 0
    internal var textColor: Int = 0

    internal var menu1: String = ""
    internal var menu2: String = ""
    internal var menu3: String = ""

    internal var isDiet: Boolean = false

    internal var labelColor: Int = 0

    @set:RestrictTo(RestrictTo.Scope.LIBRARY)
    var isEnabled: Boolean = false

    init {
        calendar.setMidnight()
    }

    constructor(isDiet: Boolean = false, day: Calendar, text: String, @DrawableRes backgroundColor: Int, @DrawableRes textColor: Int) : this(day) {
        this.isDiet = isDiet
        this.text = text
        this.backgroundColor = backgroundColor
        this.textColor = textColor
    }

    constructor(isDiet: Boolean = true, day: Calendar, menu1: String, menu2: String, menu3: String, @DrawableRes backgroundColor: Int, @DrawableRes textColor: Int) : this(day) {
        this.isDiet = isDiet
        this.menu1 = menu1
        this.menu2 = menu2
        this.menu3 = menu3
        this.backgroundColor = backgroundColor
        this.textColor = textColor
    }


    constructor(day: Calendar, drawable: Drawable) : this(day) {
        imageDrawable = EventImage.EventImageDrawable(drawable)
    }

    constructor(day: Calendar, @DrawableRes drawableRes: Int) : this(day) {
        imageDrawable = EventImage.EventImageResource(drawableRes)
    }

    constructor(day: Calendar, @DrawableRes drawableRes: Int, @ColorInt labelColor: Int) : this(day) {
        imageDrawable = EventImage.EventImageResource(drawableRes)
        this.labelColor = labelColor
    }
}