
case class Citizen(citizenid: String, peacesc: Int){
  val id = citizenid
  val peacescore = peacesc

}
object Citizen {
  def createRandomCitizen() = {

    val r = scala.util.Random
    val id = 0 + r.nextInt(10000).toString()
    val peacescore = 15 + r.nextInt((99 - 15) + 1)

    Citizen(id, peacescore)

  }
}