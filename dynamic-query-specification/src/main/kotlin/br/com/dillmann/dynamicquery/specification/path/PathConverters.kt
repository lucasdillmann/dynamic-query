package br.com.dillmann.dynamicquery.specification.path

import jakarta.persistence.criteria.Path
import java.util.*

/**
 * Facade for simple conversions between the application's external and internal path names
 */
object PathConverters {

    private val converters = LinkedList<PathConverter>()

    /**
     * Registers the provided [PathConverter] in the relation of available implementations that this class can use to
     * perform conversions
     *
     * @param converter Implementation to be registered and used to perform path conversions
     */
    @JvmStatic
    @Synchronized
    fun register(converter: PathConverter) {
        converters.add(converter)
        converters.sortWith { left, right -> left.priority.compareTo(right.priority) }
    }

    /**
     * Removes the provided [PathConverter] from the relation of available implementations that this class can use to
     * perform conversions
     *
     * @param converter Implementation to be removed
     */
    @JvmStatic
    fun deregister(converter: PathConverter) {
        converters.remove(converter)
    }

    /**
     * Converts the given attribute path using the first [PathConverter] implementation available that supports the
     * conversion scenario (using theirs priorities to sort them). If no implementation supports the conversion or
     * no implementation is available, attribute name will be returned unchanged.
     *
     * @param path Path to be converted
     * @param startPoint Start point of the conversion, normally the JPA's query root value
     */
    @JvmStatic
    fun convert(path: String, startPoint: Path<out Any>): String =
        converters
            .firstOrNull { it.supports(path, startPoint) }
            ?.convert(path, startPoint)
            ?: path
}
