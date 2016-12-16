package com.lightbend.training

import akka.actor.{Actor, ActorLogging, Props}

object ChildActor {

  def props: Props = Props(new ChildActor)
}

class ChildActor extends Actor with ActorLogging {

  log.info(s"New childActor: ${self.path}")

  override def receive: Receive = Actor.emptyBehavior
}
