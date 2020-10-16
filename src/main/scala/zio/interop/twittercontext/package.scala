package zio.interop

import zio.Has

package object twittercontext {
  type TwitterContext = Has[TwitterContext.Service]
}
