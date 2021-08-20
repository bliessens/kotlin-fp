package scheduling

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

class ScheduleBuilderTest {

    @Test
    fun testName() {
        val meeting = schedule meeting {
            assign name "SCRUM"
        }

        assertThat(meeting.name).isEqualTo("SCRUM")
    }

    @Test
    fun testStartTimeOfMeeting() {
        val meeting = schedule meeting {
            starts at 12..0
        }

        assertThat(meeting.start).isEqualTo(LocalTime.of(12, 0))
    }

    @Test
    fun testEndTimeOfMeeting() {
        val meeting = schedule meeting {
            ends at 12..59
        }

        assertThat(meeting.end).isEqualTo(LocalTime.of(12, 59))
    }

    @Test
    fun testDateOfMeeting() {
        val meeting = schedule meeting {
            on date 12 August 2021
        }

        assertThat(meeting.date).isEqualTo(LocalDate.of(2021, 8, 12))
    }

    @Disabled
    @Test
    fun testDateOfMeetingInvaldSyntax() {
        val meeting = schedule meeting {
            //on date  August //12 2021
        }

        assertThat(meeting.date).isEqualTo(LocalDate.of(2021, 8, 12))
    }

    @Test
    fun testMeetingAttendees() {
        val meeting = schedule meeting {
            attendees include "me" and "you" and "the tribe" and "some groupies"
        }

        assertThat(meeting.participants)
            .hasSize(4)
            .contains("me", "you", "the tribe", "some groupies")
    }

}
