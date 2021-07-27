## Observer (_Design Pattern_)
Exemplo de implementação do padrão de projeto _Observer_, utilizando functional (SAM) interface

Linguagem: Kotlin

## Definição

Segundo Freeman e Freeman (2004):

"**O padrão Observer:** Define a dependência um-para-muitos entre objetos para que quando um objeto mude de estado todos os seus dependentes sejam avisados e atualizados automaticamente."

## O poder da ligação leve

Para Freeman e Freeman (2004), "o padrão Observer fornece um design de objeto onde os sujeitos e os observador são levemente ligados."

Por quê?

> "A única coisa que o sujeito sabe sobre um observador é que ele implementa uma certa interface"

> "Podemos adicionar novos observadores a qualquer momento"

> "Nunca precisamos modificar o sujeito para adicionar novos tipos de observadores"

> "Podemos reutilizar sujeitos ou observadores independentemente uns dos outros"

> "Alterações no sujeito ou num observador não irão afetar o outro"

## Implementação

**Observer**

```kotlin
/** Functional (SAM) interface */
fun interface Observer {
    fun update(data: Data)
}
```

**Subject implementation**

```kotlin
class WeatherData : Subject {

    private lateinit var data: Data
    private val observers: MutableList<Observer> = mutableListOf()

    override fun registerObserver(o: Observer) {
        observers.add(o)
    }

    override fun removeObserver(o: Observer) {
        observers.remove(o)
    }

    fun setMeasurements(data: Data) {
        this.data = data
        measurementsChanged()
    }

    private fun measurementsChanged() {
        notifyObservers()
    }

    override fun notifyObservers() {
        observers.forEach {
            it.update(data)
        }
    }

}
```

**Main**

```kotlin
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {

    println("Welcome to Weather Station!")

    val weatherData = WeatherData()

    /** Registering observers */
    weatherData.registerObserver { data ->
        println("Observer 1 --- Temperature: ${data.temperature}, Humidity: ${data.humidity}")
    }
    weatherData.registerObserver { data ->
        println("Observer 2 --- Temperature: ${data.temperature}, Humidity: ${data.humidity}")
    }

    var increment = 0.0

    /** Sending changes for subject */
    while (true) {
        runBlocking {
            weatherData.setMeasurements(Data(23.3 + increment++, 70.2 - increment++))
            delay(2000)
        }
    }
}
```
