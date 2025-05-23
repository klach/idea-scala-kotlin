package example.runner;

import example.java.ChainElement;
import example.java.Named;
import example.kotlin.KotlinButton;
import example.kotlin.KotlinChainElement;
import example.kotlin.KotlinDataWithInterface;
import example.kotlin.KotlinMenu;
import example.scala.ScalaButton;
import example.scala.ScalaChainElement;
import example.scala.ScalaMenu;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

class ClassesInterfaces implements Runnable {
    public void run() {
        extendingJava();
        toExtend();
    }

    private static void handle(List<ChainElement<Integer>> responsibilityChain, Object object) {
        for (ChainElement<Integer> chainElement : responsibilityChain) {
            if (chainElement.handles(object)) {
                System.err.printf("%s handled as %s by %s %s\n", object, chainElement.handle(object), chainElement, chainElement.describe());
                return;
            }
        }
        System.err.println("Not handled: " + object);
    }

    private void extendingJava() {
        // does not compile
//        ChainElement<Integer> plain = new example.scala.ScalaPlainChainElement();

        List<ChainElement<Integer>> responsibilityChain = Arrays.asList(
                new ScalaChainElement(),
                new KotlinChainElement()
        );

        // does not compile
//        ChainElement<Long> scalaLong = new example.scala.ScalaLongChainElement();
        ChainElement<Long> kotlinLong = new example.kotlin.KotlinLongChainElement();

        handle(responsibilityChain, "Java:A");
        handle(responsibilityChain, "Kotlin:B");
        handle(responsibilityChain, "Scala:C");



        Named[] named = new Named[]{new KotlinDataWithInterface("name", "addr", 2),
                new example.scala.ScalaCaseWithInterface("name", "addr", 3)
        };
    }

    private static class ImplementingScalaButton implements ScalaButton {
        @Override
        public boolean toggle() {
            return false;
        }

        @Override
        public String caption() {
            return "dummy caption";
        }

//        @Override public void reset() { System.err.println("I can but I won't override this"); }
    }

    private static class ImplementingKotlinButton implements KotlinButton {
        @Override
        public boolean toggle() {
            return false;
        }

        @NotNull
        @Override
        public String caption() {
            return "dummy caption";
        }

        @Override
        public void reset() {
            KotlinButton.super.reset(); // default implementation is accessible
            KotlinButton.DefaultImpls.reset(this); // thanks to '-Xjvm-default=all-compatibility'
        }
    }

    private static class ExtendingScalaMenu extends ScalaMenu {
        @Override
        public int test(int i) {
            buttons().foreach(button -> {button.reset() ; return null; });
            return buttons().size();
        }

//        @Override public void addButton(ScalaButton button) {} // won't compile - is final
    }

    private static class ExtendingKotlinMenu extends KotlinMenu {
        @Override
        public int test(int i) {
            getButtons().forEach(KotlinButton::reset);
            return getButtons().size();
        }

//        @Override public void addButton(KotlinButton button) {} // won't compile - is final
    }

    private void toExtend() {
        ScalaMenu scalaMenu = new ExtendingScalaMenu();
        scalaMenu.addButton(new ImplementingScalaButton());
        scalaMenu.test(0);

        KotlinMenu kotlinMenu = new ExtendingKotlinMenu();
        KotlinButton button = new ImplementingKotlinButton();
        if (button.caption() == null) { // IDE understands this could not be null
            return;
        }
        kotlinMenu.addButton(button);
        kotlinMenu.test(0);
    }
}
