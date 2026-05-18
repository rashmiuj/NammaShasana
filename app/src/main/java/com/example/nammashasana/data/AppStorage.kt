package com.example.nammashasana.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object AppStorage {
    private const val PREFS = "NammaPrefs_v4"

    fun getInscriptions(context: Context): List<Inscription> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val defaultJson = """[
            {"id":"1","name":"Halmidi Inscription","location":"Halmidi","dynasty":"Kadamba","king":"Kakusthavarma","description":"The Banas and Sendrakas of Kalasa, after fighting and winning against the Kekayas and Pallavas, granted the villages of Palmidi (modern-day Halmidi) and Mulivalli (modern-day Muluvalli) as a gift (land grant) to Vijayarasa.","kannadaTranslation":"ಕಳಸದ ಬಾಣರಸ ಮತ್ತು ಸೇಂದ್ರಕರು, ಕೇಕಯ ಪಲ್ಲವರೊಂದಿಗೆ ಹೋರಾಡಿ ವಿಜಯರಸನಿಗೆ ಪಲ್ಮಡಿ (ಹಲ್ಮಿಡಿ) ಮತ್ತು ಮೂಳಿವಳ್ಳಿ (ಮೂಳವಳ್ಳಿ) ಗ್ರಾಮಗಳನ್ನು ದತ್ತಿಯಾಗಿ ನೀಡಿದರು","giftOrLaw":"Land Gift","gpsCoordinates":"13.1500, 75.9833","imageUrl":"", "imageResName": "halmidi"},
            {"id":"2","name":"Badami Cave Inscription","location":"Badami","dynasty":"Chalukya","king":"Mangalesha","description":"To the good, he is good; to the sweet-natured, he is sweetness itself. But to the wicked who cause trouble, he is like a 'Viparita' (a contrary/unbeatable force) of this Kali age. He is none other than Lord Madhava (Vishnu) himself.","kannadaTranslation":"ಒಳ್ಳೆಯವರಿಗೆ ಒಳ್ಳೆಯವನು, ಮೃದು ಸ್ವಭಾವದವರಿಗೆ ಮೃದು, ಆದರೆ ಪೀಡಿಸುವ ದುಷ್ಟರಿಗೆ ನಾನು ಈ ಕಲಿಯುಗದ ವಿಪರೀತವಿದ್ದಂತೆ.","giftOrLaw":"Monument Dedication","gpsCoordinates":"15.9189, 75.6761","imageUrl":"", "imageResName": "badami"},
            {"id":"3","name":"Aihole Inscription","location":"Aihole","dynasty":"Chalukya","king":"Pulakeshin II","description":"It details the military achievements of Pulakeshin II, most notably his victory over the North Indian Emperor Harshavardhana on the banks of the Narmada River.","kannadaTranslation":"\"ರವಿಕೀರ್ತಿಯು ತನ್ನ ಕಾವ್ಯ ಕೌಶಲದಿಂದ ಕಾಳಿದಾಸ ಮತ್ತು ಭಾರವಿಗೆ ಸಮಾನವಾದ ಕೀರ್ತಿಯನ್ನು ಪಡೆದಿದ್ದಾನೆ.\"","giftOrLaw":"Literary claim","gpsCoordinates":"16.0189, 75.8828","imageUrl":"", "imageResName": "aihole"},
            {"id":"4","name":"Kappe Arabhatta","location":"Badami","dynasty":"Chalukya","king":"Unknown","description":"He is kind to the kind, sweet to the sweet, but a fierce warrior (Kaliyuga Viparita) to those who cause trouble; he is none other than Lord Vishnu himself.","kannadaTranslation":"ಸಾಧುಗೆ ಸಾಧು, ಮಧುರ ಸ್ವಭಾವದವರಿಗೆ ಮಧುರ, ಆದರೆ ತೊಂದರೆ ಕೊಡುವವರಿಗೆ ಈತ ಕಲಿಯುಗದ ವಿಪರೀತ ಶೂರ; ಈತ ಸಾಕ್ಷಾತ್ ವಿಷ್ಣುವೇ ಹೊರತು ಬೇರೆಯಲ್ಲ.","giftOrLaw":"Hero Stone","gpsCoordinates":"15.9150, 75.6760","imageUrl":"", "imageResName": "kappe_arabhatta"},
            {"id":"5","name":"Sravanabelagola Inscription","location":"Hassan","dynasty":"Ganga","king":"Rachamalla","description":"Wealth, beauty, and pleasure appear and vanish quickly like a rainbow, lightning, or mist. Realizing this truth and seeking no more life on earth, the great sage Nandisena took his vows and departed for the world of gods.","kannadaTranslation":"ಸಂಪತ್ತು, ಐಶ್ವರ್ಯ ಮತ್ತು ಸೌಂದರ್ಯವು ಕಾಮನಬಿಲ್ಲು, ಮಿಂಚು ಅಥವಾ ಮಂಜಿನಂತೆ ಬಂದು ಬೇಗನೆ ಮರೆಯಾಗುತ್ತದೆ; ಇವು ಶಾಶ್ವತವಲ್ಲ ಎಂಬ ಪರಮ ಸತ್ಯವನ್ನು ಅರಿತು, ಈ ಭೂಮಿಯಲ್ಲಿ ಇರಲು ಇಚ್ಛಿಸದ ನಂದಿಸೇನ ಮುನಿಗಳು ಸನ್ಯಾಸ ಸ್ವೀಕರಿಸಿ ದೇವಲೋಕಕ್ಕೆ ತೆರಳಿದರು.","giftOrLaw":"Poetic Nandisena Inscription","gpsCoordinates":"12.8580, 76.4855","imageUrl":"", "imageResName": "sravanabelagola"},
             {"id":"6","name":"Srival Inscription","location":"Yadgir","dynasty":"Rashtrakuta Dynasty","king":"Krishna III","description":"In 939 CE, during the reign of Rashtrakuta King Krishna III, prominent local dignitaries made cash grants for the maintenance of the Ishwara temple in Sirival","kannadaTranslation":"ಕ್ರಿ.ಶ. ೯೩೯ರಲ್ಲಿ ರಾಷ್ಟ್ರಕೂಟ ದೊರೆ ಮೂರನೇ ಕೃಷ್ಣನ ಕಾಲದಲ್ಲಿ, ಸಿರಿವಾಳದ ಗಣ್ಯರು ಈಶ್ವರ ದೇವಸ್ಥಾನಕ್ಕೆ ನಗದು ದತ್ತಿಯನ್ನು ನೀಡಿದರು.","giftOrLaw":"Donative/Votive Grant","gpsCoordinates":"16.7645, 77.1352","imageUrl":"", "imageResName": "srival"}
        ]"""

        val jsonStr = prefs.getString("inscriptions", defaultJson) ?: defaultJson
        val list = mutableListOf<Inscription>()
        try {
            val array = JSONArray(jsonStr)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(Inscription(
                    obj.getString("id"), obj.getString("name"), obj.getString("location"),
                    obj.optString("dynasty", "Unknown"), obj.optString("king", "Unknown"), obj.getString("description"),
                    obj.getString("kannadaTranslation"), obj.getString("giftOrLaw"),
                    obj.getString("gpsCoordinates"), obj.getString("imageUrl"),
                    null,
                    if (obj.has("imageResName")) obj.getString("imageResName") else null
                ))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun addInscription(context: Context, inc: Inscription) {
        val list = getInscriptions(context).toMutableList()
        list.add(inc)
        saveInscriptions(context, list)
    }

    fun updateInscription(context: Context, inc: Inscription) {
        val list = getInscriptions(context).toMutableList()
        val index = list.indexOfFirst { it.id == inc.id }
        if (index != -1) {
            list[index] = inc
            saveInscriptions(context, list)
        }
    }

    fun deleteInscription(context: Context, id: String) {
        val list = getInscriptions(context).toMutableList()
        list.removeAll { it.id == id }
        saveInscriptions(context, list)
    }

    private fun saveInscriptions(context: Context, list: List<Inscription>) {
        val array = JSONArray()
        list.forEach {
            val obj = JSONObject()
            obj.put("id", it.id); obj.put("name", it.name); obj.put("location", it.location)
            obj.put("dynasty", it.dynasty); obj.put("king", it.king); obj.put("description", it.description)
            obj.put("kannadaTranslation", it.kannadaTranslation); obj.put("giftOrLaw", it.giftOrLaw)
            obj.put("gpsCoordinates", it.gpsCoordinates); obj.put("imageUrl", it.imageUrl)
            if (it.imageResName != null) obj.put("imageResName", it.imageResName)
            array.put(obj)
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putString("inscriptions", array.toString()).apply()
    }

    fun getReports(context: Context): List<PreservationReport> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val jsonStr = prefs.getString("reports", "[]") ?: "[]"
        val list = mutableListOf<PreservationReport>()
        try {
            val array = JSONArray(jsonStr)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(PreservationReport(obj.getString("id"), obj.getString("placeName"), obj.getString("damageType"), obj.getString("description")))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun addReport(context: Context, rep: PreservationReport) {
        val list = getReports(context).toMutableList()
        list.add(0, rep) // newest first
        val array = JSONArray()
        list.forEach {
            val obj = JSONObject()
            obj.put("id", it.id); obj.put("placeName", it.placeName)
            obj.put("damageType", it.damageType); obj.put("description", it.description)
            array.put(obj)
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putString("reports", array.toString()).apply()
    }
}
