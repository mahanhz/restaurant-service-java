package com.example.restaurant.orderservice.architecture;

import com.example.restaurant.orderservice.OrderApplication;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;


@AnalyzeClasses(packagesOf = {OrderApplication.class})
class HexagonalArchitectureTest {

    private static final String PACKAGE_APPLICATION = "..application..";
    private static final String PACKAGE_DOMAIN = "..domain..";
    private static final String PACKAGE_INFRA = "..infra..";

    @ArchTest
    public static final ArchRule domain_should_not_depend_on_application = noClasses()
        .that()
        .resideInAPackage(PACKAGE_DOMAIN)
        .should()
        .dependOnClassesThat()
        .resideInAPackage(PACKAGE_APPLICATION);

    @ArchTest
    public static final ArchRule domain_should_not_depend_on_configuration = noClasses()
        .that()
        .resideInAPackage(PACKAGE_DOMAIN)
        .should()
        .dependOnClassesThat()
        .resideInAPackage("..config..");

    @ArchTest
    public static final ArchRule domain_should_not_depend_on_infrastructure = noClasses()
        .that()
        .resideInAPackage(PACKAGE_DOMAIN)
        .should()
        .dependOnClassesThat()
        .resideInAPackage(PACKAGE_INFRA);

    @ArchTest
    public static final ArchRule domain_should_not_depend_on_spring = noClasses()
        .that()
        .resideInAPackage(PACKAGE_DOMAIN)
        .should()
        .dependOnClassesThat()
        .resideInAPackage("org.springframework..");
}