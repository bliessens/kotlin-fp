package scheduling

import java.time.LocalDate
import java.time.LocalTime


object schedule {
    infix fun meeting(init: ScheduleBuilder.() -> Unit): ScheduleBuilder {
        return ScheduleBuilder().apply(init)
    }
}

object assign
object starts
object ends
object on
object attendees

class ScheduleBuilder {

    var name: String? = null
    var date: LocalDate? = null
    var start: LocalTime? = null
    var end: LocalTime? = null
    val participants = mutableListOf<String>()

    infix fun assign.name(name: String) {
        this@ScheduleBuilder.name = name
    }

    infix fun starts.at(range: IntRange) {
        this@ScheduleBuilder.start = LocalTime.of(range.start, range.endInclusive)
    }

    infix fun ends.at(range: IntRange) {
        this@ScheduleBuilder.end = LocalTime.of(range.start, range.endInclusive)
    }

    infix fun on.date(dayOfMonth: Int): DateBuilder {
        return DateBuilder(dayOfMonth)
    }

    infix fun attendees.include(name: String): AttendeeBuilder {
        this@ScheduleBuilder.participants += name
        return AttendeeBuilder()
    }

    override fun toString(): String {
        return """Meeting ${name}, 
            |planned ${date} starting at ${start} til ${end}
            |with attendees ${participants}
        """.trimMargin()
    }

    inner class DateBuilder(val day: Int) {
        infix fun January(year: Int) {
            this@ScheduleBuilder.date = LocalDate.of(year, 1, day)
        }

        infix fun February(year: Int) {
            this@ScheduleBuilder.date = LocalDate.of(year, 2, day)
        }

        infix fun March(year: Int) {
            this@ScheduleBuilder.date = LocalDate.of(year, 3, day)
        }

        infix fun April(year: Int) {
            this@ScheduleBuilder.date = LocalDate.of(year, 4, day)
        }

        infix fun May(year: Int) {
            this@ScheduleBuilder.date = LocalDate.of(year, 5, day)
        }

        infix fun June(year: Int) {
            this@ScheduleBuilder.date = LocalDate.of(year, 6, day)
        }

        infix fun July(year: Int) {
            this@ScheduleBuilder.date = LocalDate.of(year, 7, day)
        }

        infix fun August(year: Int) {
            this@ScheduleBuilder.date = LocalDate.of(year, 8, day)
        }

        infix fun September(year: Int) {
            this@ScheduleBuilder.date = LocalDate.of(year, 9, day)
        }
    }

    inner class AttendeeBuilder {
        infix fun and(name: String): AttendeeBuilder {
            this@ScheduleBuilder.participants += name
            return AttendeeBuilder()
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
