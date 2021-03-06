import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.pattern.ask
import scala.util.{Failure, Success}

class LockingActor(identitystr:String) extends Actor {
  var repliesnumber:Int = 0
  var actorrefmessagenum:Int = 0
  def receive = 
  {
    case other: ActorRef =>
    {
      val response = other.ask("please reply")(50000)
      response onComplete 
      { 
        case Success(result) => 
        {
          println(identitystr + ": Got answer that says: \"" + result + "\"")
        }
        case Failure(failure) => println(failure)
      }
      println(identitystr + " finished treating actorrefmessage number " + actorrefmessagenum.toString())
      actorrefmessagenum += 1
    }
    case "please reply" =>
    {
      sender() ! ("this is a reply from: " + identitystr + ". It is reply number " + repliesnumber.toString())
      repliesnumber += 1
    }
  }
}


object Main extends App 
{

  val system = ActorSystem("DemoSystem")
  val aActor = system.actorOf(Props(classOf[LockingActor], "a"))
  val bActor = system.actorOf(Props(classOf[LockingActor], "b"))
  for (x <- 0 to 7)
  {
    println("main thread sending references to both actors, round " + x.toString())
    Future{ aActor ! bActor }
    Future{ bActor ! aActor }
    //Thread.sleep(1)
  }
  Thread.sleep(5000) 
  scala.sys.exit()

}
