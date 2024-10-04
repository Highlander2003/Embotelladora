class Empaquetadora implements Runnable {
    private Caja caja;

    public Empaquetadora(Caja caja) {
        this.caja = caja;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (caja.estaLlena()) {
                    System.out.println("Empaquetador: Empaquetando caja.");
                    System.out.println("Empaquetador: Caja empaquetada y almacenada.");
                    Thread.sleep(200); // Simular tiempo de empaquetado
                }
                Thread.sleep(50); // Esperar un momento antes de verificar nuevamente
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
