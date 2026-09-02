package com.hugosantos.palco_digital.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = "com.hugosantos.palco_digital")
public class StageModuleArchitectureTest {
  @ArchTest
  static final ArchRule stage_repositories_should_only_be_accessed_by_usecases = ArchRuleDefinition.classes()
    .that().resideInAPackage("..palco.repositories..")
    .should().onlyBeAccessed().byAnyPackage("..palco.usecases..", "..palco.repositories..");
  
}
