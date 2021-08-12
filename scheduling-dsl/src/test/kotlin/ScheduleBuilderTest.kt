import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import scheduling.*
import java.time.LocalDate
import java.time.LocalTime

internal class ScheduleBuilderTest {

    @Test
    internal fun testName() {
        val meeting = schedule meeting {
            assign name "SCRUM"
        }

        assertThat(meeting.name).isEqualTo("SCRUM")
    }

    @Test
    internal fun testStartTimeOfMeeting() {
        val meeting = schedule meeting {
            starts at 12..0
        }

        assertThat(meeting.start).isEqualTo(LocalTime.of(12, 0))
    }

    @Test
    internal fun testEndTimeOfMeeting() {
        val meeting = schedule meeting {
            ends at 12..59
        }

        assertThat(meeting.end).isEqualTo(LocalTime.of(12, 59))
    }

    @Test
    internal fun testDateOfMeeting() {
        val meeting = schedule meeting {
            on date 12 August 2021
        }

        assertThat(meeting.date).isEqualTo(LocalDate.of(2021, 8, 12))
    }

    @Test
    internal fun testMeetingAttendees() {
        val meeting = schedule meeting {
            attendees include "me" and "you" and "the tribe" and "some groupies"
        }

        assertThat(meeting.participants)
            .hasSize(4)
            .contains("me", "you", "the tribe", "some groupies")
    }

}
