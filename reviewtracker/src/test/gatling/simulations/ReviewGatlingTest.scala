import java.nio.charset.StandardCharsets
import java.util.Base64

import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Review entity.
 */
class ReviewGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val authorization_header = "Basic " + Base64.getEncoder.encodeToString("reviewtrackerapp:my-secret-token-to-change-in-production".getBytes(StandardCharsets.UTF_8))

    val headers_http_authentication = Map(
        "Content-Type" -> """application/x-www-form-urlencoded""",
        "Accept" -> """application/json""",
        "Authorization"-> authorization_header
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "Bearer ${access_token}"
    )

    val scn = scenario("Test the Review entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/oauth/token")
        .headers(headers_http_authentication)
        .formParam("username", "admin")
        .formParam("password", "admin")
        .formParam("grant_type", "password")
        .formParam("scope", "read write")
        .formParam("client_secret", "my-secret-token-to-change-in-production")
        .formParam("client_id", "reviewtrackerapp")
        .formParam("submit", "Login")
        .check(jsonPath("$.access_token").saveAs("access_token"))).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all reviews")
            .get("/api/reviews")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new review")
            .post("/api/reviews")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "itemID":"SAMPLE_TEXT", "reviewID":"SAMPLE_TEXT", "customerName":"SAMPLE_TEXT", "customerID":"SAMPLE_TEXT", "title":"SAMPLE_TEXT", "rating":null, "fullRating":null, "helpfulVotes":null, "totalVotes":null, "verifiedPurchase":null, "realName":"SAMPLE_TEXT", "reviewLocalDate":"2020-01-01T00:00:00.000Z", "content":"SAMPLE_TEXT", "specificNote":"SAMPLE_TEXT"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_review_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created review")
                .get("${new_review_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created review")
            .delete("${new_review_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
