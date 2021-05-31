//Author: Mauricio Daniel Hernandez Jovel
/*Project:We need to manage your grades as a group, we need to input your name and your evaluation result (one number from 0 to 10).
Use text files as a database. Statistical module of grades obtained will be required. Is not necessary to use OOP, but we need a good structure of the application.
 We will need to see your top-down design as well.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Main {

  public static void main(String[] args) {

    //Creacion de maqueta de datos del registro
    HashMap<String, Double> registro = new HashMap<>();
    //Llenado de datos
    IntStream.range(0, 10).forEach(i -> {
      String alumno = ingresar(registro);
      double nota = ingresar(alumno);
      registro.put(alumno, nota);
    });
    estadisticas(registro);
  }
  //  ----------------------------Modulo Archivo de Texto--------------------------//
  private static void guardar(StringBuilder estadistica, StringBuilder alumnos) {
    //Creacion del archivo de texto
    try {
      File myObj = new File("prueba.txt");
      if (myObj.createNewFile()) {
        System.out.println("File created: " + myObj.getName());
      }
      else {
        System.out.println("El archivo ya existe");
      }
    } catch (IOException e) {
        System.out.println("Un error ha ocurrido en la creacion del archivo.");
        e.printStackTrace();
      }
    //Escritura en el archivo de texto
    try {
      FileWriter myWriter = new FileWriter("prueba.txt");
      myWriter.write(String.valueOf(alumnos));
      myWriter.write(String.valueOf(estadistica));
      myWriter.close();
      System.out.println("Se ha escrito exitosamente en el archivo.");
    }  catch (FileNotFoundException f)  {
      System.out.println("Error a la hora de buscar archivo donde escribir");
      f.printStackTrace();
    }catch (IOException e) {
        System.out.println("Un error ocurrio a la hora de escribir en los archivos.");
        e.printStackTrace();
    }
    System.out.println(alumnos);
      System.out.println(estadistica);
  }

  //----------------------------Modulo Estadisticas--------------------------//
  private static void estadisticas(HashMap<String, Double> registro) {

    double min = 11;
    double max = -1;
    double sum = 0;
    double llavemenos = -1;
    double llavemas = -1;
    int menos = 21;
    int mas = -1;
    HashMap<Double, Integer> repetidas = new HashMap<>();
    StringBuilder estadistica = new StringBuilder();
    StringBuilder alumnos = new StringBuilder();
    ArrayList<Double> maxrepetidas = new ArrayList<>();
    ArrayList<Double> minrepetidas = new ArrayList<>();
    //En esta porcion de codigo se sacan la menor
    for (String key : registro.keySet()) {
      double valor = registro.get(key);
      alumnos.append(key)
              .append("\t\t\t")
              .append(valor)
              .append("\n");
      if (repetidas.containsKey(valor)) {
        repetidas.replace(valor, repetidas.get(valor) + 1);
      } else {
          repetidas.put(valor, 1);
        }
      if (min > valor) {
        min = valor;
      }
      if (max < valor) {
        max = valor;
      }
      sum += valor;
    }
    //En este codigo se sacan las notas mas y menos repetidas
    for (Double llave : repetidas.keySet()) {
      if (menos > repetidas.get(llave)) {
        menos = repetidas.get(llave);
        llavemenos = llave;
      }
      if (mas < repetidas.get(llave)) {
        mas = repetidas.get(llave);
        llavemas = llave;
      }
    }

    for (Double llave : repetidas.keySet()) {
      if (menos == repetidas.get(llave)) {
        minrepetidas.add(llave);
      }
      if (mas == repetidas.get(llave)) {
        maxrepetidas.add(llave);
      }
    }
    alumnos.append("-------------------------------------------------------------------------------------------------------------- \n");
    estadistica.append("La nota promedio es ")
            .append(sum / 10)
            .append("\nLa nota mayor del grupo es ")
            .append(max)
            .append("\nLa nota minima del grupo es ")
            .append(min)
            .append("\nLa nota mas repeteida es ")
            .append(maxrepetidas)
            .append(" Y se ha repetido ")
            .append(repetidas.get(llavemas))
            .append(" veces ")
            .append("\nLa nota menos repeteida es ")
            .append(minrepetidas)
            .append(" Y se ha repetido ")
            .append(repetidas.get(llavemenos))
            .append(" veces ");

    guardar(estadistica, alumnos);
  }

    //----------------------------METODO DE INGRESO DE ALUMNO--------------------------//
  public static String ingresar(HashMap<String, Double> registro) {
    Scanner entrada = new Scanner(System.in);
    System.out.println("Ingrese el nombre del alumno");
    String alumno = entrada.nextLine();
    alumno = validar(alumno, registro);
    return alumno;
  }
    //----------------------------METODO DE INGRESO DE NOTA--------------------------//
  public static Double ingresar(String alumno) {
    Scanner entrada = new Scanner(System.in);
    System.out.println("Ingrese la nota del alumno " + alumno);
    String notaEntrada = entrada.nextLine();
    return validar(notaEntrada, alumno);
  }
    //----------------------------METODO DE VALIDACION DE ALUMNO--------------------------//
  private static String validar(String alumno, HashMap<String, Double> registro) {
    Pattern pattern = Pattern.compile("[^a-zA-Z ]");
    Matcher matcher = pattern.matcher(alumno);
    boolean matchFound = matcher.find();
    if (matchFound || alumno.isEmpty()) {
      System.out.println("Nombre de alumno incorrecto, porfavor ingrese nuevamente el nombre");
      return ingresar(registro);
    }
    if (registro.containsKey(alumno)) {
      System.out.println("El alumno ya existe porfavor ingrese un nombre diferente");
      return ingresar(registro);
    }
    return alumno;
  }
    //----------------------------METODO DE VALIDACION DE NOTA--------------------------//
  private static Double validar(String nota, String alumno) {
    Pattern pattern = Pattern.compile("(^\\d\\.?\\d?\\d?$)");
    Matcher matcher = pattern.matcher(nota);
    boolean matchFound = matcher.find();
    if (matchFound) {
      if (Double.parseDouble(nota) >= 0 && Double.parseDouble(nota) <= 10) {
        return Double.valueOf(nota);
      } else{
        System.out.println("Error, nota fuera de rango");
        }
    } else {
      System.out.println("Error vuelva a ingresar la nota");
      }
    return ingresar(alumno);
  }
}
