package toolkit.declarative_components;

import java.util.function.Predicate;
import java.util.function.Supplier;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import toolkit.declarative_components.DeclarativeContractsHandler.RenderIfHandler;

public interface DeclarativeContracts {
    void mountEffect(Runnable effect, ObservableValue<?>... dependencies);

    <T> RenderIfHandler<T> renderIf(
            ObservableValue<T> observable,
            Predicate<T> predicate,
            Supplier<Node> nodeSupplier);

    RenderIfHandler<Boolean> renderIf(
            ObservableValue<Boolean> observable,
            Supplier<Node> nodeSupplier);
}
