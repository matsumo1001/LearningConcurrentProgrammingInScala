package org.learningconcurrency.ch2.ex
import org.learningconcurrency._

/**
 * @author yoshifumi.matsumoto
 */
object Ex1 extends App {
  def parallel[A, B](a: => A, b: => B): (A, B) = {
    var aVar: A = null.asInstanceOf[A]
    var bVar: B = null.asInstanceOf[B]
    val t1 = thread {
      aVar = a
    }
    val t2 = thread {
      bVar = b
    }
    t1.join()
    t2.join()
    (aVar, bVar)
  }
  print(parallel({"AAA"}, {1+1}))
}