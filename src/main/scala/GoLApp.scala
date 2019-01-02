import scalafx.animation._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.scene.layout.{AnchorPane, GridPane, StackPane}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle

object GoLApp extends JFXApp {

  val spaces = 64
  val cellsize = 12
  val size: Int = spaces * cellsize
  var game = new GoL
  game.setGrid(spaces, spaces, game.grid)

  stage = new PrimaryStage {

    title = "Game of Life"
    height = size
    width = size

    scene = new Scene {

      content = new AnchorPane {
        var started = false
        val time = AnimationTimer { t =>
          children = List(
            new GridPane {
              for ((k, v) <- game.grid) {
                if (v == "ALIVE") {
                  val cell = Rect(k._1, k._2, cellsize, v)
                  add(cell, k._1, k._2)
                } else if (v == "DEAD") {
                  val cell = Rect(k._1, k._2, cellsize, v)
                  add(cell, k._1, k._2)
                }
              }
            })
          game.tick()
          Thread.sleep(500)
          started = true
        }
        time.start()
      }
    }

  }
}


case class Rect(xp: Int, yp: Int, size: Int, state: String) extends Rectangle {

  val CellFill: Color = if (state == "ALIVE") Blue else Black
  width = size
  height = size
  fill = CellFill
  x = xp
  y = yp
}


