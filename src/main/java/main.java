import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Francis Cáceres on 25/5/2017.
 */
public class main {
    public static void main(String[] args) throws Exception {
        int countParrafos = 0, countImagen = 0, countFormularios =0, conteoPost=0,conteoGet=0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Direccion url a la que quiere acceder: ");
        String name = br.readLine();

        Document doc = Jsoup.connect(name).get();

        int lines = doc.html().split("\n").length; //Convierto el doc en un string, luego separo a traves de \n en arreglo
        // de strings y al final busco el tamano del arreglo para saber cuantas lineas

        System.out.println("Lineas: " + lines);

        Elements parrafos = doc.getElementsByTag("p");
        for (Element parrafo : parrafos) {
            countParrafos++;
        }

        System.out.println("Parrafos: " + countParrafos);

//        Elements imagenes = doc.getElementsByTag("img");
//        for (Element imagen : imagenes) {
//            countImagen++;
//        }

        Elements par = doc.getElementsByTag("p");
        for (Element parr : par) {
            Document dis = Jsoup.parse(parr.html());
            Elements imge = dis.select("img");

            for (Element i : imge){
                countImagen++;
            }
        }

        System.out.println("Imagenes: " + countImagen);

        Elements formularios = doc.getElementsByTag("form");
        for (Element formulario : formularios) {
            countFormularios++;
            if(formulario.attr("method").equals("get"))
                conteoGet++;

            if(formulario.attr("method").equals("post"))
                conteoPost++;
        }

        System.out.println("Formularios: " + countFormularios);
        System.out.println("Formularios_Get: " + conteoGet);
        System.out.println("Formularios_Post: " + conteoPost);

        Elements forms = doc.getElementsByTag("form");
        for (Element form : forms) {
            Elements entradas = doc.getElementsByTag("input");
            for(Element entrada : entradas) {
                System.out.println();
                System.out.println("Nombre: " + entrada.attr("name"));
                System.out.println("Tipo: " + entrada.attr("type"));
            }
        }


        for (Element fom : forms){
            if(fom.attr("method").toString().toLowerCase().equals("post")) {
                System.out.println("Esto es post");
                int num = name.length()-1;

                Document respuesta = null;
                String objetivo = fom.attr("action");

                if (!(num > objetivo.length())){
                    respuesta = Jsoup.connect(objetivo)
                            .data("asignatura", "practica1")
                            .post();

                }else{
                    respuesta = Jsoup.connect(name.substring(0,name.indexOf("/",9))+objetivo)
                            .data("asignatura", "practica1")
                            .post();
                }

                System.out.println(respuesta);
            }
        }

    }
}
