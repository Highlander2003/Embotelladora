import java.util.concurrent.Semaphore;

class PlantaEmbotelladora {
    private final int MaxCaja = 10; // Capacidad en litros
    private int BotellaCaja = 0; // Botellas actuales en la caja
    private Semaphore semaforoCaja = new Semaphore(1); // Semáforo para gestionar el acceso a la caja
    private Semaphore semaforoEmpaquetado = new Semaphore(0); // Semáforo para el empaquetador

    public void embotellar(int totalBotellas) throws InterruptedException {
        for (int i = 0; i < totalBotellas; i++) { 
            semaforoCaja.acquire(); // Adquirir acceso a la caja
            BotellaCaja++;
            System.out.println("Botellas " + (i + 1) + " listas. Botellas en caja: " + BotellaCaja);
            if (BotellaCaja == MaxCaja) {
                System.out.println("Embotellador: Caja llena, se envía señal al empaquetador.");
                semaforoEmpaquetado.release(); // Señal al empaquetador
                BotellaCaja = 0; // Reiniciar contador de botellas en caja
            }
            semaforoCaja.release(); 
            Thread.sleep(1000); // Simular tiempo de embotellado
        }
    }

    public void empaquetar() throws InterruptedException {
        while (true) {
            semaforoEmpaquetado.acquire(); // Esperar a que haya una caja llena
            semaforoCaja.acquire(); 
            System.out.println("Empaquetador: Empaquetando caja.");
            semaforoCaja.release(); 
            System.out.println("Empaquetador: Caja empaquetada y almacenada.");
            Thread.sleep(2000); // Simular tiempo de empaquetado
        }
    }

    public static void main(String[] args) {
        PlantaEmbotelladora planta = new PlantaEmbotelladora();
        int totalBotellas = 100; 

        Thread embotelladorThread = new Thread(() -> {
            try {
                planta.embotellar(totalBotellas); // Pasar el número total de botellas
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread empaquetadorThread = new Thread(() -> {
            try {
                planta.empaquetar();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        embotelladorThread.start();
        empaquetadorThread.start();
    }
}
