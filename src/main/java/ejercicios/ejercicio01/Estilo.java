package ejercicios.ejercicio01;

import java.util.ArrayList;

public class Estilo {
	
	public static void main(String[]args) {
		ArrayList<String> arr = new ArrayList<String>();
		
 		 for(int i = 0; i < 50 ; i++) {
 			 arr.add(String.valueOf(Math.random() < .5 ? i : -i));
 		 }

 		 int i = 0;
 		 while(true) {
 			 if(i == arr.size()) {
 				 System.out.println("No se ha encontrado ningún valor negativo");
 				 break;
 			 }
 			 
 			 if(Integer.valueOf(arr.get(i)) >= 0) {
 	 			 i++;
 				 continue;
 			 }
 			 
 			 System.out.println("He encontrado el primer valor negativo: " + arr.get(i));
 			 break;
 		 }
	}
}
