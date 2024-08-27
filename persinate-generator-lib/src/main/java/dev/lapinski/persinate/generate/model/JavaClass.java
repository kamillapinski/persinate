package dev.lapinski.persinate.generate.model;

public record JavaClass(
    JavaQualifiedName qualifiedName,
    JavaImports imports,
    JavaFinalType finalType,
    JavaAnnotations annotations,
    JavaRawBlock nativeHeader,
    JavaExtends extendingClass,
    JavaImplements implementedInterfaces,
    JavaClassFields classFields
) implements JavaCode {
    @Override
    public String javaCode() {
        String packageStatement = "package " + qualifiedName.packageName() + ";";
        String finalModifier = finalType.javaCode().isEmpty() ? "" : " "+ finalType.javaCode();

        String classDefinition = "public" + finalModifier + " class " + qualifiedName.simpleName() + " " + extendingClass.javaCode() + " " + implementedInterfaces.javaCode();

        String nativeHeaderCode = nativeHeader.javaCode();

        String nativeHeaderPart = nativeHeaderCode.isBlank() ? "" : (nativeHeaderCode + '\n');

        return packageStatement + "\n"
            + imports.javaCode() + "\n"
            + annotations.javaCode() + '\n'
            + nativeHeaderPart
            + classDefinition + " {\n\t"
            + classFields.javaCode().replace("\n", "\n\t") + "\n"
            + "}";
    }
}
