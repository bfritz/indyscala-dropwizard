package org.indyscala.dropwizard

import com.yammer.dropwizard.ScalaService
import com.yammer.dropwizard.config.Environment

object MeetingService 
  extends ScalaService[MeetingConfiguration]("Meeting") {

  def initialize(conf: MeetingConfiguration, env: Environment) {
    // TODO: implement service
  }

}

