import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class TransporteAnalisisService {

    public static Map<String, Long> calcularAfluenciaPorEstacion(List<RegistroTransporte> registros) {
        return registros.stream()
                .filter(r -> "entrada".equalsIgnoreCase(r.accion()))
                .collect(Collectors.groupingBy(
                        RegistroTransporte::estacion,
                        Collectors.counting()
                ));
    }

    public static Map<Integer, Long> identificarHorasPico(List<RegistroTransporte> registros) {
        return registros.stream()
                .collect(Collectors.groupingBy(
                        r -> r.timestamp().getHour(),
                        Collectors.counting()
                ));
    }

   // c) Rutas más utilizadas (ordenadas descendentemente por volumen de pasajes)
    public static List<Map.Entry<String, Long>> obtenerRutasMasUtilizadas(
        List<RegistroTransporte> registros) {

    return registros.stream()
            .collect(Collectors.groupingBy(
                    RegistroTransporte::ruta,
                    Collectors.counting()
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .collect(Collectors.toList());
}

    public static Map<String, List<String>> obtenerPatronesViajePorUsuario(List<RegistroTransporte> registros) {
        return registros.stream()
                .sorted(Comparator.comparing(RegistroTransporte::timestamp))
                .collect(Collectors.groupingBy(
                        RegistroTransporte::idUsuario,
                        Collectors.mapping(RegistroTransporte::estacion, Collectors.toList())
                ));
    }

    public static Map<String, Double> calcularTiempoPromedioEntreEstaciones(List<RegistroTransporte> registros) {
        Map<String, List<RegistroTransporte>> registrosPorUsuario = registros.stream()
                .sorted(Comparator.comparing(RegistroTransporte::timestamp))
                .collect(Collectors.groupingBy(RegistroTransporte::idUsuario));

        return registrosPorUsuario.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            List<RegistroTransporte> lista = entry.getValue();
                            if (lista.size() < 2) return 0.0;
                            double totalMinutos = 0;
                            int intervalos = 0;
                            for (int i = 0; i < lista.size() - 1; i++) {
                                Duration d = Duration.between(lista.get(i).timestamp(), lista.get(i + 1).timestamp());
                                totalMinutos += d.toMinutes();
                                intervalos++;
                            }
                            return intervalos > 0 ? totalMinutos / intervalos : 0.0;
                        }
                ));
    }

    public static Map<String, String> detectarSobrecargaRutas(List<RegistroTransporte> registros, long umbral) {
        return registros.stream()
                .collect(Collectors.groupingBy(
                        RegistroTransporte::ruta,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue() > umbral ? "CRÍTICA (" + e.getValue() + " viajes)" : "NORMAL (" + e.getValue() + " viajes)"
                ));
    }
}