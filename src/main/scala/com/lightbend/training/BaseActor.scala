package com.lightbend.training

import akka.actor.{Actor, ActorIdentity, ActorLogging, ActorRef, ActorSelection, Identify, Props}

import scala.concurrent.duration._

object BaseActor {
  case class CreateChild(name: String)

  def props(searchUpperCased: Boolean = false): Props = Props(new BaseActor(searchUpperCased))
}

class BaseActor(searchUpperCased: Boolean) extends Actor with ActorLogging {
  import BaseActor._

  override def receive: Receive = {
    case CreateChild(name) =>
      createchild(name)
      val searchedName = if (searchUpperCased) name.toUpperCase else name
      val as = context.actorSelection(s"$searchedName")
      as ! Identify(1)
      log.info(s"Sent an identification request for ${as.anchorPath}${as.pathString}")

    case ActorIdentity(1, Some(actorRef)) =>
      log.info(s"Identified: ${actorRef.path} with ActorRef($actorRef)")

    case ActorIdentity(1, None) =>
      log.error(s"Actor Identification failed")
  }

  private def createchild(name: String): ActorRef = {
    context.actorOf(ChildActor.props, name)
  }
}
