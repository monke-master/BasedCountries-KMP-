package data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class FullCountrySerializer: KSerializer<FullCountryRemote> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("data.FullCountryRemote") {
        element<String>(NAME_FIELD)
        element<String>(FLAGS_FIELD)
        element<String>("capital")
    }

    override fun serialize(encoder: Encoder, value: FullCountryRemote) {
        // Not used
    }


    override fun deserialize(decoder: Decoder): FullCountryRemote {
        val jsonDecoder = decoder as JsonDecoder

        val jsonElement = jsonDecoder.decodeJsonElement() as JsonObject
        val name = (jsonElement[NAME_FIELD] as JsonObject)["official"]?.jsonPrimitive?.content!!
        val flag = (jsonElement[FLAGS_FIELD] as JsonObject)["svg"]?.jsonPrimitive?.content!!
        val capital = (jsonElement["capital"] as JsonArray).firstOrNull()?.jsonPrimitive?.content!!
        val region = (jsonElement["region"])?.jsonPrimitive?.content!!
        val area = (jsonElement["area"])?.jsonPrimitive?.content!!.toFloat()
        val population = (jsonElement["population"])?.jsonPrimitive?.content!!.toLong()
        val coatOfArms = (jsonElement["coatOfArms"] as JsonObject)["svg"]?.jsonPrimitive?.content!!

        return FullCountryRemote(
            name = name,
            flag = flag,
            capital = capital,
            region = region,
            area = area,
            population = population,
            coatOfArms = coatOfArms
        )
    }



}