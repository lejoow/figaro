/**
  * Created by Joo on 15/4/2017.
  */

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language._
import com.cra.figaro.library.atomic.continuous.{Normal, Uniform}
import com.cra.figaro.library.atomic.discrete.Binomial
import com.cra.figaro.library.compound.If

object HelloWorld extends App {

  val sunnyToday: AtomicFlip = Flip(0.2)
  println(VariableElimination.probability(sunnyToday, true))


  val greetingToday: If[String] = If(sunnyToday,
    Select(0.6 -> "Hello World!", 0.4 -> "Howdy, universe!"),
    Select(0.2 -> "Hello World!", 0.8 -> "Oh no, not again!")
  )


  greetingToday.observe("Hello World!")

  println(VariableElimination.probability(sunnyToday, true))

  greetingToday.unobserve()

  println(VariableElimination.probability(sunnyToday, true))

  val sunnyTomorrow = If(sunnyToday, Flip(0.8), Flip(0.05))
  val greetingTomorrow: If[String] = If(sunnyTomorrow,
    Select(0.6 -> "Hello World!", 0.4 -> "Howdy, universe!"),
    Select(0.2 -> "Hello World!", 0.8 -> "Oh no, not again!")
  )


  println(VariableElimination.probability(greetingTomorrow, "Hello World!"))
  greetingToday.observe("Hello World!")
  println(VariableElimination.probability(greetingTomorrow, "Hello World!"))


  val numSunnyDaysInWeek = Binomial(7, 0.2)
  println(VariableElimination.probability(numSunnyDaysInWeek, 3))

  val temperature = Normal(40, 100)

  def greaterThan50(d: Double) = d > 50

  println(Importance.probability(temperature, greaterThan50 _))

  val uniformTemp = Uniform(10, 70)
  println(Importance.probability(uniformTemp, greaterThan50 _))

  val goodMood = Dist(0.2 -> Flip(0.6), 0.8 -> Flip(0.2))

  println(VariableElimination.probability(goodMood, true))

  val sunnyTodayProbability = Uniform(0, 0.05)
  val sunnyTodayWitUniform = Flip(sunnyTodayProbability)
  println(Importance.probability(sunnyTodayWitUniform, true))

  val tempMean = Normal(40, 9)
  val tempVariance = Select(0.5 -> 80.0, 0.5 -> 105.0)
  println(Importance.probability(temperature, (d: Double) => d > 50))


  val teamWinsInMonth = Binomial(5, 0.4)
  val sunnyDaysInMonth = Binomial(30, 0.2)
  val monthQuality = Apply(sunnyDaysInMonth, teamWinsInMonth,
    (days: Int, wins: Int) => {
      val x = days * wins
      if (x > 20) "good" else if (x > 10) "average" else "poor"
    })

  println(VariableElimination.probability(monthQuality, "good"))

  val goodMoodChained = Chain(monthQuality, (s:String) =>
    if( s== "good") Flip(0.9)
    else if ( s== "average") Flip(0.6)
    else Flip(0.1)
  )
}
