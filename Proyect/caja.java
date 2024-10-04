package Proyect;
import java.util.concurrent.Semaphore;

class Caja {
    
    private int totalCajasEmpacadas = 0;
    private final int capacidadMaxima = 10; // Capacidad en litros
    private int botellasEnCaja = 0; // Botellas actuales en la caja
    private Semaphore semaforoCaja = new Semaphore(1); // Semáforo para gestionar el acceso a la caja

    public void agregarBotella() throws InterruptedException {
        semaforoCaja.acquire(); // Adquirir acceso a la caja
        botellasEnCaja++;
        if (botellasEnCaja == capacidadMaxima) {
            System.out.println("Caja llena.");
            totalCajasEmpacadas++;
            System.out.println("Total de cajas almacenadas: " + totalCajasEmpacadas);
        }
        semaforoCaja.release(); // Liberar acceso a la caja
    }

    public boolean estaLlena() {
        return botellasEnCaja == capacidadMaxima;
    }

    public void reiniciar() {
        botellasEnCaja = 0;
    }

    public Semaphore getSemaforoCaja() {
        return semaforoCaja;
    }

    public int getBotellasEnCaja() {
        return botellasEnCaja;
    }
}
