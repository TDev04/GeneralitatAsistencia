package org.vinyes.asistencia.NFCDriver;

import java.time.*;
import java.util.concurrent.*;
import org.vinyes.asistencia.Database.RegistroDAO;

public class RegistroScheduler {
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public static void iniciarTareaCorreccionDiaria() {
        Runnable tarea = RegistroDAO::corregirFichajesOlvidados; // lambda hell

        // cuanto falta para las 23.00
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime hoyA23 = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 0));

        // si ya han pasado las 23:00, lo programamos para mañana
        if (ahora.isAfter(hoyA23)) {
            hoyA23 = hoyA23.plusDays(1);
        }

        // joe hahaha
        long delayInicial = Duration.between(ahora, hoyA23).toMillis();
        long intervalo24h = Duration.ofHours(24).toMillis();

        scheduler.scheduleAtFixedRate(tarea, delayInicial, intervalo24h, TimeUnit.MILLISECONDS);

        System.out.println("[debug] Inicio de salidas programada a las 23:00.");
    }
}
