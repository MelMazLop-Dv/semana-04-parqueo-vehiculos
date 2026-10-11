import java.util.Scanner;

public class ParqueoVehiculos {

    public static double obtenerTarifa(int tipoVehiculo) {
        if (tipoVehiculo == 1) {
            return 5.00;
        } else if (tipoVehiculo == 2) {
            return 8.00;
        } else {
            return 12.00;
        }
    }

    public static String obtenerNombreVehiculo(int tipoVehiculo) {
        if (tipoVehiculo == 1) {
            return "Motocicleta";
        } else if (tipoVehiculo == 2) {
            return "Automóvil";
        } else {
            return "Pickup o camioneta";
        }
    }

    public static double calcularDescuento(double subtotal, int horas) {
        if (horas > 8) {
            return subtotal * 0.15; // Descuento del 15%
        }
        return 0.0;
    }

    public static double calcularPago(int horas, double tarifa) {
        double subtotal = horas * tarifa;
        double descuento = calcularDescuento(subtotal, horas);
        return subtotal - descuento;
    }

    public static double calcularPago(int horas, double tarifa, double recargo) {
        double subtotal = horas * tarifa;
        double descuento = calcularDescuento(subtotal, horas);
        double subtotalConDescuento = subtotal - descuento;
        return subtotalConDescuento + recargo;
    }

    public static void mostrarComprobante(String placa, String tipo, int horas, double tarifa, double subtotal, double descuento, double recargo, double total) {
        System.out.println("========== COMPROBANTE ==========");
        System.out.println("Placa: " + placa);
        System.out.println("Tipo: " + tipo);
        System.out.println("Horas estacionado: "+ horas);
        System.out.println("Tarifa por hora: Q " + tarifa);
        System.out.println("Subtotal Q "+ subtotal);
        System.out.println("Recargo por ticket perdido: Q " + recargo);
        System.out.println("TOTAL: Q "+ total);
        System.out.println("==================================");
    };


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int cantidadVehiculos = 0;

        while (cantidadVehiculos <= 0) {
            System.out.print("Ingrese la cantidad de vehículos: ");
            if (scanner.hasNextInt()) {
                cantidadVehiculos = scanner.nextInt();
                if (cantidadVehiculos <= 0) {
                    System.out.println("Debe ser mayor a cero.");
                }
            } else {
                System.out.println("Ingrese un número válido.");
                scanner.next();
            }
        }

        int contMotos = 0;
        int contAutos = 0;
        int contPickups = 0;
        int contTicketsPerdidos = 0;
        double totalRecaudado = 0.0;

        double mayorPago = 0.0;
        String placaMayorPago = "";

        for (int i = 1; i <= cantidadVehiculos; i++) {
            System.out.println("\nVehículo " + i);

            System.out.print("Número de placa: ");
            String placa = scanner.next();

            int tipo = 0;
            while (tipo < 1 || tipo > 3) {
                System.out.print("Tipo (1: Motocicleta, 2: Automóvil, 3: Pickup): ");
                if (scanner.hasNextInt()) {
                    tipo = scanner.nextInt();
                    if (tipo < 1 || tipo > 3) {
                        System.out.println("Opción inválida (debe ser 1, 2 o 3).");
                    }
                } else {
                    System.out.println("Ingrese un número.");
                    scanner.next();
                }
            }

            int horas = 0;
            while (horas <= 0) {
                System.out.print("Horas estacionadas: ");
                if (scanner.hasNextInt()) {
                    horas = scanner.nextInt();
                    if (horas <= 0) {
                        System.out.println("Las horas deben ser mayores a cero.");
                    }
                } else {
                    System.out.println("Ingrese un número válido.");
                    scanner.next();
                }
            }

            char perdioTicket = 'X';
            while (perdioTicket != 'S' && perdioTicket != 'N') {
                System.out.print("¿Perdió el ticket? (S/N): ");
                String respuesta = scanner.next().toUpperCase();
                if (respuesta.length() == 1 && (respuesta.charAt(0) == 'S' || respuesta.charAt(0) == 'N')) {
                    perdioTicket = respuesta.charAt(0);
                } else {
                    System.out.println("Ingrese únicamente S o N.");
                }
            }

            if (tipo == 1) {
                contMotos++;
            } else if (tipo == 2) {
                contAutos++;
            } else {
                contPickups++;
            }

            double tarifa = obtenerTarifa(tipo);
            String nombreTipo = obtenerNombreVehiculo(tipo);
            double subtotal = horas * tarifa;
            double descuento = calcularDescuento(subtotal, horas);

            double totalPagar = 0.0;
            double recargo = 0.0;

            if (perdioTicket == 'S') {
                recargo = 50.00;
                contTicketsPerdidos++;
                totalPagar = calcularPago(horas, tarifa, recargo);
            } else {
                totalPagar = calcularPago(horas, tarifa);
            }

            totalRecaudado += totalPagar;

            if (i == 1 || totalPagar > mayorPago) {
                mayorPago = totalPagar;
                placaMayorPago = placa;
            }

            mostrarComprobante(placa, nombreTipo, horas, tarifa, subtotal, descuento, recargo, totalPagar);
        }


        System.out.println("\n=== RESUMEN DE LA JORNADA ===");
        System.out.println("Cantidad de motocicletas: " + contMotos);
        System.out.println("Cantidad de automóviles: " + contAutos);
        System.out.println("Cantidad de pickups o camionetas: " + contPickups);
        System.out.println("Cantidad de tickets perdidos: " + contTicketsPerdidos);
        System.out.printf("Total de dinero recaudado: Q%.2f\n", totalRecaudado);
        System.out.println("Vehículo con el pago más alto - Placa: " + placaMayorPago + " (Q" + String.format("%.2f", mayorPago) + ")");

        scanner.close();
    }
}