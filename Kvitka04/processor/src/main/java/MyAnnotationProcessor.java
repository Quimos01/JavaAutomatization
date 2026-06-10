import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("GenerateRunner")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class MyAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if(annotations.isEmpty()) {
            return false;
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateRunner.class)) {
            GenerateRunner annotation = element.getAnnotation(GenerateRunner.class);
            String newClassName = annotation.runnerName();

            try {
                JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(newClassName);

                try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
                    out.println("// Generated code!");
                    out.println("import java.lang.reflect.*;");
                    out.println();

                    out.println("@RunnerInfo(generatedBy = \"MyAnnotationProcessor-V1\")");
                    out.println("public class " + newClassName + " {");
                    out.println("    public void run() {");
                    out.println("        System.out.println(\"Class TaskExecutor: This class is created during compilation!\");");
                    out.println("    }");
                    out.println("}");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
