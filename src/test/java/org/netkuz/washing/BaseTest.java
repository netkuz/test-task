package org.netkuz.washing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.netkuz.washing.model.Machine;
import org.netkuz.washing.model.Model;
import org.netkuz.washing.model.Program;
import org.netkuz.washing.model.enumeration.Detergent;
import org.netkuz.washing.model.enumeration.Mode;
import org.netkuz.washing.model.enumeration.Status;
import org.netkuz.washing.model.enumeration.Type;
import org.netkuz.washing.repository.MachineRepository;
import org.netkuz.washing.repository.ModelRepository;
import org.netkuz.washing.repository.ProgramRepository;
import org.netkuz.washing.service.MachineService;
import org.netkuz.washing.service.ModelService;
import org.netkuz.washing.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DirtiesContext
@SpringBootTest
public abstract class BaseTest extends BaseContainer {
    protected MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected ModelRepository modelRepository;
    @Autowired
    protected ModelService modelService;
    @Autowired
    protected MachineRepository machineRepository;
    @Autowired
    protected MachineService machineService;
    @Autowired
    protected ProgramRepository programRepository;
    @Autowired
    protected ProgramService programService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                    chain.doFilter(request, response);
                })
                .build();
        createModels();
        createProgram();
        createMachines();
    }

    @AfterEach
    void tearDown() {
        modelRepository.deleteAll();
    }

    void createModels() {
        List<Model> models = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var model = new Model();
            model.setId(UUID.randomUUID());
            model.setName("model_" + i);
            model.setDescription("Description model " + i);
            models.add(model);
        }
        modelRepository.saveAll(models);
    }

    void createProgram() {
        List<Program> programs = new ArrayList<>();
        modelRepository.findAll().forEach(model -> {
            for (int i = 0; i < 5; i++) {
                var program = new Program();
                program.setId(UUID.randomUUID());
                program.setName("program_" + model.getName() + "_" + i);
                program.setModel(model);
                program.setTemperature(i + 30);
                program.setWeight(i * 1000);
                program.setSpin(i * 1000);
                program.setDetergent(Detergent.AVG);
                program.setDescription("Description " + i);
                programs.add(program);
            }
        });
        programRepository.saveAll(programs);
    }

    void createMachines() {
        List<Machine> machines = new ArrayList<>();
        modelRepository.findAll().forEach(model -> {
            for (int i = 0; i < 5; i++) {
                var machine = new Machine();
                machine.setId(UUID.randomUUID());
                machine.setSerialNumber("serial_number_" + model.getName() + "_" + i);
                machine.setModel(model);
                machine.setStatus(Status.IDLE);
                machine.setMode(Mode.NONE);
                machine.setType(Type.MANUAL);
                machines.add(machine);
            }
        });
        machineRepository.saveAll(machines);
    }
}
