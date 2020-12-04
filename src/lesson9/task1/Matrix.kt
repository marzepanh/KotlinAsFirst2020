@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

import java.lang.IllegalArgumentException

// Урок 9: проектирование классов
// Максимальное количество баллов = 40 (без очень трудных задач = 15)

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая (2 балла)
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> = MatrixImpl(height, width, e)

/**
 * Средняя сложность (считается двумя задачами в 3 балла каждая)
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(
    override val height: Int, override val width: Int, e: E
) : Matrix<E> {

    private val map = mutableMapOf<Cell, E>()

    init {
        for (i in 0 until width) {
            for (j in 0 until height) {
                map[Cell(i, j)] = e
            }
        }
    }

    override fun get(row: Int, column: Int): E =
        map[Cell(row, column)] ?: throw IllegalArgumentException("This cell doesn't exist")

    override fun get(cell: Cell): E =
        map[cell] ?: throw IllegalArgumentException("This cell doesn't exist")

    override fun set(row: Int, column: Int, value: E) {
        map[Cell(row, column)] = value
    }

    override fun set(cell: Cell, value: E) {
        map[cell] = value
    }

    override fun equals(other: Any?) =
        other is MatrixImpl<*> &&
                height == other.height &&
                width == other.width &&
                map == other.map

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("[\n")
        for (row in 0 until height) {
            sb.append("    [")
            for (column in 0 until width) {
                sb.append(this[row, column])
                if (column != width - 1) sb.append(", ")
            }
            sb.append("],\n")
        }
        sb.append("]")
        return "$sb"
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        return result
    }
}

