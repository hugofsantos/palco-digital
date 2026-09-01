package com.hugosantos.palco_digital.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.hugosantos.palco_digital")
public class IdentityModuleArchitectureTest {
  @ArchTest
  static final ArchRule identity_repositories_must_be_accessed_only_by_use_cases = classes()
      .that().resideInAPackage("..identity.repositories..")
      .should().onlyBeAccessed().byAnyPackage(
          "..identity.usecases..",
          "..identity.repositories.." // Permite que repositórios acessem uns aos outros, se necessário
      );  
}
