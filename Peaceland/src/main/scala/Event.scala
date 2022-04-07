import faker.{Address, Lorem}
import scala.util.Random

case class Event(droneId: Int, peacescore: Int, citizenid: String,lat: Double, lon: Double, people: List[Citizen], words: List[String], date: Long)

object Reports{
  def create() = {

    val r = new Random();
    val c = Citizen.createRandomCitizen()
    val id = r.nextInt(100)
    val latitude = (-90) + (90 - (-90)) * r.nextDouble();
    val longitude = (-180) + (180 - (-180)) * r.nextDouble();
    val peacescore = c.peacesc
    val citizenid = c.citizenid
    val nbCitizen = 2 + r.nextInt(10)
    val citizens = List.fill(nbCitizen)(Citizen.createRandomCitizen())
    val nbWord = 2 + r.nextInt(10)
    val wordsHeards = Lorem.words(nbWord)
    val strDay = 10000 + r.nextLong()

    Event(id, peacescore, citizenid, latitude, longitude, citizens, wordsHeards, strDay)

  }
  def report_toString(r: Event) : String = {
    val citizens = r.people.map(citizen => citizen.id + ":" + citizen.peacescore).mkString(",")
    val words = r.words.mkString(",")
    val stringReport = r.droneId + ";" + r.peacescore + ";" + r.citizenid + ";" + r.lat + ";" + r.lon + ";" + citizens + ";" + words + ";" + r.date
    stringReport

  }
}