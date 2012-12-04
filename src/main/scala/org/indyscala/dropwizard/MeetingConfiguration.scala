package org.indyscala.dropwizard

import com.yammer.dropwizard.config.Configuration
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints._
import org.hibernate.validator.constraints._

class MeetingConfiguration extends Configuration {

  @JsonProperty
  @NotEmpty
  @Pattern(regexp="[A-Z]+")
  var dayOfWeek = "MONDAY"

  @JsonProperty
  @Range(min=1, max=5)
  var weekOfMonth = 1
}
