fun List<Order>.toReceipt() : String{
    return this.toString()
}

fun main() {
    val kotlinBook = Product(
        id = 1,
        name = "Kotlin in Action",
        price = 1_500.0,
        category = Category.BOOKS,
    )

    val coffee = Product(
        id = 2,
        name = "Coffee",
        price = 500.0,
        category = Category.FOOD,
    )

    val headphones = Product(
        id = 3,
        name = "Headphones",
        price = 8_000.0,
        category = Category.ELECTRONICS,
    )

    val customer = Customer(
        name = "Иван",
        email = "ivan@example.com",
        discount = 0.10,
    )


    val orders = listOf(
        Order(
            id = 1,
            customer = customer,
            items = listOf(
                OrderItem(kotlinBook, count = 2),
                OrderItem(coffee, count = 1),
            ),
            status = OrderStatus.Paid(
                transactionId = "TX-123",
            ),
        ),
    )

    println(orders.toReceipt())

}

enum class Category{
    FOOD,
    ELECTRONICS,
    BOOKS,
    OTHERS
}

sealed class OrderStatus {
    abstract val title: String

    data object Created : OrderStatus(){
        override val title: String = "создан"
    }
    data object Delivered : OrderStatus(){
        override val title: String = "доставлен"
    }
    data class Paid(val transactionId: String) : OrderStatus(){
        override val title: String = "Оплачен, транзакция: $transactionId"
    }
    data class Canceled(val reason: String) : OrderStatus(){
        override val title: String = "Отменен, причина: $reason"
    }
}
data class Product(val id: Int,
                   val name: String,
                   val price: Double,
                   val category: Category) {
}

data class Customer(val name: String,
                    val email: String,
                    val discount: Double) {
    override fun toString(): String {
        return "Покупатель: $name, <$email>"
    }
}

data class OrderItem(val product: Product,
                     val count: Int = 1){
    override fun toString(): String {
        return "${product.name} x $count = ${product.price * count}"
    }
}

data class Order(val id: Int,
                 val customer: Customer,
                 val items: List<OrderItem>,
                 val status: OrderStatus){

    override fun toString(): String {
        val result : String = buildString {
            appendLine("Заказ #$id")
            appendLine(customer.toString())
            appendLine("Статус: ${status.title}")

            var totalCost = 0.0
            for (item in items){
                appendLine(item.toString())
                totalCost += item.product.price * item.count
            }
            appendLine("Скидка: ${(customer.discount * 100).toInt()}%")
            appendLine("Итого: ${totalCost - (totalCost * customer.discount)}")
        }
        return result
    }
}