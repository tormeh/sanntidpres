import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.pattern.ask
import scala.util.{Failure, Success}

import scala.language.postfixOps
import scala.sys
import java.lang.Thread
import scala.util.Random
import akka.util.Timeout
import scala.collection.mutable.ArrayBuffer

class LockingActor(printstr:String) extends Actor {
  var othermessagesanswered:Int = 0
  def receive = 
  {
    case other: ActorRef =>
    {
      val response = other.ask("please reply")(50000)
      response onComplete 
      { 
        case Success(result) => println("success: " + result)
        case Failure(failure) => println(failure)
      }
      othermessagesanswered += 1
      println(printstr + ": " + othermessagesanswered.toString())
    }
    case "please reply" =>
    {
      sender() ! "this is a reply"
    }
    case s: String =>
    {
      println("got string: " + s)
    }
  }
}


object Main extends App 
{
    def deadlockattempt(): Unit =
    {
      val system = ActorSystem("DemoSystem")
      val aActor = system.actorOf(Props(classOf[LockingActor], "a"))
      val bActor = system.actorOf(Props(classOf[LockingActor], "b"))
      for (x <- 0 to 10)
      {
        println(x)
        Future{ aActor ! bActor }
        Future{ bActor ! aActor }
        //Thread.sleep(1)
      }
      Thread.sleep(1000) 
      scala.sys.exit()
    }

  deadlockattempt()
}