package org.indyscala.dropwizard

import com.yammer.metrics.core.HealthCheck;
import com.yammer.metrics.core.HealthCheck.Result;

class FakeDbHealthCheck extends HealthCheck("fake-db") {
  override def check: Result = {
    // Result.unhealthy("FUBAR")
    Result.healthy
  }
}
