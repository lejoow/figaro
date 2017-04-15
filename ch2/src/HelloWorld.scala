/**
  * Created by Joo on 15/4/2017.
  */
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.library.atomic.discrete.Binomial
import com.cra.figaro.library.compound.If

object HelloWorld extends App{

  val sunnyToday: AtomicFlip = Flip(0.2)
  println(VariableElimination.probability(sunnyToday, true))


  val greetingToday: If[String] = If(sunnyToday,
    Select(0.6 -> "Hello World!", 0.4 -> "Howdy, universe!"),
    Select(0.2 -> "Hello World!", 0.8 -> "Oh no, not again!")
  )


  greetingToday.observe("Hello World!")

  println(VariableElimination.probability(sunnyToday, true))

  greetingToday.unobserve()

  println(VariableElimination.probability(sunnyToday,true))

  val sunnyTomorrow = If(sunnyToday, Flip(0.8), Flip(0.05))
  val greetingTomorrow: If[String] = If(sunnyTomorrow,
    Select(0.6 -> "Hello World!", 0.4 -> "Howdy, universe!"),
    Select(0.2 -> "Hello World!", 0.8 -> "Oh no, not again!")
  )


  println(VariableElimination.probability(greetingTomorrow, "Hello World!"))
  greetingToday.observe("Hello World!")
  println(VariableElimination.probability(greetingTomorrow, "Hello World!"))


  val numSunnyDaysInWeek = Binomial(7,0.2)
  println(VariableElimination.probability(numSunnyDaysInWeek, 3))
}
