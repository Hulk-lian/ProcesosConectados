import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ProcesosConectados {

	public static void main(String[] args) {
		
		ProcessBuilder procesoA= new ProcessBuilder("ls","-l", "/home/usuario");
		ProcessBuilder procesoB= new ProcessBuilder("tr", "a","@");// tr cambia la letra indicada por otra
		
		try {
			Process pa=procesoA.start();
			Process pb=procesoB.start();
			//conexion de los flujos de salida y de entrada entre procesos
			BufferedReader salidaA= new BufferedReader(new InputStreamReader(pa.getInputStream(),"UTF-8"));
			BufferedWriter entradaB= new BufferedWriter(new OutputStreamWriter(pb.getOutputStream(),"UTF-8"));
			
			//realizacion del intercambio de datos entre un proceso y otro
			
			String lineaResultadoA;
			while ((lineaResultadoA=salidaA.readLine())!=null) {
				entradaB.write(lineaResultadoA);
				entradaB.newLine();
			}
			
			//cerrada para que el sistema limpie los recursos
			salidaA.close();
			entradaB.close();

			//conectamos flujos de salida del proceso b a consola
			
			BufferedReader salidaB= new BufferedReader(new InputStreamReader(pb.getInputStream(),"UTF-8"));
			
			String resultadoB;
			while ((resultadoB=salidaB.readLine())!=null) {
				System.out.print(resultadoB);
				System.out.println("\n");
			}
			
			salidaB.close();
			int finpa=pa.waitFor();
			int finpb=pb.waitFor();
			
			System.out.println("Procesos hijos finalizados y con salida "+finpa+" y "+finpb);
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	
}

