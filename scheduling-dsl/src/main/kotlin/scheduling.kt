package scheduling

import java.time.LocalDate
import java.time.LocalTime


object schedule {
    infix fun meeting(init: ScheduleBuilder.() -> Unit): ScheduleBuilder {
        return ScheduleBuilder().apply(init).validate()
    }
}

class ScheduleBuilder {

    private val errors = mutableListOf<String>()

    var name: String? = null
    var date: LocalDate? = null
    var start: LocalTime? = null
    var end: LocalTime? = null
    val participants = mutableListOf<String>()

    val assign = this
    val starts = Starts()
    val ends = Ends()
    val on = On()
    val attendees = Attendee()
    infix fun name(name: String) {
        this@ScheduleBuilder.name = name
    }

    fun validate(): ScheduleBuilder {
        if (date == null)
            errors += "Date is mandatory"
        if (start == null)
            errors += "Start time is not given"

//        if (errors.isNotEmpty())
//            throw IllegalStateException(errors.toString())
        return this
    }

    override fun toString(): String {
        return """Meeting ${name}, 
            |planned ${date} starting at ${start} til ${end}
            |with attendees ${participants}
        """.trimMargin()
    }

    inner class Starts {
        infix fun at(time: IntRange) {
            this@ScheduleBuilder.start = LocalTime.of(time.start, time.endInclusive)
        }
    }

    inner class Ends {
        infix fun at(time: IntRange) {
            this@ScheduleBuilder.end = LocalTime.of(time.start, time.endInclusive)
        }
    }

    inner class On {
        infix fun date(dayOfMonth: Int): DateBuilder {
            return DateBuilder(dayOfMonth, this@ScheduleBuilder)
        }
    }

    inner class DateBuilder(val day: Int, val schedule: ScheduleBuilder) {

        private fun scheduleOn(month: Int, year: Int): Unit {
            schedule.date = LocalDate.of(year, month, day)
        }

        infix fun January(year: Int) = scheduleOn(1, year)

        infix fun February(year: Int) = scheduleOn(2, year)

        infix fun March(year: Int) = scheduleOn(3, year)

        infix fun April(year: Int) = scheduleOn(4, year)

        infix fun May(year: Int) = scheduleOn(5, year)

        infix fun June(year: Int) = scheduleOn(6, year)

        infix fun July(year: Int) = scheduleOn(7, year)

        infix fun August(year: Int) = scheduleOn(8, year)

        infix fun September(year: Int) = scheduleOn(9, year)
    }

    inner class Attendee {
        infix fun include(name: String): AttendeeContinuation {
            this@ScheduleBuilder.participants += name
            return AttendeeContinuation()
        }

        inner class AttendeeContinuation {
            infix fun and(name: String): AttendeeContinuation {
                this@ScheduleBuilder.participants += name
                return AttendeeContinuation()
            }
        }
    }
}

fun main() {

    val meeting = schedule meeting {
        assign name "SCRUM"
        starts at 10..15
        ends at 10..45
        on date 15 March 2020
        attendees include "Nick" and "Maria" and "Suzan"
    }

    println(meeting)

}
