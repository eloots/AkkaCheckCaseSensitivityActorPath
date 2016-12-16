package com.lightbend.training

import akka.actor.ActorSystem

object MainNameClashes {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("case-sensitivity-system")

    // Setting searchUpperCased to true will lead to the creation of a
    // BaseActor instance that will try to search for child actors with
    // the name of the child actor converted to upper-case.
    val master = system.actorOf(Master.props(searchUpperCased = true), "master")

    // The following will work as the name of the child actor is already upper-case
    master ! Master.CreateBaseAndChild("base-actor", "CHILD-ACTOR")
    // The following will lead to a failure in the identification of the
    // child actor and demonstrates that actorSelection are case-sensitive
    master ! Master.CreateBaseAndChild("base-actor", "another-child-actor")
  }
}
