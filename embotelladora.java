class Embotelladora implements Runnable {
    private Caja caja;
    private int totalBotellas;

    public Embotelladora(Caja caja, int totalBotellas) {
        this.caja = caja;
        this.totalBotellas = totalBotellas;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < totalBotellas; i++) {
                caja.agregarBotella();
                System.out.println("Botellas " + (i + 1) + " listas. Botellas en caja: " + caja.getBotellasEnCaja());
                if (caja.estaLlena()) {
                    System.out.println("Embotellador: Caja llena, se envía señal al empaquetador.");
                    caja.reiniciar(); // Reiniciar contador de botellas en caja
                }
                Thread.sleep(100); // Simular tiempo de embotellado
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
