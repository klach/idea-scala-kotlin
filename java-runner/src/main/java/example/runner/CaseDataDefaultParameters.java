package example.runner;

import example.java.ParametersReflection;
import example.kotlin.KotlinDefaults;
import example.kotlin.KotlinFile;
import example.kotlin.KotlinMutFile;
import example.kotlin.KotlinRecordFile;
import example.lombok.LombokFile;
import example.scala.ScalaDefaults;
import example.scala.ScalaFile;
import example.scala.ScalaMutFile;

class CaseDataDefaultParameters implements Runnable {
    public void run() {
        caseDataClasses();
        defaultParameters();
    }

    private void caseDataClasses() {
        ScalaFile scalaFile1 = new ScalaFile("n1", "e1", "l1", true);
        ScalaFile scalaFile2 = new ScalaFile("n1", "e1", "l1", true);
        System.err.println("Scala are equal " + scalaFile1.equals(scalaFile2));
        System.err.println("Scala hash codes " + (scalaFile1.hashCode() == scalaFile2.hashCode()));

        KotlinFile kotlinFile1 = new KotlinFile("n1", "e1", "l1", true);
        KotlinFile kotlinFile2 = new KotlinFile("n1", "e1", "l1", true);
        System.err.println("Kotlin are equal " + kotlinFile1.equals(kotlinFile2));
        System.err.println("Kotlin hash codes " + (kotlinFile1.hashCode() == kotlinFile2.hashCode()));

        scalaFile1.name();
        scalaFile1.executable();

        kotlinFile1.getName();
//        kotlinFile1.isExecutable(); // the only problem
        kotlinFile1.getExecutable();

        KotlinRecordFile kotlinRecordFile = new KotlinRecordFile("n1", "e1", "l1", true);
        kotlinRecordFile.name();
        kotlinRecordFile.executable();



//        kotlinFile1.setName("foo"); // no setters
        new ScalaMutFile("n2", "e2", "l2", false)
                .name_$eq("foo");
        new KotlinMutFile("n2", "e2", "l2", false)
                .setName("foo");

        new LombokFile("n1", "e1", "l1", true).isExecutable();
    }


    private void defaultParameters() { // also demonstrates toString()
        System.err.println(ScalaDefaults.createFile("other-name", "other-ext", "/tmp", true));
        System.err.println(ScalaDefaults.createFile$default$1()); // prints 'removeme'

        System.err.println(KotlinDefaults.createFile("other-name", "other-ext", "/tmp", true));
        System.err.println(KotlinDefaults.createFile("other-name", "other-ext"));
        System.err.println(KotlinDefaults.createFile("other-name"));
        System.err.println(KotlinDefaults.createFile());

//        System.err.println(KotlinDefaults.createFileWithoutOverloads$default("other-name", "other-ext", "/tmp", true, 0, null));
//        System.err.println(KotlinDefaults.createFileWithoutOverloads$default("other-name", "other-ext", null, false, 12, null));
//        System.err.println(KotlinDefaults.createFileWithoutOverloads$default("other-name", null, null, false, 14, null));
//        System.err.println(KotlinDefaults.createFileWithoutOverloads$default(null, null, null, false, 15, null));

        ScalaDefaults.use();
        KotlinDefaults.INSTANCE.use();


        // does not work despite https://github.com/scala/scala/pull/4735
        // currently Gradle is not able to use modern Scala compiler
        // https://github.com/gradle/gradle/issues/2158
        System.err.println("Scala parameter names: " +
                ParametersReflection.parameterNames(ScalaDefaults.class, "createFile"));
        System.err.println("Kotlin parameter names: " +
                ParametersReflection.parameterNames(KotlinDefaults.class, "createFile"));
    }
}
