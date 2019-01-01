import scala.collection.mutable
import scala.util.Random

class GoL {

  val xMax = 63
  val yMax = 63
  var grid: mutable.HashMap[(Int, Int), String] = mutable.HashMap.empty[(Int, Int), String]

/** def main(args: Array[String]): Unit = {
    setGrid(xMax, yMax, grid)
    while (true) {
      tick()
      print(Con())
      Thread.sleep(1000)
    }
  }
**/

  def setGrid(x: Int, y: Int, g: mutable.HashMap[(Int, Int), String]) {
    for (i <- 0 to x) {
      for (h <- 0 to y) {
        val k = new Point(i, h)
        val r = new Random()
        if (r.nextDouble() > 0.85) {
          val v = "ALIVE"
          setState(k.point, v, g)
        } else {
          val v = "DEAD"
          setState(k.point, v, g)
        }
      }
    }
  }

  def tick(): Unit = {


    val neighbours = new Array[String](8)

    var newGrid: mutable.HashMap[(Int, Int), String] = mutable.HashMap.empty[(Int, Int), String]

      for (x <- 0 to xMax) {
        for (y <- 0 to yMax) {
      val p = new Point(x, y)
      neighbours(0) = getState(new Point(x - 1, y).point, grid)
      neighbours(1) = getState(new Point(x + 1, y).point, grid)
      neighbours(2) = getState(new Point(x - 1, y + 1).point, grid)
      neighbours(3) = getState(new Point(x, y + 1).point, grid)
      neighbours(4) = getState(new Point(x + 1, y + 1).point, grid)
      neighbours(5) = getState(new Point(x - 1, y - 1).point, grid)
      neighbours(6) = getState(new Point(x, y - 1).point, grid)
      neighbours(7) = getState(new Point(x + 1, y - 1).point, grid)
      if (getState(p.point, grid) == "ALIVE") {
        if (neighbours.count(_ == "ALIVE") < 2) {
          setState(p.point, "DEAD", newGrid)
        } else if (neighbours.count(_ == "ALIVE") == 2 || neighbours.count(_ == "ALIVE") == 3) {
          setState(p.point, "ALIVE", newGrid)
        } else if (neighbours.count(_ == "ALIVE") > 3) {
          setState(p.point, "DEAD", newGrid)
        }
      } else {
        if (neighbours.count(_ == "ALIVE") == 3) {
          setState(p.point, "ALIVE", newGrid)
        }
      }
    }
  }
    grid.clear()
    grid = newGrid
  }

  def setState(k: (Int,Int), v: String, g: mutable.HashMap[(Int, Int), String]) {
    if (g.contains(k)) {
      g.remove(k)
      g += k -> v
    } else {
      g += k -> v
    }
  }

  def getState(k: (Int,Int), g: mutable.HashMap[(Int, Int), String]): String = {
    try {
      g(k)
    } catch {
      case _: NoSuchElementException => "DEAD"
    }
  }

  def Con(): String = {
    val line = new StringBuilder()
    for (x <- 0 to xMax) {
      for (y <- 0 to yMax) {
        val p = new Point(x, y)
        if (getState(p.point, grid) == "ALIVE")  line.append('X') else line.append(' ')
      }
      line.append('\n')
    }
    line.toString()
  }

}

class Point(x: Int, y: Int) {
  var point: (Int, Int) = (x,y)
}
