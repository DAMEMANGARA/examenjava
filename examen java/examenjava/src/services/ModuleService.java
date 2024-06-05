import java.sql.SQLException;
import java.util.List;

import repositories.Main.ModuleRepository;

public class ModuleService {

    private ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public void addModule(String moduleName) {
        try {
            moduleRepository.addModule(moduleName);
            System.out.println("Module ajouté avec succès: " + moduleName);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du module: " + e.getMessage());
        }
    }

    public void listModules() {
        try {
            List<String> modules = moduleRepository.listModules();
            if (modules.isEmpty()) {
                System.out.println("Aucun module trouvé.");
            } else {
                System.out.println("Modules disponibles :");
                for (String module : modules) {
                    System.out.println("- " + module);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des modules: " + e.getMessage());
        }
    }

    // Autres méthodes de service pour les modules peuvent être ajoutées ici

    public static void main(String[] args) {
        // Créez une instance de ModuleRepository ou ModuleRepositoryImpl
        // Puis utilisez cette instance pour créer une instance de ModuleService
        // et appelez les méthodes addModule() et listModules() comme dans l'exemple
        // ci-dessous. Assurez-vous que votre implémentation de ModuleRepository est correcte.

        /*
        Exemple :

        ModuleRepository moduleRepository = new ModuleRepositoryImpl(); // Créez une instance appropriée de ModuleRepository
        ModuleService moduleService = new ModuleService(moduleRepository); // Utilisez ModuleRepository pour créer ModuleService

        moduleService.addModule("Java"); // Exemple d'utilisation : ajouter un module
        moduleService.addModule("Python"); // Exemple d'utilisation : ajouter un autre module

        moduleService.listModules(); // Exemple d'utilisation : lister tous les modules
        */
    }
}
