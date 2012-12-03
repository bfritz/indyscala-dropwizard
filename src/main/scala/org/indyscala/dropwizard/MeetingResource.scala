package org.indyscala.dropwizard

import com.yammer.metrics.annotation.Timed

import java.util.Date

import javax.ws.rs.{GET,Path,Produces,QueryParam}
import javax.ws.rs.core.MediaType

@Path("/meeting")
@Produces(Array(MediaType.APPLICATION_JSON))
class MeetingResource(config: MeetingConfiguration) {
  @GET
  @Timed
  def meeting(@QueryParam("date") date: Option[String]) = {
    isMeetingOn(parseDate(date))
  }

  def isMeetingOn(date: Date) = {
    // FIXME: implement
    Seq(false)
  }

  def parseDate(dateStr: Option[String]) = dateStr match {
    case Some(d) => {
      // FIXME: parse date string
      new Date
    }
    case None => new Date
  }
}

// vim: set ts=2 sw=2 et:
