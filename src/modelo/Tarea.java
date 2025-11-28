package modelo;


public class Tarea implements Runnable{
    
    String tarea;
    

    @Override
    public void run() {
       try{
            System.out.println("se inicia la tarea 1"+Tarea);
            Thread.sleep(3000);
            System.out.println("fin de la tarea"+nombreTarea);
            System.out.println("se inicia la tarea 1"+nombreTarea);
            
            
        }catch(InterruptedException e){
            e.printStackTrace();
    }
    
    
}
