public class Circle {

  private int radius;
  private int x;
  private int y;

  public Colors color;

  public Circle() {
    this.x = 0;
    this.y = 0;
    this.radius = 15;
    this.color = Colors.cian;
  }

  public Circle(int radius, Colors color) {
    this.x = 2;
    this.y = 2;
    this.radius = radius;
    this.color = color;
  }

  public Circle(int x, int y) {
    this.x = x;
    this.y = y;
    this.radius = 5;
    this.color = Colors.cian;
  }

  public Circle(int x, int y, int radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;
    this.color = Colors.cian;
  }

  public Circle(int x, int y, int radius, Colors color) {
    this.x = x;
    this.y = y;
    this.radius = radius;
    this.color = color;
  }

  public boolean equals(Circle circle) {
    return this.radius == circle.radius &&
        this.x == circle.x &&
        this.y == circle.y &&
        this.color == circle.color;
  }


  private float reduceDecimals(double number, int decimals){
    int offset = (int)(Math.pow(10, decimals));
    int value = (int)(number * offset);
    return (float)(value) / (float)offset;
  }

  @Override
  public String toString() {
    return "Radio:" + this.radius +
        " Perimetro:" + reduceDecimals(this.calcPerimeter(),2) + 
        " Area:" + reduceDecimals(this.calcArea(), 2) +
        " x:" + this.x + " y:" + this.y;
  }

  public double calcPerimeter() {
    return this.radius * Math.PI * 2;
  }

  public double calcArea() {
    return Math.pow(this.radius, 2) * Math.PI;
  }

  public int getRadius() {
    return this.radius;
  }

  public int getX() {
    return this.x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return this.y;
  }

  public void setY(int y) {
    this.y = y;
  }
}
