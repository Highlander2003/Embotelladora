Importa la clase Semaphore: Esta clase es parte del paquete java.util.concurrent y se utiliza para controlar el acceso a un recurso compartido mediante la utilización de contadores. Es útil en situaciones de concurrencia, donde múltiples hilos pueden acceder a un recurso al mismo tiempo.

    import java.util.concurrent.Semaphore;

- MaxCaja: Constante que representa la capacidad máxima de botellas que puede contener una caja (10 botellas en este caso).
- BotellaCaja: Variable que cuenta cuántas botellas están actualmente en la caja. Inicialmente está en 0.
- semaforoCaja: Un semáforo que permite que solo un hilo acceda a la variable BotellaCaja a la vez, evitando condiciones de carrera.
- semaforoEmpaquetado: Un semáforo que inicia en 0. Se utiliza para señalar al empaquetador cuando hay una caja llena lista para ser empaquetada.

    private final int MaxCaja = 10; // Capacidad en litros
    private int BotellaCaja = 0; // Botellas actuales en la caja
    private Semaphore semaforoCaja = new Semaphore(1); // Semáforo para gestionar el acceso a la caja
    private Semaphore semaforoEmpaquetado = new Semaphore(0); // Semáforo para el empaquetador

Definición del método embotellar: Este método embotella un número específico de botellas (totalBotellas). Puede lanzar InterruptedException, que debe ser manejada si el hilo es interrumpido.

    public void embotellar(int totalBotellas) throws InterruptedException

Adquiere el semáforo: Asegura que solo un hilo pueda modificar BotellaCaja a la vez.

    semaforoCaja.acquire(); // Adquirir acceso a la caja

Incrementa BotellaCaja: Se añade una botella y se imprime el número de botellas listas hasta ahora.

    BotellaCaja++;
    System.out.println("Botellas " + (i + 1) + " listas. Botellas en caja: " + BotellaCaja);

Verifica si la caja está llena: Si se alcanza el número máximo de botellas, se imprime un mensaje y se libera el semáforo semaforoEmpaquetado para notificar al empaquetador que hay una caja lista. Luego, BotellaCaja se reinicia a 0.

    if (BotellaCaja == MaxCaja) {
        System.out.println("Embotellador: Caja llena, se envía señal al empaquetador.");
        semaforoEmpaquetado.release(); // Señal al empaquetador
        BotellaCaja = 0; // Reiniciar contador de botellas en caja
    }

Libera el semáforo: Permite que otros hilos accedan a BotellaCaja. Luego, simula el tiempo de embotellado con Thread.sleep(1000), que pausa el hilo durante 1000 milisegundos (1 segundo).

    semaforoCaja.release(); // Liberar acceso a la caja
    Thread.sleep(1000); // Simular tiempo de embotellado

Definición del método empaquetar: Este método ejecuta un bucle infinito que espera y empaqueta cajas llenas. También puede lanzar InterruptedException

    public void empaquetar() throws InterruptedException {
        while (true) {

Adquiere el semáforo: Espera hasta que un embotellador libere el semáforo, indicando que hay una caja llena lista para empaquetar.
 
    semaforoEmpaquetado.acquire(); // Esperar a que haya una caja llena

Adquiere el semáforo semaforoCaja: Asegura que el acceso a BotellaCaja es seguro, y se imprime un mensaje indicando que el empaquetador está en proceso. 

    semaforoCaja.acquire(); // Adquirir acceso a la caja
    System.out.println("Empaquetador: Empaquetando caja.");

Libera el semáforo semaforoCaja: Permite que otros hilos accedan a la caja. Se imprime un mensaje confirmando que la caja ha sido empaquetada.

    semaforoCaja.release(); // Liberar acceso a la caja
    System.out.println("Empaquetador: Caja empaquetada y almacenada.");

Simula el tiempo de empaquetado: Pausa el hilo durante 2000 milisegundos (2 segundos).
 
    Thread.sleep(2000); // Simular tiempo de empaquetado

Crea un hilo para el embotellador: Se utiliza una expresión lambda para definir la tarea del hilo, que llama al método embotellar(). Si se lanza una InterruptedException, se maneja de forma que el hilo se interrumpe correctamente.
    Thread embotelladorThread = new Thread(() -> {
        try {
            planta.embotellar(totalBotellas); // Pasar el número total de botellas
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    });

Crea un hilo para el empaquetador: Similar al embotellador, pero llama al método empaquetar().

    Thread empaquetadorThread = new Thread(() -> {
        try {
            planta.empaquetar();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    });

Inicia ambos hilos: Llama a start() en ambos hilos, lo que hace que comiencen a ejecutarse de forma concurrente.

    embotelladorThread.start();
    empaquetadorThread.start();
