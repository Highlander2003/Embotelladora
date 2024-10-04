package Proyect;
public class Main {
    public static void main(String[] args) {
        Caja caja = new Caja();
        int totalBotellas = 100; // Puedes cambiar este número según sea necesario

        Thread embotelladorThread = new Thread(new Embotelladora(caja, totalBotellas));
        Thread empaquetadorThread = new Thread(new Empaquetadora(caja));

        embotelladorThread.start();
        empaquetadorThread.start();
    }
}
