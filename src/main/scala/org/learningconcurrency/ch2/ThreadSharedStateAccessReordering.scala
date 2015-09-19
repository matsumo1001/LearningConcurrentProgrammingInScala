package org.learningconcurrency.ch2
import org.learningconcurrency._

/**
 * @author yoshifumi.matsumoto
 */
object ThreadSharedStateAccessReordering extends App {
  for (i <- 0 until 10000) {
    var a = false
    var b = false
    var x = -1
    var y = -1
    var t1 = thread {
      a = true
      y = if (b) 0 else 1
    }
    var t2 = thread {
      b = true
      x = if (a) 0 else 1
    }
    t1.join()
    t2.join()
    assert(!(x==1 && y==1), s"x=$x, y=$y")
  }
}