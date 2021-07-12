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