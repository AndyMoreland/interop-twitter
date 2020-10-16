package zio.interop.twittercontext

import com.twitter.util.Local
import com.twitter.util.Local.Context
import zio.{FiberRef, ZLayer}

object TwitterContext {
  trait Service {
    def context: FiberRef[Context]
  }

  def makeRootContext: ZLayer[Any, Throwable, TwitterContext] = {
    ZLayer.fromEffect(
      for {
        ref <- FiberRef.make[Context](Local.save())
      } yield
        new TwitterContext.Service { self =>
          override def context: FiberRef[Context] = ref
        }
    )
  }
}
