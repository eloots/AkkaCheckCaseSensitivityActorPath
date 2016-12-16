package com.lightbend.training

import akka.actor.{Actor, ActorLogging, Props}
import scala.concurrent.duration._

object Master {
  case class CreateBaseAndChild(baseName: String, childName: String)
  case object ExitAfterDelay
  def props(searchUpperCased: Boolean = false): Props = Props(new Master(searchUpperCased))
}

class Master(searchUpperCased: Boolean) extends Actor with ActorLogging {
  import Master._

  import context.dispatcher
  context.system.scheduler.scheduleOnce(1.second, self, ExitAfterDelay)

  override def receive: Receive = {
    case CreateBaseAndChild(baseName, childName) if context.child(baseName).isEmpty =>
      context.actorOf(BaseActor.props(searchUpperCased), baseName) ! BaseActor.CreateChild(childName)

    case CreateBaseAndChild(baseName, childName) =>
      context.child(baseName).get ! BaseActor.CreateChild(childName)

    case ExitAfterDelay =>
      shutdown()
  }

  private def shutdown(): Unit = {
    import context.dispatcher
    context.system.terminate().onComplete(_ => println(s"We're done for today!"))
  }
}
