import java.sql.SQLException;
import java.util.List;

import entities.Module;
import repositories.Main.ModuleRepository;

public class ModuleService {

    private ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public void ajouterModule(Module module) throws SQLException {
        moduleRepository.ajouterModule(module);
    }

    public List<Module> listerModules() throws SQLException {
        return moduleRepository.listerModules();
    }
}

