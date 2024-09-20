import java.util.Scanner;
import java.util.Map;

class Main {
  static Scanner input = new Scanner(System.in);
  static Canvas terminalCanvas = new Canvas(52, 52);
  static int uiWidth = terminalCanvas.getWidth() - 2;
  static Circle currentCircle = new Circle(25, 25, 20, Colors.blue);

  static void print(String content) {
    System.out.print(content);
  }

  static void separator() {
    System.out.println("-".repeat(uiWidth +4));
  }

  static void manualCircleMove(){
    System.out.printf("| %-" + uiWidth +"s |\n", "Ingresa (w a s d) para mover el circulo, (0) para regresar al menu"); 
    String inputKey = (String)(input.nextLine()).toLowerCase(); 

    int x = currentCircle.getX();
    int y = currentCircle.getY();
    switch (inputKey) {
      case "w": 
      // 0,0 es arriba a la izquierda por eso reducir "y" se ve como subir
      // dado el factor de escala en "y" por culpa de los caracters se resta 2
        y -= 2;
        break;
      case "a":
        x--;
       break;
      case "s":
        y += 2;
        break;
      case "d":
        x++;
        break;
      case "0":
        return; 
      default:
        System.out.println("ingrese una opcion valida >:|");
        break;
    }

    currentCircle.setX(x);
    currentCircle.setY(y);
    terminalCanvas.drawCircle(currentCircle,true);
    manualCircleMove();
  }


  static void moveCircle(){
    System.out.printf("| %-"+uiWidth+"s |\n", "Ingrese la nueva posicion del circulo como 2 numeros separados con un espacio (x y)"); 
    int x = 0;
    int y = 0;
    try {
      x = input.nextInt();
      y = input.nextInt();
    } catch (Exception e) {
      System.out.println("ingrese una opcion valida >:|");
      input.nextLine();
      moveCircle();
      return;
    }
    currentCircle.setX(x);
    currentCircle.setY(y);
  }

  static Circle createNewCircle(boolean temporal){
    System.out.println("Ingrese el radio: ");
    int radius = 15;
    try {
      radius = input.nextInt(); 
    } catch (Exception e) {
      System.out.println("Ingrese un valor Valido >:(");
      return createNewCircle(temporal);
    }

    System.out.println("Selecione un color:");
    int i = 0;
    Colors[] colorsArray = new Colors[terminalCanvas.colors2Codes.size()];
    for(Colors key : terminalCanvas.colors2Codes.keySet()){
      System.out.println("(" + i + ")" + key);
      colorsArray[i] = key;
      i++;
    }
    int colorOption = input.nextInt();
    System.out.println(terminalCanvas.getHeight());
    
    Circle createdCircle = new Circle((int)(terminalCanvas.getWidth()/2 -1),(int)(terminalCanvas.getHeight()-1),radius,colorsArray[colorOption]);
    // se que es raro tener negacion primero pero java no me deja en paz y apesar que un boolean solo puede tener 2 valores y por ende
    // el else siempre se ejecuta, me obliga a tener un return fuera de un bloque a pesar que la condicial regresa algo el 100% de las veces
    if(!temporal){
      currentCircle = createdCircle;
    }
    return createdCircle;
  }

  static void createNewCanvas(){
    System.out.println("Ingrese las dimenciones del canvas(valores entre 30 y 120 aprox, segun tu terminal, evita saltos de linea)");
    System.out.println("Escribe el ancho y el alto separados con espacios (x y)");

    int width = 50;
    int height = 50;
    try {
      width = input.nextInt();
      height = input.nextInt(); 
    } catch (Exception e) {
      input.nextLine();
      System.out.println("Ingrse un valor valido >:|");
      createNewCanvas();
      return;
    }
    uiWidth = width -2;
    terminalCanvas = new Canvas(height,width);
  }
 
  public static void main(String[] args) {
 
    String title = "\n█▀▀ █ █▀█ █▀▀ █░█ █░░ █▀█ █▀\n█▄▄ █ █▀▄ █▄▄ █▄█ █▄▄ █▄█ ▄█\n";
    print(title);
    int menuOption = 0;

    createNewCircle(false);

    do {
      separator();
      if(currentCircle != null){
        System.out.printf("| %-"+uiWidth+"s |\n", "Circulo  " + currentCircle.color);
        System.out.printf("| %-"+uiWidth+"s |\n", currentCircle.toString());
        terminalCanvas.drawCircle(currentCircle, true);
      }
      
      System.out.printf("| %-"+uiWidth+"s |\n", "Seleciona una opcion");
      separator();
      System.out.printf("| %-"+uiWidth+"s |\n", "(1) Mover circulo (manual)");
      System.out.printf("| %-"+uiWidth+"s |\n", "(2) Mover circulo (con cordenadas)");
      System.out.printf("| %-"+uiWidth+"s |\n", "(3) Comparar");
      System.out.printf("| %-"+uiWidth+"s |\n", "(4) Crear nuevo circulo");
      System.out.printf("| %-"+uiWidth+"s |\n", "(5) Crear nuevo canvas");
      System.out.printf("| %-"+uiWidth+"s |\n", "(0) Salir");
      print(": ");

      try {
        menuOption = input.nextInt();
      } catch (Exception e) {
        input.nextLine();
        print("Ingrese un valor valido >:|\n");
        continue;
      }

      separator();
      // limpia el buffer del scanner, magia de java pa evitar bugs
      input.nextLine();

      if (menuOption == 1) {
        manualCircleMove();
      } else if (menuOption == 2) {
        moveCircle();
      } else if (menuOption == 3) {
        Circle circle2Compare = createNewCircle(true);
        // dibujaria ambos ciruclos en el canvas pero ya quiero terminar el trabajo XD
        String greenColor = terminalCanvas.colors2Codes.get(Colors.green);
        String redColor = terminalCanvas.colors2Codes.get(Colors.red);
        System.out.println(currentCircle.equals(circle2Compare)? greenColor+"Son iguales"+greenColor:redColor+"Son diferentes"+redColor);
      } else if(menuOption == 4){
        createNewCircle(false);
      }else if(menuOption == 5){
        createNewCanvas();
      }
    } while (menuOption != 0);

    input.close();
  }
}
