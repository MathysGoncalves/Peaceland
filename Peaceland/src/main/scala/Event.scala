import java.util.Date

case class Event(
                  val citizen: Citizen,
                  val latitude: Double,
                  val longitude: Double,
                  val wordHeard: String,
                  val people: Array[Citizen],
                  val date: Long,
                  val battery: Int,
                  val temperature: Int,
                  val droneId:String,
                )