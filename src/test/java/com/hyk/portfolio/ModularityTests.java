package com.hyk.portfolio;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModularityTests {

  static final ApplicationModules modules = ApplicationModules.of(HykPortfolioApplication.class);

  @Test
  void verifiesModularStructure() {
    modules.verify();
  }

  @Test
  void printsModuleStructure() {
    modules.forEach(System.out::println);
  }

  @Test
  void writesDocumentationSnippets() {
    new Documenter(modules)
        .writeDocumentation()
        .writeIndividualModulesAsPlantUml();
  }

}
