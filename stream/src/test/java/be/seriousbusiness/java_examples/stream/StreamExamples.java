package be.seriousbusiness.java_examples.stream;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamExamples {

    /**
     * Logger declared using the declaration pattern.
     *
     * @see <a href="http://www.slf4j.org/faq.html#declaration_pattern">SLF4J Declaration Pattern</a>
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final Collection<String> CAPITAL_NAMES = Arrays.asList("Berlin", "Brussels", "London", "Moscow", "Paris", "Washington, D.C.");

    /**
     * <p>Example of a {@link Stream} joining a collection of {@link String} Capital names into a new concatenated {@link String}. Underneath the
     * {@link StringBuilder} is used</p>
     * <p>Result: BerlinBrusselsLondonMoscowParisWashington, D.C.</p>
     *
     * @see Collectors#joining()
     */
    @Test
    public void collect_stream_to_string_using_collectors_joining() {
        LOGGER.info("Capitals: {}", CAPITAL_NAMES.stream().collect(Collectors.joining()));
    }

    /**
     * <p>Example of a {@link Stream} joining a collection of {@link String} Capital names into a new concatenated {@link String}. Each separated by
     * a delimiter. Underneath the {@link StringJoiner} is used</p>
     * <p>Result: Berlin,Brussels,London,Moscow,Paris,Washington, D.C.</p>
     *
     * @see Collectors#joining(CharSequence)
     */
    @Test
    public void collect_stream_to_string_using_collectors_joining_with_delimiter() {
        LOGGER.info("Capitals: {}", CAPITAL_NAMES.stream().collect(Collectors.joining(",")));
    }

    /**
     * <p>Example of a {@link Stream} joining a collection of {@link String} Capital names into a new concatenated {@link String}. Each separated by
     * a delimiter. The resulting {@link String} will start with given prefix and end with given suffix. Underneath the {@link StringJoiner} is
     * used.</p>
     * <p>Result: [Berlin,Brussels,London,Moscow,Paris,Washington, D.C.]</p>
     *
     * @see Collectors#joining(CharSequence, CharSequence, CharSequence)
     */
    @Test
    public void collect_stream_to_string_using_collectors_joining_with_delimiter_prefix_and_suffix() {
        LOGGER.info("Capitals: {}", CAPITAL_NAMES.stream().collect(Collectors.joining(",", "[", "]")));
    }

    /**
     * <p>Example of a {@link Stream} reducing a collection of {@link String} Capital names into a new concatenated {@link String}. Note that an
     * empty {@link String} is provided to the reducer, allowing to always guarantee a result.</p>
     * <p>Result: BerlinBrusselsLondonMoscowParisWashington, D.C.</p>
     *
     * @see Stream#reduce(Object, BinaryOperator)
     */
    @Test
    public void reduce_stream_to_string() {
        LOGGER.info("Capitals: {}", CAPITAL_NAMES.stream().reduce("", (capital1, capital2) -> capital1 + "" + capital2));
    }

    /**
     * <p>Example of a {@link Stream} reducing a collection of {@link String} Capital names into a new concatenated {@link String}. Note that an
     * empty {@link String} is provided to the reducer, allowing to always guarantee a result. Also this time we make use of {@link
     * String#concat(String)} to handle the actual concatenation.</p>
     * <p>Result: BerlinBrusselsLondonMoscowParisWashington, D.C.</p>
     *
     * @see Stream#reduce(Object, BinaryOperator)
     * @see String#concat(String)
     */
    @Test
    public void reduce_stream_to_string_using_string_concat() {
        LOGGER.info("Capitals: {}", CAPITAL_NAMES.stream().reduce("", String::concat));
    }

    /**
     * <p>Example of a {@link Stream} reducing a collection of {@link String} Capital names into a new concatenated {@link String}. Each delimited by
     * a comma. Note that in this case no empty {@link String} is provided to the reducer. As this would always return a concatenated {@link String}
     * starting with the delimited character. Which in most cases is not a preferred outcome. As a side effect, the reducer cannot guarantee a result.
     * Forcing us to handle the {@link Optional} result. In our case we resolved this by enforcing to throw an exception in case no result is
     * returned. A case we know will never occur, since we control the input stream of capital names.</p>
     * <p>Result: Berlin,Brussels,London,Moscow,Paris,Washington, D.C.</p>
     *
     * @see Stream#reduce(BinaryOperator)
     */
    @Test
    public void reduce_stream_to_string_delimited() {
        LOGGER.info("Capitals: {}", CAPITAL_NAMES.stream().reduce((capital1, capital2) -> capital1 + "," + capital2).orElseThrow());
    }

    /**
     * <p>Example of a {@link Stream} reducing a collection of {@link String} Capital names into a new concatenated {@link String}. Each delimited by
     * a comma. Note that in this case no empty {@link String} is provided to the reducer. As this would always return a concatenated {@link String}
     * starting with the delimited character. Which in most cases is not a preferred outcome. As a side effect, the reducer cannot guarantee a result.
     * Forcing us to handle the {@link Optional} result. In our case we resolved this by enforcing to throw an exception in case no result is
     * returned. A case we know will never occur, since we control the input stream of capital names. Also this time we make use of {@link
     * String#join(CharSequence, CharSequence...)} to handle the actual delimited concatenation.</p>
     * <p>Result: Berlin,Brussels,London,Moscow,Paris,Washington, D.C.</p>
     *
     * @see Stream#reduce(BinaryOperator)
     * @see String#join(CharSequence, CharSequence...)
     */
    @Test
    public void reduce_stream_to_string_delimited_using_string_join() {
        LOGGER.info("Capitals: {}", CAPITAL_NAMES.stream().reduce((capital1, capital2) -> String.join(",", capital1, capital2)).orElseThrow());
    }

    /**
     * <p>Example of the {@link String} join method alternative. Introduced in Java 8. Allowing to directly join an {@link Iterable} into a new
     * {@link String}. Underneath the {@link StringJoiner} is used.</p>
     * <p>
     * Results, with:
     * <ul>
     *     <li>delimiter: "": BerlinBrusselsLondonMoscowParisWashington, D.C.</li>
     *     <li>delimiter: ",": Berlin,Brussels,London,Moscow,Paris,Washington, D.C.</li>
     * </ul>
     * </p>
     *
     * @see String#join(CharSequence, Iterable)
     */
    @Test
    public void join_collection_to_string_using_string_join_with_delimiter() {
        LOGGER.info("Capitals: {}", String.join("", CAPITAL_NAMES));
        LOGGER.info("Capitals: {}", String.join(",", CAPITAL_NAMES));
    }

    @Test
    public void custom_collect_stream_to_string() {
        /**
         * The supplier is responsible for the creation of a new 'container' which will be used to 'collect' our processed {@link Stream} elements
         * into. In our case we create a simple {@link String} array allowed to store a single value. Which is by default already empty.
         */
        final Supplier<String[]> supplier = () -> new String[] { "" };
        /**
         * The accumulator, allows to add a stream element. In our case a capital name to a 'container'. The 'container' here, is the {@link String}
         * array initially created by the supplier defined above. We use it to concat every given capital name to the first element (index 0) of
         * this array.
         */
        final BiConsumer<String[], String> accumulator = (container, capitalName) -> container[0] += capitalName;
        /**
         * To be able to parallel process the stream. We need to provide a way to combine two result containers into one.
         * We do this by concatenating the first element of the second result container with the first element of the first container.
         * The second result container is afterwards no more of any use.
         */
        final BinaryOperator<String[]> combiner = (container1, container2) -> {
            container1[0] += container2[0];
            return container1;
        };
        /**
         * The finisher determines the result of the custom {@link Collector} implementation.
         * Here the first element of the container get's returned. Which is the concatenated result of all streamed capital names.
         */
        final Function<String[], String> finisher = container -> container[0];

        LOGGER.info("Capitals: {}", CAPITAL_NAMES.stream().collect(Collector.of(supplier, accumulator, combiner, finisher)));
    }
}
