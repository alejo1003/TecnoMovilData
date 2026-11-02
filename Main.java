import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2026, 10, 31, 6, 0);

        List<RegistroTransporte> datos = List.of(
            new RegistroTransporte("U101", "Ruta 318", "San Antonio", "entrada", base),
            new RegistroTransporte("U101", "Ruta 318", "Sabaneta", "salida", base.plusMinutes(25)),
            new RegistroTransporte("U102", "Ruta 319", "San Antonio", "entrada", base.plusMinutes(5)),
            new RegistroTransporte("U102", "Ruta 319", "Aguacatala", "salida", base.plusMinutes(40)),
            new RegistroTransporte("U103", "Ruta 318", "Sabaneta", "entrada", base.plusHours(2)),
            new RegistroTransporte("U103", "Ruta 318", "Poblado", "salida", base.plusHours(2).plusMinutes(15)),
            new RegistroTransporte("U104", "Ruta 318", "San Antonio", "entrada", base),
            new RegistroTransporte("U105", "Ruta 318", "San Antonio", "entrada", base.plusMinutes(10))
        );

        System.out.println("=== TECNOMOVIL DATA: RESULTADOS DEL ANALISIS FUNCIONAL ===\n");
        System.out.println("a) Afluencia por Estacion: " + TransporteAnalisisService.calcularAfluenciaPorEstacion(datos));
        System.out.println("b) Horas Pico: " + TransporteAnalisisService.identificarHorasPico(datos));
        System.out.println("c) Rutas mas Utilizadas: " + TransporteAnalisisService.obtenerRutasMasUtilizadas(datos));
        System.out.println("d) Patrones de Viaje por Usuario: " + TransporteAnalisisService.obtenerPatronesViajePorUsuario(datos));
        System.out.println("e) Tiempo Promedio (Minutos): " + TransporteAnalisisService.calcularTiempoPromedioEntreEstaciones(datos));
        System.out.println("f) Estado de Carga por Ruta: " + TransporteAnalisisService.detectarSobrecargaRutas(datos, 3));
    }
}