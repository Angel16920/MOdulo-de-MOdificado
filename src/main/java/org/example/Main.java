package org.example;


import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    // Datos de la cuenta registrada
    static String cliente;
    static String correoRegistrado;
    static double telefono;
    static int psw;
    static int hayCuenta = 0;
    static int rolRegistrado = 0; // 1 = Cliente, 2 = Fotógrafo
    static final String CONTRASENIA_FOTOGRAFO = "foto123";

    // ARREGLOS PARA FOTOGRAFÍAS EJECUTIVAS
    static String[] nombresServicios = {
            "Foto infantil", "Foto óvalo", "Foto para cartilla",
            "Foto para diploma / título", "Foto para pasaporte / visa", "Foto para documentos oficiales"
    };

    static String[] tamaniosServicios = {
            "3.5 x 2 cm", "5 x 7 cm (aprox.)", "3.5 x 2.5 cm",
            "5 x 7 cm (aprox.)", "5 x 5 cm", "4.5 x 3.5 cm"
    };

    static String[] descripcionesServicios = {
            "Ideal para cartilla, credencial escolar y otros documentos oficiales.",
            "Perfecta para títulos, certificados y documentos institucionales.",
            "Requerida para la cartilla del servicio militar y otros trámites oficiales.",
            "Ideal para diplomas, títulos profesionales y certificados académicos.",
            "Cumple con las medidas y requisitos internacionales para pasaportes y visas.",
            "Útil para credenciales, constancias y trámites gubernamentales."
    };

    static double[] preciosServicios = {50.0, 80.0, 60.0, 90.0, 100.0, 70.0};

    static final int MAX_SESIONES = 1000;
    static String[] clientesRegistrados = new String[MAX_SESIONES];
    static String[] correosClientesRegistrados = new String[MAX_SESIONES];
    static double[] telefonosClientesRegistrados = new double[MAX_SESIONES];

    static String[] serviciosRegistrados = new String[MAX_SESIONES];
    static String[] fechasRegistradas = new String[MAX_SESIONES];
    static String[] horasRegistradas = new String[MAX_SESIONES];
    static double[] presupuestosRegistrados = new double[MAX_SESIONES];

    static double[] anticiposRegistrados = new double[MAX_SESIONES];
    static double[] restantesRegistrados = new double[MAX_SESIONES];
    static int totalSesiones = 0;

    static int leerEntero() {
        while (!sc.hasNextInt()) {
            System.out.println("Opción incorrecta");
            sc.next();
            System.out.println("Ingrese un numero entero");
        }
        var numero = sc.nextInt();
        sc.nextLine();
        return numero;
    }

    static int sonIguales(String a, String b) {
        if (a.length() != b.length()) {
            return 0;
        }
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return 0;
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        var opcion = 0;

        while (opcion != 3) {
            System.out.print("""
                    =======================
                    Bienvenido
                    1. Registrarse
                    2. Iniciar Sesion
                    3. Salir
                    =======================
                    Elige una opcion: \s""");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> registrar();
                case 2 -> iniciarSesion();
                case 3 -> System.out.println("Tenga un buen dia");
                default -> System.out.println("Opcion no válida");
            }
        }

        sc.close();
    }

    static void registrar() {
        System.out.print("""
                =======================
                      REGISTRO      
                =======================
                ¿Qué tipo de usuario eres?
                1. Cliente
                2. Fotógrafo
                Elige una opción:\s""");
        rolRegistrado = leerEntero();

        while (true) {

            while (rolRegistrado < 1 || rolRegistrado > 2) {
                System.out.println("Opción no válida. Elige 1 para Cliente o 2 para Fotógrafo:");
                rolRegistrado = leerEntero();
            }

            if (rolRegistrado == 2) {
                var intentosFoto = 0;
                var accesoFoto = false;

                while (!accesoFoto && intentosFoto < 3) {
                    System.out.print("Ingresa la contraseña de acceso para Fotógrafo: ");
                    var passIngresada = sc.nextLine();

                    if (passIngresada.equals(CONTRASENIA_FOTOGRAFO)) {
                        accesoFoto = true;
                    } else {
                        intentosFoto++;
                        System.out.println("Contraseña incorrecta. Intentos restantes: " + (3 - intentosFoto));
                    }
                }

                if (!accesoFoto) {
                    System.out.println("Se agotaron los 3 intentos. Regresando a seleccionar Cliente o Fotógrafo...");
                    continue;
                }
            }

            break;
        }

        System.out.println("Nombre completo");
        cliente = sc.nextLine();

        System.out.println("Correo electronico");
        correoRegistrado = sc.nextLine();

        System.out.println("Número telefonico");
        while (!sc.hasNextDouble()) {
            System.out.println("No ingrese caracteres");
            sc.next();
            System.out.println("Ingrese otra vez");
        }

        telefono = sc.nextDouble();
        sc.nextLine();

        System.out.println("Crear una contraseña (solo numeros)");
        psw = leerEntero();

        System.out.println("Confirme la contraseña");
        var confirmas = leerEntero();
        while (confirmas != psw) {
            System.out.println("Las contraseñas no coinciden");
            System.out.println("Ingrese otra vez");
            confirmas = leerEntero();
        }

        hayCuenta = 1;
        System.out.println("Cuenta registrada correctamente como: " + (rolRegistrado == 2 ? "Fotógrafo" : "Cliente"));
    }

    static void iniciarSesion() {
        System.out.print("""
                =======================
                Iniciar Sesion
                =======================
                """);

        if (hayCuenta == 0) {
            System.out.println("Aun no hay cuentas registradas");
            System.out.println("¿No tienes cuenta? Registrate");
            return;
        }

        var intentos = 0;
        var acceso = 0;

        while (acceso == 0 && intentos < 3) {
            System.out.println("Nombre completo");
            var nombreIngresado = sc.nextLine();
            System.out.println("Contraseña");
            var contras = leerEntero();

            if (sonIguales(nombreIngresado, cliente) == 1 && contras == psw) {
                acceso = 1;
            } else {
                intentos++;
                System.out.println("Nombre o contraseña incorrectos");
                if (intentos < 3) {
                    System.out.println("Ingrese otra vez");
                }
            }
        }

        if (acceso == 0) {
            System.out.println("Demasiados intentos, regresando al menu");
            return;
        }

        System.out.println("Bienvenido, " + cliente + " (" + (rolRegistrado == 2 ? "Fotógrafo" : "Cliente") + ")");
        menuModulos();
    }

    static void menuModulos() {
        var opcion1 = 0;

        while (opcion1 != 4) {
            System.out.print("""
                    =======================
                    Elige un Modulo
                    1. Retrato
                    2. Evento Social
                    3. Fotografias Ejecutivas
                    4. Cerrar sesion
                    =======================
                    """);
            opcion1 = leerEntero();

            switch (opcion1) {
                case 1 -> System.out.println("Modulo Retrato (En construcción)");
                case 2 -> System.out.println("Modulo Evento Social (En construcción)");
                case 3 -> menuFotografiasEjecutivas();
                case 4 -> System.out.println("Sesion cerrada");
                default -> System.out.println("Opcion no válida");
            }
        }
    }

    static void menuFotografiasEjecutivas() {
        var opcionFoto = 0;
        var etiquetaRol = (rolRegistrado == 2) ? "FOTÓGRAFO" : "CLIENTE";

        do {
            System.out.println("""
                    ========================================
                          FOTOGRAFÍAS EJECUTIVAS (%s)
                    ========================================""".formatted(etiquetaRol));

            if (rolRegistrado == 2) {
                System.out.print("""
                        1. Ver catálogo de servicios
                        2. Agendar sesión nueva
                        3. Ver sesiones agendadas
                        4. Buscar cliente
                        5. Eliminar sesión
                        6. Ordenar sesiones por fecha
                        7. Regresar al menú anterior
                        Elige una opción:\s""");

                opcionFoto = leerEntero();

                switch (opcionFoto) {
                    case 1 -> mostrarCatalogo();
                    case 2 -> agendarSesion(sc);
                    case 3 -> mostrarSesionesGuardadas();
                    case 4 -> buscarCliente(sc);
                    case 5 -> eliminarSesion(sc);
                    case 6 -> ordenarSesionesBurbuja();
                    case 7 -> System.out.println("Regresando...");
                    default -> System.out.println("Error: Opción no válida.");
                }
            } else {
                System.out.print("""
                        1. Ver catálogo de servicios
                        2. Agendar sesión nueva
                        3. Ver mis sesiones agendadas
                        4. Regresar al menú anterior
                        Elige una opción:\s""");

                opcionFoto = leerEntero();

                switch (opcionFoto) {
                    case 1 -> mostrarCatalogo();
                    case 2 -> agendarSesion(sc);
                    case 3 -> mostrarSesionesGuardadas();
                    case 4 -> System.out.println("Regresando...");
                    default -> System.out.println("Error: Opción no válida.");
                }
            }
            System.out.println();
        } while ((rolRegistrado == 2 && opcionFoto != 7) || (rolRegistrado == 1 && opcionFoto != 4));
    }

    public static void mostrarCatalogo() {
        System.out.println("\n--- CATÁLOGO DE SERVICIOS ---");
        for (int i = 0; i < nombresServicios.length; i++) {
            System.out.print("""
                    %d. %s
                       - Tamaño: %s
                       - Descripción: %s
                       - Presupuesto: $%s
                    ----------------------------------------
                    """.formatted(
                    i + 1,
                    nombresServicios[i],
                    tamaniosServicios[i],
                    descripcionesServicios[i],
                    preciosServicios[i]
            ));
        }
    }

    public static void agendarSesion(Scanner scanner) {
        if (totalSesiones >= MAX_SESIONES) {
            System.out.println("Lo sentimos, la agenda está llena.");
            return;
        }

        mostrarCatalogo();

        var seleccion = 0;
        var servicioValido = false;
        while (!servicioValido) {
            System.out.print("Elige el número de servicio (1-6): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Error: Debes ingresar un número válido.");
                scanner.next();
            } else {
                seleccion = scanner.nextInt();
                scanner.nextLine();
                if (seleccion >= 1 && seleccion <= nombresServicios.length) {
                    servicioValido = true;
                } else {
                    System.out.println("Error: El número de servicio debe ser del 1 al " + nombresServicios.length + ".");
                }
            }
        }

        var indice = seleccion - 1;

        var clienteNombre = "";
        var telefonoCliente = 0.0;

        if (rolRegistrado == 1) {
            clienteNombre = cliente;
            telefonoCliente = telefono;
            System.out.println("Cliente: " + clienteNombre);
        } else {
            System.out.print("Ingresa el nombre completo del cliente: ");
            clienteNombre = scanner.nextLine();

            System.out.print("Ingresa el número telefónico del cliente: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Error: Ingresa un número válido.");
                scanner.next();
            }
            telefonoCliente = scanner.nextDouble();
            scanner.nextLine();
        }

        var fecha = "";
        var hora = "";
        var horarioDisponible = false;
        while (!horarioDisponible) {
            System.out.print("Ingresa la fecha (ej. 20/11/26): ");
            fecha = scanner.nextLine();

            System.out.print("Ingresa la hora (ej. 2:00): ");
            hora = scanner.nextLine();

            var ocupado = false;
            for (int i = 0; i < totalSesiones; i++) {
                if (fechasRegistradas[i].equalsIgnoreCase(fecha) && horasRegistradas[i].equalsIgnoreCase(hora)) {
                    ocupado = true;
                    break;
                }
            }

            if (ocupado) {
                System.out.println("\nError: Lo sentimos, ese horario y fecha ya están ocupados. Elige otros.");
            } else {
                horarioDisponible = true;
            }
        }

        var anticipo = -1.0;
        var anticipoValido = false;
        while (!anticipoValido) {
            System.out.print("Ingresa el monto del anticipo: $");
            if (!scanner.hasNextDouble()) {
                System.out.println("Error: El anticipo debe ser una cantidad numérica válida.");
                scanner.next();
            } else {
                anticipo = scanner.nextDouble();
                scanner.nextLine();
                if (anticipo >= 0) {
                    anticipoValido = true;
                } else {
                    System.out.println("Error: El anticipo no puede ser negativo.");
                }
            }
        }

        var costoTotal = preciosServicios[indice];
        var restante = costoTotal - anticipo;

        clientesRegistrados[totalSesiones] = clienteNombre;
        correosClientesRegistrados[totalSesiones] = (rolRegistrado == 1) ? correoRegistrado : "externo@correo.com";
        telefonosClientesRegistrados[totalSesiones] = telefonoCliente;
        serviciosRegistrados[totalSesiones] = nombresServicios[indice];
        fechasRegistradas[totalSesiones] = fecha;
        horasRegistradas[totalSesiones] = hora;
        presupuestosRegistrados[totalSesiones] = costoTotal;
        anticiposRegistrados[totalSesiones] = anticipo;
        restantesRegistrados[totalSesiones] = restante;

        var indexCreado = totalSesiones;
        totalSesiones++;

        System.out.println("\n¡Sesión registrada con éxito!");
        imprimirTicket(indexCreado);
    }

    public static void mostrarSesionesGuardadas() {
        if (totalSesiones == 0) {
            System.out.println("\nNo hay sesiones agendadas todavía.");
            return;
        }

        var encontroAlguna = false;

        for (int i = 0; i < totalSesiones; i++) {
            if (rolRegistrado == 2 || correosClientesRegistrados[i].equalsIgnoreCase(correoRegistrado)) {
                imprimirTicket(i);
                encontroAlguna = true;
            }
        }

        if (rolRegistrado == 1 && !encontroAlguna) {
            System.out.println("\nNo tienes sesiones agendadas actualmente.");
        }
    }

    public static void buscarCliente(Scanner scanner) {
        System.out.println("\n--- BUSCAR CLIENTE ---");
        if (totalSesiones == 0) {
            System.out.println("No hay sesiones registradas.");
            return;
        }

        System.out.print("Ingresa el nombre del cliente: ");
        var nombreBuscado = scanner.nextLine();
        var encontrado = false;

        for (int i = 0; i < totalSesiones; i++) {
            if (clientesRegistrados[i].toLowerCase().contains(nombreBuscado.toLowerCase())) {
                System.out.println("\n--- CLIENTE ENCONTRADO ---");
                imprimirTicket(i);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ningún cliente con ese nombre.");
        }
    }

    public static void eliminarSesion(Scanner scanner) {
        System.out.println("\n--- ELIMINAR SESIÓN ---");
        if (totalSesiones == 0) {
            System.out.println("No hay sesiones agendadas.");
            return;
        }

        mostrarSesionesGuardadas();
        System.out.print("Ingresa el número de la sesión que deseas eliminar: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Error: Debes ingresar un número válido.");
            scanner.next();
            return;
        }

        var numSesion = scanner.nextInt();
        scanner.nextLine();

        if (numSesion < 1 || numSesion > totalSesiones) {
            System.out.println("Error: El número de sesión no es válido.");
            return;
        }

        var indiceAEliminar = numSesion - 1;

        for (int i = indiceAEliminar; i < totalSesiones - 1; i++) {
            clientesRegistrados[i] = clientesRegistrados[i + 1];
            correosClientesRegistrados[i] = correosClientesRegistrados[i + 1];
            telefonosClientesRegistrados[i] = telefonosClientesRegistrados[i + 1];
            serviciosRegistrados[i] = serviciosRegistrados[i + 1];
            fechasRegistradas[i] = fechasRegistradas[i + 1];
            horasRegistradas[i] = horasRegistradas[i + 1];
            presupuestosRegistrados[i] = presupuestosRegistrados[i + 1];
            anticiposRegistrados[i] = anticiposRegistrados[i + 1];
            restantesRegistrados[i] = restantesRegistrados[i + 1];
        }

        totalSesiones--;
        System.out.println("\n¡Sesión eliminada con éxito!");
    }

    public static void ordenarSesionesBurbuja() {
        System.out.println("\n--- ORDENAR SESIONES POR FECHA ---");
        if (totalSesiones < 2) {
            System.out.println("No hay suficientes sesiones para ordenar.");
            return;
        }

        for (int i = 0; i < totalSesiones - 1; i++) {
            for (int j = 0; j < totalSesiones - 1 - i; j++) {
                if (fechasRegistradas[j].compareTo(fechasRegistradas[j + 1]) > 0) {
                    var tempP = presupuestosRegistrados[j];
                    presupuestosRegistrados[j] = presupuestosRegistrados[j + 1];
                    presupuestosRegistrados[j + 1] = tempP;

                    var tempC = clientesRegistrados[j];
                    clientesRegistrados[j] = clientesRegistrados[j + 1];
                    clientesRegistrados[j + 1] = tempC;

                    var tempCorreo = correosClientesRegistrados[j];
                    correosClientesRegistrados[j] = correosClientesRegistrados[j + 1];
                    correosClientesRegistrados[j + 1] = tempCorreo;

                    var tempTel = telefonosClientesRegistrados[j];
                    telefonosClientesRegistrados[j] = telefonosClientesRegistrados[j + 1];
                    telefonosClientesRegistrados[j + 1] = tempTel;

                    var tempS = serviciosRegistrados[j];
                    serviciosRegistrados[j] = serviciosRegistrados[j + 1];
                    serviciosRegistrados[j + 1] = tempS;

                    var tempF = fechasRegistradas[j];
                    fechasRegistradas[j] = fechasRegistradas[j + 1];
                    fechasRegistradas[j + 1] = tempF;

                    var tempH = horasRegistradas[j];
                    horasRegistradas[j] = horasRegistradas[j + 1];
                    horasRegistradas[j + 1] = tempH;

                    var tempA = anticiposRegistrados[j];
                    anticiposRegistrados[j] = anticiposRegistrados[j + 1];
                    anticiposRegistrados[j + 1] = tempA;

                    var tempR = restantesRegistrados[j];
                    restantesRegistrados[j] = restantesRegistrados[j + 1];
                    restantesRegistrados[j + 1] = tempR;
                }
            }
        }
        System.out.println("¡Sesiones ordenadas por fecha exitosamente!");
        mostrarSesionesGuardadas();
    }

    public static void imprimirTicket(int i) {
        System.out.print("""
                ----------------------------------------
                TICKET DE COMPROBANTE
                ========================================
                No. de Sesión : %d
                Cliente : %s
                Teléfono  : %d
                Servicio : %s
                Fecha : %s
                Hora : %s
                Costo total : $%s
                Anticipo : $%s
                Restante : $%s
                ========================================
                Gracias por su preferencia
                ----------------------------------------
                """.formatted(
                i + 1,
                clientesRegistrados[i],
                (long) telefonosClientesRegistrados[i],
                serviciosRegistrados[i],
                fechasRegistradas[i],
                horasRegistradas[i],
                presupuestosRegistrados[i],
                anticiposRegistrados[i],
                restantesRegistrados[i]
        ));
    }
}