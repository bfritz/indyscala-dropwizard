package org.indyscala.dropwizard

import com.yammer.metrics.annotation.Timed

import javax.ws.rs.{GET,Path,Produces,QueryParam}
import javax.ws.rs.core.MediaType

import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.format.DateTimeFormat

@Path("/meeting")
@Produces(Array(MediaType.APPLICATION_JSON))
class MeetingResource(config: MeetingConfiguration) {

  val dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd")
  val days = Map(
    "SUNDAY" -> DateTimeConstants.SUNDAY,
    "MONDAY" -> DateTimeConstants.MONDAY,
    "TUESDAY" -> DateTimeConstants.TUESDAY,
    "WEDNESDAY" -> DateTimeConstants.WEDNESDAY,
    "THURSDAY" -> DateTimeConstants.THURSDAY,
    "FRIDAY" -> DateTimeConstants.FRIDAY,
    "SATURDAY" -> DateTimeConstants.SATURDAY
  )

  val weekOfMonth = config.weekOfMonth
  val dayOfWeek = days(config.dayOfWeek)

  @GET
  @Timed
  def meeting(@QueryParam("date") date: Option[String]) = {
    val d = parseDate(date)
    MeetingResponse(dateFormatter.print(d), isMeetingOn(d))
  }

  def isMeetingOn(date: DateTime) = {
    isNthOccurrenceOfDayInMonth(date, dayOfWeek, weekOfMonth)
  }

  // Mon == 1, Sun == 7 as in DateTimeConstants
  def isNthOccurrenceOfDayInMonth(date: DateTime, dayOfWeek: Int, weekOfMonth: Int) = {
    date.dayOfWeek.get == dayOfWeek && true // FIXME: check for proper week of month
  }

  def parseDate(dateStr: Option[String]) = dateStr match {
    case Some(d) => dateFormatter.parseDateTime(d)
    case None => new DateTime
  }
}
