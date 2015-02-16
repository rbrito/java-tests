package cc.cynic.jvmtests.threading

import java.util.concurrent.{ ForkJoinPool, RecursiveTask }

class ScalaSumArray(val lo: Int, val hi: Int, val arr: Array[Int]) extends RecursiveTask[Int] {
  val SEQUENTIAL_THRESHOLD = 1

  def mysum(lo: Int, hi: Int) = {
    val r = lo until hi
    (for (i <- r) yield arr(i)).sum
  }

  def compute: Int = {
      if (hi - lo <= SEQUENTIAL_THRESHOLD)
        mysum(lo, hi)
      else {
        val left = new ScalaSumArray(lo, (lo + hi) / 2, arr)
        val right = new ScalaSumArray((lo + hi) / 2, hi, arr)

        left.fork()
        val rightans = right.compute()
        val leftans = left.join()
        leftans + rightans
      }
  }
}

object ScalaSumArray {
  def main(args: Array[String]) {
    val fjpool: ForkJoinPool = new ForkJoinPool
    val lo = 0
    val hi = 10001
    val r = lo until hi
    val arr: Array[Int] = r.toArray

    // Without parallelism
    val foo = new ScalaSumArray(lo, hi, arr)
    println(foo.mysum(lo, hi))

    // With parallelism
    val s = new ScalaSumArray(lo, hi, arr)
    println(fjpool.invoke(s))
  }
}
