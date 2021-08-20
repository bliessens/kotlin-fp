package scheduling

import java.time.LocalDate
import java.time.LocalTime


object schedule {
    infix fun meeting(init: ScheduleBuilder.() -> Unit): ScheduleBuilder {
        return ScheduleBuilder().apply(init)
    }
}

class ScheduleBuilder {
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

        private fun scheduleOn(day: Int, month: Int, year: Int): Unit {
            schedule.date = LocalDate.of(day, month, year)
        }

        infix fun January(year: Int) = scheduleOn(day, 1, year)

        infix fun February(year: Int) = scheduleOn(day, 2, year)

        infix fun March(year: Int) = scheduleOn(day, 3, year)

        infix fun April(year: Int) = scheduleOn(day, 4, year)

        infix fun May(year: Int) = scheduleOn(day, 5, year)

        infix fun June(year: Int) = scheduleOn(day, 6, year)

        infix fun July(year: Int) = scheduleOn(day, 7, year)

        infix fun August(year: Int) = scheduleOn(day, 8, year)

        infix fun September(year: Int) = scheduleOn(day, 9, year)
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
        attendees include "a" and "b" and "c"
    }

    println(meeting)

}
