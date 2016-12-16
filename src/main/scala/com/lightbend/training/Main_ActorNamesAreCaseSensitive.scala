package com.lightbend.training

import akka.actor.ActorSystem

object Main_ActorNamesAreCaseSensitive {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("case-sensitivity-system-tests")

    val master = system.actorOf(Master.props(), "master")

    // Following doesn't lead to a name clash on the child actors
    // because names are case sensitive.
    master ! Master.CreateBaseAndChild("base-actor", "child-actor")
    master ! Master.CreateBaseAndChild("base-actor", "Child-actor")
  }
}
