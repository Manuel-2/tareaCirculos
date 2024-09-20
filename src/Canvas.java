import java.util.Scanner;
import java.util.Map;


public class Canvas {

  // codigos de escape Ansi para colores estandar en terminales unix
  // son estos: https://en.wikipedia.org/wiki/ANSI_escape_code#8-bit
  public Map<Colors, String> colors2Codes = Map.of(
      Colors.red, "\033[31m",
      Colors.green, "\033[32m",
      Colors.yellow, "\033[33m",
      Colors.blue, "\033[34m",
      Colors.purple, "\033[35m",
      Colors.cian, "\033[36m",
      Colors.white, "\033[37m");

  // el primer indice correcponde la fila
  // el segundo indice corresponde al caracter en esa fila
  // la cordena 0,0 es arriba a la izquierda
  private char[][] display;
  private int columns;
  private int rows;

  private float verticalScaleFactor = 2f;

  public Canvas(int rows, int columns) {
    this.columns = columns;
    this.rows = (int) (rows / this.verticalScaleFactor);
    this.display = new char[rows][columns];
    this.startCleanDisplay();
  }

  public void drawCircle(Circle circle, boolean deletePreviusDrawings) {
    if (deletePreviusDrawings) {
      this.startCleanDisplay();
    }

    int radius = circle.getRadius();

    int circleY = (int) (circle.getY() / verticalScaleFactor);
    int circleX = circle.getX();

    if (positionIsInsideDisplay(circleY, circleX)) {
      display[circleY][circleX] = '+';
    }

    // el usar flotantes incrementa la resolucion en los calculos
    for (float r = -circle.getRadius(); r <= circle.getRadius(); r += 0.1) {
      int cirX = (int) (Math.sin(r) * radius) + circleX;
      // dado que los caracteres son mas altos que anchos se divide por 1.8
      int cirY = (int) (Math.cos(r) * radius / this.verticalScaleFactor) + circleY;

      if (positionIsInsideDisplay(cirY, cirX)) {
        this.display[cirY][cirX] = 'O';
      }
    }
    this.render(circle);
  }

  private boolean positionIsInsideDisplay(int y, int x) {
    return x >= 0 && x < this.columns && y >= 0 && y < this.rows;
  }

  private void render(Circle circle) { 
    System.out.println(colors2Codes.get(Colors.white) + "-".repeat(this.columns + 2));
    for (int r = 0; r < rows; r++) {
      String line = colors2Codes.get(Colors.white) + "|" + colors2Codes.get(circle.color);
      for (int c = 0; c < columns; c++) {
        // if(display[r][c] == 'O' || display[r][c] == '+') continue;
        // esto detecta que esta a la derecha del centro y dentro del circulo 
        if(r == (int)(circle.getY()/this.verticalScaleFactor) && c > circle.getX() && c < circle.getX() + circle.getRadius() - 1){
          line+= "-";
          continue;
        }
        // aca se calcula que se este 1 por debajo(es una suma por que 0,0 es arriba a la izquierda) del centro del circulo y en medio del centro y el perimetro a la derecha
        // basicamente se coloca debajo en el centro de la linea que se crea en if de arriba 
        // esto escribe el texto r=radio dentro del circulo
        else if(r == (int)(circle.getY()/this.verticalScaleFactor)+1 && c == circle.getX()+ (int)(circle.getRadius()/2)-2){
          int radiusNumberOfDigits = (int)Math.log10(circle.getRadius())+ 1;
          int spaces = radiusNumberOfDigits + 2;
          line+= ("r=" + circle.getRadius()) + " "; 
          c+= spaces;
          continue;
        }
        // 
        else if(c == circle.getX() - 2 && r == (int)(circle.getY()/this.verticalScaleFactor)-1){
          int circleXNumberOfDigits = (int)Math.log10(circle.getX())+ 1;
          int circleYNumberOfDigits = (int)Math.log10(circle.getY())+ 1;
          line += 
            (circleXNumberOfDigits==1?" "+circle.getX():circle.getX())
            + "," + 
            (circleYNumberOfDigits==1?" "+circle.getY():circle.getY()) + " ";
          c += 5;
          continue;
        }
        line += this.display[r][c];
      }
      System.out.println(line +colors2Codes.get(Colors.white) + "|");
    }
    System.out.println( "-".repeat(this.columns + 2) + colors2Codes.get(Colors.white) );
  }

  private void startCleanDisplay() {
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < columns; c++) {
        this.display[r][c] = ' ';
      }
    }
  }

  public int getWidth(){
    return this.columns;
  }

  public int getHeight(){
    return this.rows;
  }


}
