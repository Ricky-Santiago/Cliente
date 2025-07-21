package util;

import org.mindrot.jbcrypt.BCrypt;

public class HashUtil {

    // Generar hash de la contraseña
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // Verificar contraseña contra hash
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    // Método main para probar
    public static void main(String[] args) {
        String passwordOriginal = "1234";
        
        // Hashear
        String hash = hashPassword(passwordOriginal);
        System.out.println("🔐 Hash generado: " + hash);

        // Verificar correcta
        boolean correcta = checkPassword("miClaveSegura123", hash);
        System.out.println("¿Contraseña válida? " + correcta);

        // Verificar incorrecta
        boolean incorrecta = checkPassword("claveMala", hash);
        System.out.println("¿Contraseña válida con clave incorrecta? " + incorrecta);
    }
}
