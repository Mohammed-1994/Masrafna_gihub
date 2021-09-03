package com.example.masrafna.util

import com.example.masrafna.data.models.City

class Cities {
    companion object {
        fun getCities(): ArrayList<City> {
            return arrayListOf<City>(
                City(0, "أربيل"),
                City(1, "الأنبار"),
                City(2, "بابل"),
                City(3, "بغداد"),
                City(4, "البصرة"),
                City(5, "حلبجة"),
                City(6, "دهوك"),
                City(7, "القادسة"),
                City(8, "ديالي"),
                City(9, "ذي قار"),
                City(10, "سليمانية"),
                City(11, "صلاح الدين"),
                City(12, "كركوك"),
                City(13, "كربلاء"),
                City(14, "المثنى"),
                City(15, "ميسان"),
                City(16, "النجف"),
                City(17, "نينوى"),
                City(18, "واسط"),

                )


        }
    }


}