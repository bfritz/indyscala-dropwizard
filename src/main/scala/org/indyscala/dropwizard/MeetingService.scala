package org.indyscala.dropwizard

import com.yammer.dropwizard.ScalaService
import com.yammer.dropwizard.assets.AssetsBundle
import com.yammer.dropwizard.bundles.ScalaBundle
import com.yammer.dropwizard.config.{Bootstrap,Environment}

object MeetingService 
  extends ScalaService[MeetingConfiguration] {

  override def initialize(bootstrap: Bootstrap[MeetingConfiguration]) {
    bootstrap.setName("meeting")
    bootstrap.addBundle(new ScalaBundle)
    bootstrap.addBundle(new AssetsBundle)
  }

  override def run(config: MeetingConfiguration, env: Environment) {
    env.addResource(new MeetingResource(config))
    env.addHealthCheck(new FakeDbHealthCheck)
  }
}
