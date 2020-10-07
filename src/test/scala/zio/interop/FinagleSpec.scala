package zio.interop

import com.twitter.finagle.context.Contexts
import com.twitter.finagle.context.Contexts.local
import com.twitter.util.Future
import zio.{FiberRef, ZIO}
import zio.test.Assertion._
import zio.test._
import zio.interop.twitter._

object FinagleSpec extends DefaultRunnableSpec {
  val runtime = runner.runtime

  def foo =
    FiberRef.make(Contexts.local)
      .map(ref =>
      ref.get)

  private val testingLocalContextKey: local.Key[String] = new local.Key[String]
//  private val testingBroadcastContextKey: broadcast.Key[String] =
//    new Contexts.broadcast.Key[String]("testing.key") {
//      override def marshal(value: String): Buf =
//        Buf.Utf8(value)
//
//      override def tryUnmarshal(buf: Buf): twitter.util.Try[String] =
//        buf match {
//          case Buf.Utf8(value) => Return(value)
//        }
//    }

  override def spec =
    suite("FinagleSpec")(
      suite("Context")(
        testM("should work without any monkeying around") {
          ZIO(Contexts.local.let(testingLocalContextKey, "a") {
            assert(Contexts.local.get(testingLocalContextKey))(
              isSome(equalTo("a")))
          })
        },
        testM("should work when converting") {
          assertM(Contexts.local.let(testingLocalContextKey, "a") {
            zio.Task.fromTwitterFuture {
              Future {
                Contexts.local.get(testingLocalContextKey)
              }
            }
          })(isSome(equalTo("b")))
        },
      ),
    )
}
