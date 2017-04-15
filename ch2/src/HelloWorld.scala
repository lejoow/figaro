/**
  * Created by Joo on 15/4/2017.
  */
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._

object HelloWorld extends App{

  val sunnyToday = Flip(0.2)
  println(VariableElimination.probability(sunnyToday, true))


}
