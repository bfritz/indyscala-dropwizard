package org.indyscala.dropwizard

import com.yammer.dropwizard.ScalaService
import com.yammer.dropwizard.bundles.ScalaBundle
import com.yammer.dropwizard.config.{Bootstrap,Environment}

object MeetingService 
  extends ScalaService[MeetingConfiguration] {

  override def initialize(bootstrap: Bootstrap[MeetingConfiguration]) {
    bootstrap.setName("meeting")
    bootstrap.addBundle(new ScalaBundle)
  }

  override def run(config: MeetingConfiguration, env: Environment) {
    // TODO: implement service
  }
}

