# Dropwizard with Scala

---

# What is Dropwizard?

Coda Hale probably describes it best:

> Dropwizard is a sneaky way of making fast Java or Scala web services.
>
> It's a little bit of opinionated glue code which bangs together a set
> of libraries which have historically not sucked:
>
> * Jetty for HTTP servin'.
> * Jersey for REST modelin'.
> * Jackson for JSON parsin' and generatin'.
> * Logback for loggin'.
> * Hibernate Validator for validatin'.
> * Metrics for figurin' out what your service is doing in production.
> * SnakeYAML for YAML parsin' and configuratin'.
>
> Yammer's high-performance, low-latency, Java and Scala services all
> use Dropwizard. In fact, Dropwizard is really just a simple extraction
> of Yammer's glue code.

---

# Alternatives?

You betcha...

* [Scalatra](http://scalatra.org)
* [Unfiltered](http://unfiltered.databinder.net/)
* [Spray](http://spray.io/)
* [Akka](http://akka.io/) without Spray [1,2]
* [Finagle](https://github.com/twitter/finagle) [3]
* [Pinky](https://github.com/pk11/pinky) (no longer maintained)
* ...and more.[4]

[1] <http://doc.akka.io/docs/akka/1.3.1/scala/http.html><br/>
[2] <http://github.com/mattbowen/akka-web-template><br/>
[3] <https://groups.google.com/d/topic/finaglers/BamVfTlnM9Q/discussion></br>
[4] <http://stackoverflow.com/questions/3678795/scala-framework-for-a-rest-api-server><br/>

---

# Why use Dropwizard with Scala?

You might not want to.  There are a lot of Scala stacks
for backing RESTful web services.

But Dropwizard might be a good fit if you have a **mix
of Java and Scala** services or...

---

# Why use Dropwizard with Scala? (cont.)

...if Dropwizard fits your requirements particularly
well.  E.g. if you:

* are serving mostly **JSON**<br/>
  (nothing precludes other formats and **Freemarker template** support is built-in)
* like the **JAX-RS resource resolution** approach<br/>
  (@Consumes, @Produces, @Path)
* appreciate the ease of adding **timing data** and **health checks**
   * with **built-in instrumentation** for Jetty, Jersey, Logback,
     JDBI and HttpClient
   * and the ability to easily **integrate with Graphite or Ganglia**
     or one of the zillon JSON-capable dashboards
* like the **fat-jar**-per-service approach

---

# What does a basic service look like?

* Configuration
* [Scala]Service
* Resource
* representation classes (optional)
* Health Check (optional)
* static assets (optional)

---

# Configuration - class

    class MeetingConfiguration extends Configuration {
      @JsonProperty
      @NotEmpty @Pattern(regexp="[A-Z]+")
      var dayOfWeek: String = "MONDAY"

      @JsonProperty
      @Range(min=1, max=5)
      var weekOfMonth: Int = 1
    }

---

# Configuration - YAML

    dayOfWeek: MONDAY
    weekOfMonth: 2

---

# Service

    object MeetingService extends ScalaService[MeetingConfiguration] {
      override def initialize(bootstrap: Bootstrap[MeetingConfiguration]) {
        bootstrap.setName("meeting")
        bootstrap.addBundle(new ScalaBundle)
        bootstrap.addBundle(new AssetsBundle)
      }

      override def run(config: MeetingConfig, env: Environment) {
        env.addResource(new MeetingResource(config))
        env.addHealthCheck(new FakeDbHealthCheck)
      }
    }

---

# Resource

    @Path("/meeting")
    @Produces(Array(MediaType.APPLICATION_JSON))
    class MeetingResource(config: MeetingConfiguration) {
      val ymdFormat = DateTimeFormat.forPattern("yyyy-MM-dd")

      @GET
      @Timed
      def meeting(@QueryParam("date") date: Option[String]) = {
        val d = parseDate(date)
        MeetingResponse(ymdFormat.print(d), isMeetingOn(d))
      }

      def isMeetingOn(date: Date) = {
        // fancy date logic that returns Boolean goes here
      }
    }

---

# representation classes

Can use case classes:

    case class MeetingResponse(date: String, meeting: Boolean)

or more complex structure:

* Jackson annotations
* SoapMessage with appropriate JAX-RS provider

---

# Health Check

    class DbAvailableHealthCheck extends HealthCheck {

      override def check: Result = {
        try {
          // ping DB
        } finally {
          // cleanup
        }
        Result.healthy
      }
    }

---

# Instrumentation:

$ curl <http://localhost:8081/metrics?pretty=true>

    "jvm" : {
      "vm" : {
        "name" : "OpenJDK 64-Bit Server VM",
        "version" : "1.6.0_24-b24"
      },
      "memory" : {
        "heapMax" : 1.847394304E9,
    [..]
        "memory_pool_usages" : {
          "Code Cache" : 0.0167388916015625,
          "PS Eden Space" : 0.04516772831629589,
    [..]
        }
      },
      "daemon_thread_count" : 7,
      "thread_count" : 24,
      "current_time" : 1354573221576,
      "uptime" : 897,
      "fd_usage" : 0.00341796875,

    [..]

---

# What else?


* instrumented [JDBI](http://jdbi.org/) and Hibernate support
* database migrations via [Liquibase](http://liquibase.org/)
* [pluggable authentication](http://dropwizard.codahale.com/manual/auth/)
  with HTTP Basic and OAuth2 support
* serve static assets from fat jar
* logging wrapper around [Logback](http://logback.qos.ch/) with
  routing of Log4j and commons-logging through Logback
* and more: <http://dropwizard.codahale.com/manual/>

---

# Resources

* Dropwizard: <http://dropwizard.codahale.com/>
* dropwizard-scala: <http://dropwizard.codahale.com/manual/scala/>
* Courtney Robinson's Templates: <https://github.com/zcourts/scala-dropwizard-template>
* Archetypes: <https://github.com/nicktelford/dropwizard/tree/feature/maven-archetypes>

---

# Me

Brad Fritz

*brad@fewerhassles.com*

*bfritz* on **github**, **irc** (freenode) and **twitter**
