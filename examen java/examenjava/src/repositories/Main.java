import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.Module;
import services.ModuleService;

public class Main {
    private static Connection connection;
    private static ModuleService moduleService;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdatabase", "username", "password");

            ModuleRepositoryImpl moduleRepository = new ModuleRepositoryImpl(connection); // Utilisez la classe d'implémentation
            moduleService = new ModuleService(moduleRepository);

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("1. Ajouter un Module");
                System.out.println("2. Lister les Modules");
                System.out.println("3. Quitter");
                System.out.print("Choisissez une option: ");
                int choix = scanner.nextInt();

                switch (choix) {
                    case 1:
                        ajouterModule(scanner);
                        break;
                    case 2:
                        listerModules();
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Option invalide.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void ajouterModule(Scanner scanner) {
        System.out.print("Entrez le nom du module: ");
        String nom = scanner.next();
        Module module = new Module();
        module.setNom(nom);
        try {
            moduleService.ajouterModule(module);
            System.out.println("Module ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listerModules() {
        try {
            List<Module> modules = moduleService.listerModules();
            for (Module module : modules) {
                System.out.println("ID: " + module.getId() + ", Nom: " + module.getNom());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Interface ModuleRepository
    public interface ModuleRepository {
        void ajouterModule(Module module) throws SQLException;
        List<Module> listerModules() throws SQLException;
    }

    // Implémentation de ModuleRepository
    public static class ModuleRepositoryImpl implements ModuleRepository {
        private Connection connection;

        public ModuleRepositoryImpl(Connection connection) {
            this.connection = connection;
        }

        @Override
        public void ajouterModule(Module module) throws SQLException {
            String sql = "INSERT INTO modules (nom) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, module.getNom());
                statement.executeUpdate();
            }
        }

        @Override
        public List<Module> listerModules() throws SQLException {
            List<Module> modules = new ArrayList<>();
            String sql = "SELECT * FROM modules";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Module module = new Module();
                    module.setId(resultSet.getInt("id"));
                    module.setNom(resultSet.getString("nom"));
                    modules.add(module);
                }
            }
            return modules;
        }
    }
}
