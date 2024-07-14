package data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


class CountrySerializer: KSerializer<CountryRemote> {

    // Не используется
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("data.CountryRemote") {
        element<String>(NAME_FIELD)
        element<String>(FLAGS_FIELD)
    }

    override fun serialize(encoder: Encoder, value: CountryRemote) {
        // Не используется
    }

    override fun deserialize(decoder: Decoder): CountryRemote {
        val jsonDecoder = decoder as JsonDecoder

        val jsonElement = jsonDecoder.decodeJsonElement() as JsonObject
        val name = (jsonElement[NAME_FIELD] as JsonObject)["official"]?.jsonPrimitive?.content!!
        val flag = (jsonElement[FLAGS_FIELD] as JsonObject)["svg"]?.jsonPrimitive?.content!!

        return CountryRemote(
            name = name,
            flag = flag
        )
    }




}