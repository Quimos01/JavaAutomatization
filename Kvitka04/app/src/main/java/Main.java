public class Main {
    public static void main(String[] args) {
        try {
            Class<?> generatedClass = Class.forName("TaskExecutor");

            if (generatedClass.isAnnotationPresent(RunnerInfo.class)) {
                System.out.println("Success! The class \"TaskExecutor\" has runtime-annotation");
                RunnerInfo info = generatedClass.getAnnotation(RunnerInfo.class);
                System.out.println("Information from annotation: " + info.generatedBy());

                Object runnerInstance = generatedClass.getDeclaredConstructor().newInstance();
                generatedClass.getMethod("run").invoke(runnerInstance);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
