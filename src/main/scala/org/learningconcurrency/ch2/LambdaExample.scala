package org.learningconcurrency.ch2
import org.learningconcurrency._

/**
 * @author yoshifumi.matsumoto
 */
object LambdaExample extends App {
  var inc: () => Unit = null
  val t = thread {
    if (inc != null) inc()
  }
  private var number = 1
  inc = () => {number += 1}
}