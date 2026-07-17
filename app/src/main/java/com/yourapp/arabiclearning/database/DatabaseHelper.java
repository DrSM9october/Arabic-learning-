package com.yourapp.arabiclearning.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.yourapp.arabiclearning.models.Phrase;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "arabic_phrases.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_PHRASES = "phrases";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PHRASES + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "arabic_text TEXT," +
                "persian_text TEXT," +
                "phonetic TEXT," +
                "dialect TEXT," +
                "gender TEXT," +
                "category TEXT," +
                "difficulty TEXT," +
                "is_favorite INTEGER DEFAULT 0" +
                ")";
        db.execSQL(createTable);
        insertAllPhrases(db);
    }

    private void insertAllPhrases(SQLiteDatabase db) {
        // ==========================================
        // بخش اول: لهجه عراقی (۱۴۰ جمله)
        // ==========================================
        String[][] iraqi = {
            // ----- احوالپرسی (۲۰ جمله) -----
            {"هَلا", "سلام", "hala", "iraqi", "neutral", "greeting", "beginner"},
            {"هَلا بِيكَ", "سلام بر تو (مذکر)", "hala bik", "iraqi", "male", "greeting", "beginner"},
            {"هَلا بِيكي", "سلام بر تو (مونث)", "hala biki", "iraqi", "female", "greeting", "beginner"},
            {"شَلونَكَ؟", "چطوری؟ (مذکر)", "shlonak", "iraqi", "male", "greeting", "beginner"},
            {"شَلونَچِ؟", "چطوری؟ (مونث)", "shlonach", "iraqi", "female", "greeting", "beginner"},
            {"شَلونكُم؟", "چطورید؟", "shlonakum", "iraqi", "neutral", "greeting", "beginner"},
            {"زَين", "خوبم (مذکر)", "zayn", "iraqi", "male", "greeting", "beginner"},
            {"زَينَة", "خوبم (مونث)", "zayna", "iraqi", "female", "greeting", "beginner"},
            {"زَينين", "خوبیم", "zaynin", "iraqi", "neutral", "greeting", "beginner"},
            {"شَكُو ماكُو؟", "چه خبر؟", "shaku maku", "iraqi", "neutral", "greeting", "beginner"},
            {"ماكُو شَي جَديد", "چیز جدیدی نیست", "maku shi jdid", "iraqi", "neutral", "greeting", "intermediate"},
            {"الحَمْدُلله", "خدا را شکر", "alhamdulillah", "iraqi", "neutral", "greeting", "beginner"},
            {"الله بِالخَير", "خوش آمدی", "allah bilkhair", "iraqi", "neutral", "greeting", "beginner"},
            {"أهلين", "خوش آمدی", "ahlein", "iraqi", "neutral", "greeting", "beginner"},
            {"أهلين وَسَهلين", "خوش آمدی (محترمانه)", "ahlein w sahlein", "iraqi", "neutral", "greeting", "intermediate"},
            {"شَكو؟", "چه شده؟", "shaku", "iraqi", "neutral", "greeting", "beginner"},
            {"ماكو شَي", "هیچی نیست", "maku shi", "iraqi", "neutral", "greeting", "beginner"},
            {"كُل شَي زَين", "همه چی خوبه", "kul shi zayn", "iraqi", "neutral", "greeting", "intermediate"},
            {"إنتَ شلونَكَ؟", "تو چطوری؟ (مذکر)", "inta shlonak", "iraqi", "male", "greeting", "beginner"},
            {"إنتِ شلونَچِ؟", "تو چطوری؟ (مونث)", "inti shlonach", "iraqi", "female", "greeting", "beginner"},
            
            // ----- معرفی (۱۵ جمله) -----
            {"شِنُو إسْمَكَ؟", "اسمت چیه؟ (مذکر)", "shinu ismak", "iraqi", "male", "introduction", "beginner"},
            {"شِنُو إسْمَچِ؟", "اسمت چیه؟ (مونث)", "shinu ismach", "iraqi", "female", "introduction", "beginner"},
            {"أنا إسْمي...", "اسم من ... است", "ana ismi", "iraqi", "neutral", "introduction", "beginner"},
            {"شِنُو إسْم عائِلَتَك؟", "نام خانوادگیت چیست؟", "shinu ism a'iltak", "iraqi", "neutral", "introduction", "intermediate"},
            {"مِن وِين إنتَ؟", "اهل کجایی؟ (مذکر)", "min wayn inta", "iraqi", "male", "introduction", "beginner"},
            {"مِن وِين إنتي؟", "اهل کجایی؟ (مونث)", "min wayn inti", "iraqi", "female", "introduction", "beginner"},
            {"أنا مِن بَغداد", "من بغداد هستم", "ana min baghdad", "iraqi", "neutral", "introduction", "beginner"},
            {"تَشَرَّفنا", "از دیدنت خوشحالم", "titsharrafna", "iraqi", "neutral", "introduction", "intermediate"},
            {"أنا سَعيد بِشوفَتك", "خوشحالم که می‌بینمت", "ana sa'id bishoftak", "iraqi", "neutral", "introduction", "intermediate"},
            {"شِنُو تِشتَغِل؟", "چه کار می‌کنی؟", "shinu tishtighil", "iraqi", "neutral", "introduction", "intermediate"},
            {"أنا مُوَظَّف", "کارمند هستم", "ana muwazzaf", "iraqi", "neutral", "introduction", "intermediate"},
            {"أنا طالِب", "دانشجو هستم", "ana talib", "iraqi", "neutral", "introduction", "intermediate"},
            {"أنا مُعَلِّمَة", "معلم هستم (مونث)", "ana mu'allima", "iraqi", "female", "introduction", "intermediate"},
            {"أنا مُهَندِس", "مهندس هستم", "ana muhandis", "iraqi", "neutral", "introduction", "intermediate"},
            {"أنا طَبيب", "دکتر هستم", "ana tabib", "iraqi", "neutral", "introduction", "intermediate"},
            
            // ----- سوالات روزمره (۲۵ جمله) -----
            {"وَيْن رايِحْ؟", "کجا می‌ری؟ (مذکر)", "wayn rayih", "iraqi", "male", "daily", "beginner"},
            {"وَيْن رايحَة؟", "کجا می‌ری؟ (مونث)", "wayn rayha", "iraqi", "female", "daily", "beginner"},
            {"وَيْن تَجي؟", "کجا می‌آیی؟ (مذکر)", "wayn tiji", "iraqi", "male", "daily", "beginner"},
            {"وَيْن تَجين؟", "کجا می‌آیی؟ (مونث)", "wayn tijin", "iraqi", "female", "daily", "beginner"},
            {"شَكَد السّاعَة؟", "ساعت چند است؟", "shgad is-saa'a", "iraqi", "neutral", "daily", "beginner"},
            {"شَكَد صار الوَقت؟", "ساعت چند شده؟", "shgad sar il-waqt", "iraqi", "neutral", "daily", "intermediate"},
            {"وَيْن الحَمّام؟", "دستشویی کجاست؟", "wayn il-hammaam", "iraqi", "neutral", "daily", "beginner"},
            {"وَيْن المَطار؟", "فرودگاه کجاست؟", "wayn il-matar", "iraqi", "neutral", "daily", "beginner"},
            {"وَيْن الفُندُق؟", "هتل کجاست؟", "wayn il-funduq", "iraqi", "neutral", "daily", "beginner"},
            {"وَيْن المَطعَم؟", "رستوران کجاست؟", "wayn il-mat'am", "iraqi", "neutral", "daily", "beginner"},
            {"وَيْن المُستَشفى؟", "بیمارستان کجاست؟", "wayn il-mustashfa", "iraqi", "neutral", "daily", "intermediate"},
            {"وَيْن المَدرَسَة؟", "مدرسه کجاست؟", "wayn il-madrasa", "iraqi", "neutral", "daily", "intermediate"},
            {"وَيْن الجامِعَة؟", "دانشگاه کجاست؟", "wayn il-jami'a", "iraqi", "neutral", "daily", "intermediate"},
            {"مُمْكِن تَساعِدني؟", "می‌توانی کمکم کنی؟", "mumkin tsa'idni", "iraqi", "neutral", "daily", "beginner"},
            {"مُمْكِن تِحكِي بِبُطء؟", "می‌توانی آهسته صحبت کنی؟", "mumkin tihki bibut'", "iraqi", "neutral", "daily", "intermediate"},
            {"ما فَهِمْت", "متوجه نشدم", "maa fihimt", "iraqi", "neutral", "daily", "beginner"},
            {"أعِيد مَرَّة ثانِيَة", "دوباره بگو", "a'id marra thaniya", "iraqi", "neutral", "daily", "intermediate"},
            {"شَكَد العُمْر؟", "چند سالته؟", "shgad il-'umr", "iraqi", "neutral", "daily", "intermediate"},
            {"عُمْري ٢٥ سَنَة", "۲۵ سالمه", "'umri 25 sana", "iraqi", "neutral", "daily", "intermediate"},
            {"مُتَزَوِّج؟", "متاهلی؟", "mutazawwij", "iraqi", "neutral", "daily", "intermediate"},
            {"عِندَك أولاد؟", "بچه داری؟", "'indak awlad", "iraqi", "neutral", "daily", "intermediate"},
            {"عِندي وَلَد وَبِنت", "یک پسر و یک دختر دارم", "'indi walad w bint", "iraqi", "neutral", "daily", "intermediate"},
            {"شِنُو السَّبَب؟", "دلیلش چیست؟", "shinu is-sabab", "iraqi", "neutral", "daily", "intermediate"},
            {"شِنُو الحَل؟", "راه حل چیست؟", "shinu il-hal", "iraqi", "neutral", "daily", "intermediate"},
            {"شِنُو رَأيَكَ؟", "نظرت چیه؟", "shinu ra'yak", "iraqi", "neutral", "daily", "intermediate"},
            
            // ----- خرید و پول (۲۰ جمله) -----
            {"شَكَد هَذا؟", "این چند است؟", "shgad hatha", "iraqi", "neutral", "shopping", "beginner"},
            {"شَكَد ثَمَن هَذا؟", "قیمت این چقدر است؟", "shgad thaman hatha", "iraqi", "neutral", "shopping", "intermediate"},
            {"كُلِش غالي!", "خیلی گران است!", "kulesh ghali", "iraqi", "neutral", "shopping", "beginner"},
            {"هَذا رَخيص", "این ارزان است", "hatha rakhiis", "iraqi", "neutral", "shopping", "intermediate"},
            {"عِندَكُم شَي أرخَص؟", "چیز ارزان‌تری دارید؟", "'indkum shi arkhas", "iraqi", "neutral", "shopping", "intermediate"},
            {"بِدي أشتري هَذا", "می‌خواهم این را بخرم", "bidi ashtri hatha", "iraqi", "neutral", "shopping", "intermediate"},
            {"كَم دينار؟", "چند دینار؟", "kam dinar", "iraqi", "neutral", "shopping", "intermediate"},
            {"أعطيني تَخفيض", "به من تخفیف بده", "a'tini takhfiid", "iraqi", "neutral", "shopping", "advanced"},
            {"مُمْكِن أدفَع بِالبِطاقَة؟", "می‌توانم با کارت پرداخت کنم؟", "mumkin adfa' bilbitaqa", "iraqi", "neutral", "shopping", "intermediate"},
            {"قَبول نَقدي", "نقدی قبول می‌کنید؟", "maqbul naqdi", "iraqi", "neutral", "shopping", "intermediate"},
            {"وَيْن السّوق؟", "بازار کجاست؟", "wayn is-suq", "iraqi", "neutral", "shopping", "beginner"},
            {"وَيْن المَحَل؟", "مغازه کجاست؟", "wayn il-mahal", "iraqi", "neutral", "shopping", "intermediate"},
            {"شَكَد المَصاريف؟", "هزینه‌ها چقدر است؟", "shgad il-masarif", "iraqi", "neutral", "shopping", "advanced"},
            {"هَذا غالي كَثير", "این خیلی گران است", "hatha ghali kathir", "iraqi", "neutral", "shopping", "intermediate"},
            {"أريد أرجِع هَذا", "می‌خواهم این را برگردانم", "urid arji' hatha", "iraqi", "neutral", "shopping", "advanced"},
            {"مُمْكِن أُجَرِّب هَذا؟", "می‌توانم این را امتحان کنم؟", "mumkin ujarrib hatha", "iraqi", "neutral", "shopping", "intermediate"},
            {"ما عِندي فُلوس", "پول ندارم", "ma 'indi fulus", "iraqi", "neutral", "shopping", "intermediate"},
            {"كَيف أدفَع؟", "چطور پرداخت کنم؟", "kayf adfa'", "iraqi", "neutral", "shopping", "intermediate"},
            {"هَل تَقبَل شيك؟", "چک قبول می‌کنید؟", "hal taqbal shik", "iraqi", "neutral", "shopping", "advanced"},
            {"أين مَكتَب الصّرافَة؟", "صرافی کجاست؟", "ayn maktab is-sarafa", "iraqi", "neutral", "shopping", "advanced"},
            
            // ----- غذا و رستوران (۲۵ جمله) -----
            {"بِدي آكُل", "می‌خواهم بخورم", "bidi aakul", "iraqi", "neutral", "food", "beginner"},
            {"بِدي أشْرَب", "می‌خواهم بنوشم", "bidi ashrab", "iraqi", "neutral", "food", "beginner"},
            {"شِنُو عِندَكُم؟", "چه دارید؟", "shinu 'indkum", "iraqi", "neutral", "food", "beginner"},
            {"شِنُو الأكِل اليَوم؟", "غذای امروز چیست؟", "shinu il-akil il-yom", "iraqi", "neutral", "food", "intermediate"},
            {"بِدي دَجاج", "مرغ می‌خواهم", "bidi dijaj", "iraqi", "neutral", "food", "intermediate"},
            {"بِدي سَمَك", "ماهی می‌خواهم", "bidi samak", "iraqi", "neutral", "food", "intermediate"},
            {"بِدي لَحْم", "گوشت می‌خواهم", "bidi lahm", "iraqi", "neutral", "food", "intermediate"},
            {"بِدي خُضْرَوات", "سبزی می‌خواهم", "bidi khudrawat", "iraqi", "neutral", "food", "intermediate"},
            {"بِدي فَواكِه", "میوه می‌خواهم", "bidi fawakih", "iraqi", "neutral", "food", "intermediate"},
            {"بِدي ماء", "آب می‌خواهم", "bidi ma'a", "iraqi", "neutral", "food", "beginner"},
            {"بِدي شاي", "چای می‌خواهم", "bidi shay", "iraqi", "neutral", "food", "beginner"},
            {"بِدي قَهْوَة", "قهوه می‌خواهم", "bidi qahwa", "iraqi", "neutral", "food", "beginner"},
            {"بِدي عَصير", "آبمیوه می‌خواهم", "bidi 'asir", "iraqi", "neutral", "food", "intermediate"},
            {"غَدا", "ناهار", "ghada", "iraqi", "neutral", "food", "beginner"},
            {"عَشاء", "شام", "'asha", "iraqi", "neutral", "food", "beginner"},
            {"فُطور", "صبحانه", "futur", "iraqi", "neutral", "food", "beginner"},
            {"أكِل لَذيذ", "غذا خوشمزه است", "akil laziz", "iraqi", "neutral", "food", "intermediate"},
            {"شُكراً عَلَى الأكِل", "ممنون برای غذا", "shukran 'ala il-akil", "iraqi", "neutral", "food", "intermediate"},
            {"صَحتَين", "نوش جان", "sahtin", "iraqi", "neutral", "food", "beginner"},
            {"بِالهَنا وَالشِّفا", "نوش جان", "bil-hana wish-shifa", "iraqi", "neutral", "food", "intermediate"},
            {"أريد قائِمَة الطَّعام", "منو می‌خواهم", "urid qa'imat it-ta'am", "iraqi", "neutral", "food", "intermediate"},
            {"ما هُوَ طَعام اليَوم؟", "غذای امروز چیست؟", "ma huwa ta'am il-yom", "iraqi", "neutral", "food", "intermediate"},
            {"هَل عِندَكُم أكل نَباتي؟", "غذای گیاهی دارید؟", "hal 'indkum akil nabati", "iraqi", "neutral", "food", "advanced"},
            {"أريد فاتورَة", "صورت‌حساب می‌خواهم", "urid fatura", "iraqi", "neutral", "food", "intermediate"},
            {"الأكِل كان لَذيذاً", "غذا خوشمزه بود", "il-akil kan lazizan", "iraqi", "neutral", "food", "intermediate"},
            
            // ----- مسیریابی (۱۵ جمله) -----
            {"دور", "بپیچ", "door", "iraqi", "neutral", "directions", "beginner"},
            {"يَسار", "چپ", "yasar", "iraqi", "neutral", "directions", "beginner"},
            {"يَمين", "راست", "yameen", "iraqi", "neutral", "directions", "beginner"},
            {"شُبْكَة", "مستقیم", "shubka", "iraqi", "neutral", "directions", "beginner"},
            {"دور عَلَى اليَسار", "به چپ بپیچ", "door 'ala il-yasar", "iraqi", "neutral", "directions", "intermediate"},
            {"دور عَلَى اليَمين", "به راست بپیچ", "door 'ala il-yameen", "iraqi", "neutral", "directions", "intermediate"},
            {"أمشي شُبْكَة", "مستقیم برو", "amshi shubka", "iraqi", "neutral", "directions", "intermediate"},
            {"وَيْن شارِع الرَّشيد؟", "خیابان الرشید کجاست؟", "wayn shari' il-rashid", "iraqi", "neutral", "directions", "advanced"},
            {"كَم المَسافَة؟", "چقدر فاصله است؟", "kam il-masafa", "iraqi", "neutral", "directions", "intermediate"},
            {"المَسافَة بَعيدَة", "فاصله دور است", "il-masafa ba'ida", "iraqi", "neutral", "directions", "intermediate"},
            {"المَسافَة قَريبَة", "فاصله نزدیک است", "il-masafa qariba", "iraqi", "neutral", "directions", "intermediate"},
            {"كَيف أوصَل إلَى...؟", "چطور به ... برسم؟", "kayf awsal ila...", "iraqi", "neutral", "directions", "intermediate"},
            {"هَل هُوَ بَعيد؟", "آیا دور است؟", "hal huwa ba'id", "iraqi", "neutral", "directions", "intermediate"},
            {"أين مَوقِف السَّيارات؟", "پارکینگ کجاست؟", "ayn mawqif is-sayyarat", "iraqi", "neutral", "directions", "advanced"},
            {"خُذ هَذا الشّارِع", "این خیابان را برو", "khud hatha is-shari'", "iraqi", "neutral", "directions", "intermediate"},
            
            // ----- خانواده (۱۵ جمله) -----
            {"أبوكَ", "پدرت (مذکر)", "abook", "iraqi", "male", "family", "beginner"},
            {"أبوجِ", "پدرت (مونث)", "abooj", "iraqi", "female", "family", "beginner"},
            {"أمكَ", "مادرت (مذکر)", "ummak", "iraqi", "male", "family", "beginner"},
            {"أمجِ", "مادرت (مونث)", "ummij", "iraqi", "female", "family", "beginner"},
            {"أخوانَكَ", "برادرانت", "ikhwanak", "iraqi", "neutral", "family", "intermediate"},
            {"خَواتَكَ", "خواهرانت", "khawatak", "iraqi", "neutral", "family", "intermediate"},
            {"عائِلَتي كَبيرَة", "خانواده‌ام بزرگ است", "'a'ilti kabira", "iraqi", "neutral", "family", "intermediate"},
            {"عِندي أخ واحِد", "یک برادر دارم", "'indi akh wahid", "iraqi", "neutral", "family", "intermediate"},
            {"عِندي أخت واحِدَة", "یک خواهر دارم", "'indi ukht wahida", "iraqi", "neutral", "family", "intermediate"},
            {"جِدي", "پدربزرگم", "jiddi", "iraqi", "neutral", "family", "intermediate"},
            {"جِدَّتي", "مادربزرگم", "jiddati", "iraqi", "neutral", "family", "intermediate"},
            {"عَمي", "عمویم", "'ammi", "iraqi", "neutral", "family", "advanced"},
            {"خالي", "داییم", "khali", "iraqi", "neutral", "family", "advanced"},
            {"إبن عَمي", "پسر عمویم", "ibn 'ammi", "iraqi", "neutral", "family", "advanced"},
            {"بِنْت عَمي", "دختر عمویم", "bint 'ammi", "iraqi", "neutral", "family", "advanced"},
            
            // ----- اصطلاحات محاوره‌ای (۲۰ جمله) -----
            {"قَلبي", "عزیزم (قلب من)", "galbi", "iraqi", "neutral", "expression", "intermediate"},
            {"روحي", "جانم", "ruhi", "iraqi", "neutral", "expression", "intermediate"},
            {"عَلى راسي", "بالای چشم (روی سرم)", "ala raasi", "iraqi", "neutral", "expression", "intermediate"},
            {"أروح لَكَ فَدوَة", "فدایت شوم (مذکر)", "aroh lak fadwa", "iraqi", "male", "expression", "advanced"},
            {"أروح لَج فَدوَة", "فدایت شوم (مونث)", "aroh lich fadwa", "iraqi", "female", "expression", "advanced"},
            {"خَلي نَسولِف", "بگذار حرف بزنیم", "khali nsolef", "iraqi", "neutral", "expression", "intermediate"},
            {"أشوفَك باچِر", "فردا می‌بینمت (مذکر)", "ashoofak bachr", "iraqi", "male", "expression", "beginner"},
            {"أشوفَچ باچِر", "فردا می‌بینمت (مونث)", "ashoofach bachr", "iraqi", "female", "expression", "beginner"},
            {"خَلَص", "تمام شد", "khalas", "iraqi", "neutral", "expression", "beginner"},
            {"خَلَصنا", "تمام کردیم", "khalasna", "iraqi", "neutral", "expression", "intermediate"},
            {"روح", "برو", "rooh", "iraqi", "neutral", "expression", "beginner"},
            {"تَعال", "بیا", "ta'aal", "iraqi", "neutral", "expression", "beginner"},
            {"تَعال هِنا", "بیا اینجا", "ta'aal hina", "iraqi", "neutral", "expression", "intermediate"},
            {"شَباب", "بچه‌ها", "shbab", "iraqi", "neutral", "expression", "beginner"},
            {"إنتَ", "تو (مذکر)", "inta", "iraqi", "male", "expression", "beginner"},
            {"إنتِ", "تو (مونث)", "inti", "iraqi", "female", "expression", "beginner"},
            {"إحنا", "ما", "ihna", "iraqi", "neutral", "expression", "intermediate"},
            {"إنتم", "شما", "intum", "iraqi", "neutral", "expression", "intermediate"},
            {"هُمَّ", "آنها", "humma", "iraqi", "neutral", "expression", "intermediate"},
            {"أوكي", "باشه", "oki", "iraqi", "neutral", "expression", "beginner"},
            
            // ----- عشق و احساسات (۱۵ جمله) -----
            {"هَواي أحِبَّك", "خیلی دوستت دارم (مذکر)", "hwaya a7bek", "iraqi", "male", "love", "intermediate"},
            {"هَواي أحِبَّچ", "خیلی دوستت دارم (مونث)", "hwaya a7bech", "iraqi", "female", "love", "intermediate"},
            {"أشتَقتلَك", "دلم برایت تنگ شده (مذکر)", "ishtaqtilak", "iraqi", "male", "love", "intermediate"},
            {"أشتَقتلَچ", "دلم برایت تنگ شده (مونث)", "ishtaqtilich", "iraqi", "female", "love", "intermediate"},
            {"تَعبان", "خسته‌ام (مذکر)", "ta'ban", "iraqi", "male", "emotion", "beginner"},
            {"تَعبانَة", "خسته‌ام (مونث)", "ta'bana", "iraqi", "female", "emotion", "beginner"},
            {"زَعْلان", "ناراحتم (مذکر)", "za'lan", "iraqi", "male", "emotion", "intermediate"},
            {"زَعْلانَة", "ناراحتم (مونث)", "za'lana", "iraqi", "female", "emotion", "intermediate"},
            {"فَرحان", "خوشحالم (مذکر)", "farhan", "iraqi", "male", "emotion", "intermediate"},
            {"فَرحانَة", "خوشحالم (مونث)", "farhana", "iraqi", "female", "emotion", "intermediate"},
            {"خايف", "می‌ترسم (مذکر)", "khayif", "iraqi", "male", "emotion", "intermediate"},
            {"خايفَة", "می‌ترسم (مونث)", "khayfa", "iraqi", "female", "emotion", "intermediate"},
            {"نَعسان", "خوابم می‌آید (مذکر)", "na'san", "iraqi", "male", "emotion", "intermediate"},
            {"نَعسانَة", "خوابم می‌آید (مونث)", "na'sana", "iraqi", "female", "emotion", "intermediate"},
            {"جوعان", "گرسنه‌ام (مذکر)", "ju'an", "iraqi", "male", "emotion", "intermediate"},
        };

        // ==========================================
        // بخش دوم: لهجه لبنانی (۱۳۰ جمله)
        // ==========================================
        String[][] lebanese = {
            // ----- احوالپرسی (۲۰ جمله) -----
            {"مَرحَبا", "سلام", "mar7aba", "lebanese", "neutral", "greeting", "beginner"},
            {"مَرحَبتَين", "سلام (دوبار)", "mar7abtayn", "lebanese", "neutral", "greeting", "beginner"},
            {"أهلاً وَسَهلاً", "خوش آمدی", "ahla w sahla", "lebanese", "neutral", "greeting", "beginner"},
            {"كَيفَكَ؟", "چطوری؟ (مذکر)", "kifak", "lebanese", "male", "greeting", "beginner"},
            {"كَيفَكِ؟", "چطوری؟ (مونث)", "kifek", "lebanese", "female", "greeting", "beginner"},
            {"كَيفكُن؟", "چطورید؟", "kifkon", "lebanese", "neutral", "greeting", "beginner"},
            {"مَنيح", "خوبم (مذکر)", "mni7", "lebanese", "male", "greeting", "beginner"},
            {"مَنيحَة", "خوبم (مونث)", "mni7a", "lebanese", "female", "greeting", "beginner"},
            {"مَنيحين", "خوبیم", "mni7in", "lebanese", "neutral", "greeting", "beginner"},
            {"شو أخبارَكَ؟", "چه خبر؟ (مذکر)", "shu akhbarak", "lebanese", "male", "greeting", "beginner"},
            {"شو أخبارَكِ؟", "چه خبر؟ (مونث)", "shu akhbarik", "lebanese", "female", "greeting", "beginner"},
            {"شو أخبار؟", "چه خبر؟", "shu akhbar", "lebanese", "neutral", "greeting", "beginner"},
            {"كِلُّو تَمام؟", "همه چی خوبه؟", "killo tamam", "lebanese", "neutral", "greeting", "intermediate"},
            {"الحَمدُلله عَلى السَّلامَة", "خدا را شکر برای سلامتی", "alhamdulillah 'al-sSalame", "lebanese", "neutral", "greeting", "advanced"},
            {"يِسْلَمُو", "سلامت باشی", "yislamo", "lebanese", "neutral", "greeting", "intermediate"},
            {"شُو مَعَك؟", "چی داری؟", "shu ma'ak", "lebanese", "neutral", "greeting", "intermediate"},
            {"شُو صار؟", "چه شد؟", "shu sar", "lebanese", "neutral", "greeting", "intermediate"},
            {"كُل شَي مَنيح؟", "همه چی خوبه؟", "kul shi mni7", "lebanese", "neutral", "greeting", "intermediate"},
            {"إنتَ مَنيح؟", "تو خوبی؟ (مذکر)", "inta mni7", "lebanese", "male", "greeting", "beginner"},
            {"إنتِ مَنيحَة؟", "تو خوبی؟ (مونث)", "inti mni7a", "lebanese", "female", "greeting", "beginner"},
            
            // ----- معرفی (۱۵ جمله) -----
            {"شُو إسْمَكَ؟", "اسمت چیه؟ (مذکر)", "shu ismak", "lebanese", "male", "introduction", "beginner"},
            {"شُو إسْمَكِ؟", "اسمت چیه؟ (مونث)", "shu ismik", "lebanese", "female", "introduction", "beginner"},
            {"أنا إسْمي...", "اسم من ... است", "ana ismi", "lebanese", "neutral", "introduction", "beginner"},
            {"مِن وِين إنتَ؟", "اهل کجایی؟ (مذکر)", "men wayn inta", "lebanese", "male", "introduction", "beginner"},
            {"مِن وِين إنتِ؟", "اهل کجایی؟ (مونث)", "men wayn inti", "lebanese", "female", "introduction", "beginner"},
            {"أنا مِن بَيروت", "من بیروت هستم", "ana men bayrut", "lebanese", "neutral", "introduction", "beginner"},
            {"تَشَرَّفنا", "از دیدنت خوشحالم", "tcharrafna", "lebanese", "neutral", "introduction", "intermediate"},
            {"أنا سَعيد بِشوفَتك", "خوشحالم که می‌بینمت", "ana sa'id bishoftak", "lebanese", "neutral", "introduction", "intermediate"},
            {"شُو بِتِشتَغِل؟", "چه کار می‌کنی؟", "shu bitishtighil", "lebanese", "neutral", "introduction", "intermediate"},
            {"أنا مُوَظَّف", "کارمند هستم", "ana muwazzaf", "lebanese", "neutral", "introduction", "intermediate"},
            {"أنا طالِب", "دانشجو هستم", "ana talib", "lebanese", "neutral", "introduction", "intermediate"},
            {"أنا مُعَلِّمَة", "معلم هستم (مونث)", "ana mu'allima", "lebanese", "female", "introduction", "intermediate"},
            {"أنا مُهَندِس", "مهندس هستم", "ana muhandis", "lebanese", "neutral", "introduction", "intermediate"},
            {"أنا دَكتُور", "دکتر هستم", "ana daktor", "lebanese", "neutral", "introduction", "intermediate"},
            {"شُو عَمَلَك؟", "شغلت چیه؟", "shu 'amalak", "lebanese", "neutral", "introduction", "intermediate"},
            
            // ----- سوالات روزمره (۲۰ جمله) -----
            {"شُو عَم تَعمَل؟", "چیکار می‌کنی؟ (مذکر)", "shu 3am ta3mil", "lebanese", "male", "daily", "beginner"},
            {"شُو عَم تَعمَلي؟", "چیکار می‌کنی؟ (مونث)", "shu 3am ta3mli", "lebanese", "female", "daily", "beginner"},
            {"شُو عَم تَعمَلو؟", "چیکار می‌کنید؟", "shu 3am ta3mlo", "lebanese", "neutral", "daily", "intermediate"},
            {"وَيْن رايِح؟", "کجا می‌ری؟ (مذکر)", "wayn rayih", "lebanese", "male", "daily", "beginner"},
            {"وَيْن رايحَة؟", "کجا می‌ری؟ (مونث)", "wayn rayha", "lebanese", "female", "daily", "beginner"},
            {"قَدِّيش السّاعَة؟", "ساعت چند است؟", "addaysh is-sa3a", "lebanese", "neutral", "daily", "beginner"},
            {"قَدِّيش صار الوَقت؟", "ساعت چند شده؟", "addaysh sar il-wa't", "lebanese", "neutral", "daily", "intermediate"},
            {"وَيْن الحَمّام؟", "دستشویی کجاست؟", "wayn il-7emmem", "lebanese", "neutral", "daily", "beginner"},
            {"وَيْن المَطار؟", "فرودگاه کجاست؟", "wayn il-matar", "lebanese", "neutral", "daily", "beginner"},
            {"وَيْن الفُندُق؟", "هتل کجاست؟", "wayn il-fundu'", "lebanese", "neutral", "daily", "beginner"},
            {"وَيْن المَطعَم؟", "رستوران کجاست؟", "wayn il-mat'am", "lebanese", "neutral", "daily", "beginner"},
            {"وَيْن المُستَشفى؟", "بیمارستان کجاست؟", "wayn il-mustashfa", "lebanese", "neutral", "daily", "intermediate"},
            {"وَيْن المَدرَسَة؟", "مدرسه کجاست؟", "wayn il-madrasa", "lebanese", "neutral", "daily", "intermediate"},
            {"مُمْكِن تَساعِدني؟", "می‌توانی کمکم کنی؟", "mumkin tsaa3idni", "lebanese", "neutral", "daily", "beginner"},
            {"مُمْكِن تِحكِي شوَي شوَي؟", "می‌توانی کمی آهسته صحبت کنی؟", "mumkin tihki shway shway", "lebanese", "neutral", "daily", "intermediate"},
            {"ما فَهِمْت", "متوجه نشدم", "ma fhimt", "lebanese", "neutral", "daily", "beginner"},
            {"أعِيد مَرَّة تانِيَة", "دوباره بگو", "a'id marra tanya", "lebanese", "neutral", "daily", "intermediate"},
            {"شُو مَعنى هَالكَلِمَة؟", "معنی این کلمه چیست؟", "shu ma'na hal-kelme", "lebanese", "neutral", "daily", "intermediate"},
            {"كَم عُمرَكَ؟", "چند سالته؟", "kam 'omrak", "lebanese", "neutral", "daily", "intermediate"},
            {"عُمْري ٢٥ سَنَة", "۲۵ سالمه", "'omri 25 sana", "lebanese", "neutral", "daily", "intermediate"},
            
            // ----- خرید و پول (۱۵ جمله) -----
            {"قَدِّيش هَيدا؟", "این چند است؟", "addaysh hayda", "lebanese", "neutral", "shopping", "beginner"},
            {"قَدِّيش سِعْرُو؟", "قیمتش چقدر است؟", "addaysh sa'ro", "lebanese", "neutral", "shopping", "intermediate"},
            {"كَثير غالي!", "خیلی گران است!", "ktir ghali", "lebanese", "neutral", "shopping", "beginner"},
            {"هَيدا رَخيص", "این ارزان است", "hayda rakhiis", "lebanese", "neutral", "shopping", "intermediate"},
            {"عِندكُن شَي أرخَص؟", "چیز ارزان‌تری دارید؟", "'indkon shi arkhas", "lebanese", "neutral", "shopping", "intermediate"},
            {"بِدي أشتري هَيدا", "می‌خواهم این را بخرم", "bidi ashtri hayda", "lebanese", "neutral", "shopping", "intermediate"},
            {"كَم ليرَة؟", "چند لیره؟", "kam lira", "lebanese", "neutral", "shopping", "intermediate"},
            {"أعطيني خَصْم", "به من تخفیف بده", "a'tini khasm", "lebanese", "neutral", "shopping", "advanced"},
            {"مُمْكِن أدفَع بِالكارْت؟", "می‌توانم با کارت پرداخت کنم؟", "mumkin adfa' bil-kart", "lebanese", "neutral", "shopping", "intermediate"},
            {"قَبول نَقدي؟", "نقدی قبول می‌کنید؟", "ma'bul na'di", "lebanese", "neutral", "shopping", "intermediate"},
            {"وَيْن السّوق؟", "بازار کجاست؟", "wayn is-su2", "lebanese", "neutral", "shopping", "beginner"},
            {"وَيْن المَحَل؟", "مغازه کجاست؟", "wayn il-mahal", "lebanese", "neutral", "shopping", "intermediate"},
            {"هَيدا غالي كَثير", "این خیلی گران است", "hayda ghali ktir", "lebanese", "neutral", "shopping", "intermediate"},
            {"مُمْكِن أَرجِع هَيدا؟", "می‌توانم این را برگردانم؟", "mumkin arji' hayda", "lebanese", "neutral", "shopping", "advanced"},
            {"كَيْف أدفَع؟", "چطور پرداخت کنم؟", "kayf adfa'", "lebanese", "neutral", "shopping", "intermediate"},
            
            // ----- غذا و رستوران (۲۰ جمله) -----
            {"بِدي آكُل", "می‌خواهم بخورم", "bidi aakul", "lebanese", "neutral", "food", "beginner"},
            {"بِدي أشْرَب", "می‌خواهم بنوشم", "bidi ashrab", "lebanese", "neutral", "food", "beginner"},
            {"شُو فِي عِندكُن؟", "چه دارید؟", "shu fi 3andkon", "lebanese", "neutral", "food", "beginner"},
            {"شُو أكِل اليَوم؟", "غذای امروز چیست؟", "shu akil il-yom", "lebanese", "neutral", "food", "intermediate"},
            {"بِدي جاج", "مرغ می‌خواهم", "bidi jaj", "lebanese", "neutral", "food", "intermediate"},
            {"بِدي سَمَك", "ماهی می‌خواهم", "bidi samak", "lebanese", "neutral", "food", "intermediate"},
            {"بِدي لَحْم", "گوشت می‌خواهم", "bidi la7m", "lebanese", "neutral", "food", "intermediate"},
            {"بِدي خُضْرَة", "سبزی می‌خواهم", "bidi khudra", "lebanese", "neutral", "food", "intermediate"},
            {"بِدي فَواكِه", "میوه می‌خواهم", "bidi fawake", "lebanese", "neutral", "food", "intermediate"},
            {"بِدي مَي", "آب می‌خواهم", "bidi mayy", "lebanese", "neutral", "food", "beginner"},
            {"بِدي شاي", "چای می‌خواهم", "bidi shay", "lebanese", "neutral", "food", "beginner"},
            {"بِدي قَهْوَة", "قهوه می‌خواهم", "bidi qahwa", "lebanese", "neutral", "food", "beginner"},
            {"بِدي عَصير", "آبمیوه می‌خواهم", "bidi 'asir", "lebanese", "neutral", "food", "intermediate"},
            {"غَدا", "ناهار", "ghada", "lebanese", "neutral", "food", "beginner"},
            {"عَشا", "شام", "'asha", "lebanese", "neutral", "food", "beginner"},
            {"فُطور", "صبحانه", "futur", "lebanese", "neutral", "food", "beginner"},
            {"أكِل لَذيذ", "غذا خوشمزه است", "akil laziz", "lebanese", "neutral", "food", "intermediate"},
            {"شُكراً عَالأكِل", "ممنون برای غذا", "shukran 'al-akil", "lebanese", "neutral", "food", "intermediate"},
            {"صَحتَين", "نوش جان", "sa7tayn", "lebanese", "neutral", "food", "beginner"},
            {"بِالهَنا وَالشِّفا", "نوش جان", "bil-hana wish-shifa", "lebanese", "neutral", "food", "intermediate"},
            
            // ----- مسیریابی (۱۰ جمله) -----
            {"دور", "بپیچ", "door", "lebanese", "neutral", "directions", "beginner"},
            {"شِمال", "چپ", "shmal", "lebanese", "neutral", "directions", "beginner"},
            {"يَمين", "راست", "yameen", "lebanese", "neutral", "directions", "beginner"},
            {"دَغْري", "مستقیم", "daghri", "lebanese", "neutral", "directions", "beginner"},
            {"دور عَ الشِّمال", "به چپ بپیچ", "door 'al-shmal", "lebanese", "neutral", "directions", "intermediate"},
            {"دور عَ اليَمين", "به راست بپیچ", "door 'al-yameen", "lebanese", "neutral", "directions", "intermediate"},
            {"إمشي دَغْري", "مستقیم برو", "imshi daghri", "lebanese", "neutral", "directions", "intermediate"},
            {"كَم المَسافَة؟", "چقدر فاصله است؟", "kam il-masafe", "lebanese", "neutral", "directions", "intermediate"},
            {"المَسافَة بَعيدَة", "فاصله دور است", "il-masafe ba'ide", "lebanese", "neutral", "directions", "intermediate"},
            {"المَسافَة قَريبَة", "فاصله نزدیک است", "il-masafe 'aribe", "lebanese", "neutral", "directions", "intermediate"},
            
            // ----- خانواده (۱۰ جمله) -----
            {"أبوكَ", "پدرت (مذکر)", "abouk", "lebanese", "male", "family", "beginner"},
            {"أبوكِ", "پدرت (مونث)", "abouki", "lebanese", "female", "family", "beginner"},
            {"إمَّكَ", "مادرت (مذکر)", "immak", "lebanese", "male", "family", "beginner"},
            {"إمَّكِ", "مادرت (مونث)", "immik", "lebanese", "female", "family", "beginner"},
            {"إخوانَكَ", "برادرانت", "ikhwanak", "lebanese", "neutral", "family", "intermediate"},
            {"خَواتَكَ", "خواهرانت", "khawetak", "lebanese", "neutral", "family", "intermediate"},
            {"عيلَتي كَبيرَة", "خانواده‌ام بزرگ است", "'aylati kbire", "lebanese", "neutral", "family", "intermediate"},
            {"عِندي أخ واحِد", "یک برادر دارم", "'indi akh wahid", "lebanese", "neutral", "family", "intermediate"},
            {"عِندي أخت واحِدَة", "یک خواهر دارم", "'indi ukht wahde", "lebanese", "neutral", "family", "intermediate"},
            {"جِدّي", "پدربزرگم", "jiddi", "lebanese", "neutral", "family", "intermediate"},
            
            // ----- اصطلاحات محاوره‌ای (۱۵ جمله) -----
            {"يا قَلبي", "عزیزم (قلب من)", "ya 'albi", "lebanese", "neutral", "expression", "intermediate"},
            {"روحي", "جانم", "ruhi", "lebanese", "neutral", "expression", "intermediate"},
            {"عَلى راسي", "بالای چشم", "3ala raasi", "lebanese", "neutral", "expression", "intermediate"},
            {"شُو يَعني؟", "یعنی چی؟", "shu ya3ni", "lebanese", "neutral", "expression", "intermediate"},
            {"ما فِي", "هیچی نیست", "maa fi", "lebanese", "neutral", "expression", "beginner"},
            {"شُو فِي؟", "چه خبر؟", "shu fi", "lebanese", "neutral", "expression", "beginner"},
            {"يَلّا", "بریم / بیا", "yalla", "lebanese", "neutral", "expression", "beginner"},
            {"يَلّا نْروح", "بریم", "yalla nrou7", "lebanese", "neutral", "expression", "beginner"},
            {"نِشرَب شَي سَوا؟", "می‌خواهی چیزی بنوشیم؟", "nishrab shi sawa", "lebanese", "neutral", "expression", "intermediate"},
            {"خَلَص", "تمام شد", "khalas", "lebanese", "neutral", "expression", "beginner"},
            {"خَلَصنا", "تمام کردیم", "khalasna", "lebanese", "neutral", "expression", "intermediate"},
            {"روح", "برو", "roo7", "lebanese", "neutral", "expression", "beginner"},
            {"تَعال", "بیا", "ta3aal", "lebanese", "neutral", "expression", "beginner"},
            {"شَباب", "بچه‌ها", "shbab", "lebanese", "neutral", "expression", "beginner"},
            {"مَع السَّلامَة", "خدا حافظ", "ma3 is-saleme", "lebanese", "neutral", "expression", "beginner"},
            
            // ----- عشق و احساسات (۱۵ جمله) -----
            {"بِحِبَّك", "دوستت دارم (مذکر)", "ba7ebbak", "lebanese", "male", "love", "intermediate"},
            {"بِحِبَّكِ", "دوستت دارم (مونث)", "ba7ebbek", "lebanese", "female", "love", "intermediate"},
            {"إشتَقتلَك", "دلم برایت تنگ شده (مذکر)", "ishta2tilak", "lebanese", "male", "love", "intermediate"},
            {"إشتَقتلِك", "دلم برایت تنگ شده (مونث)", "ishta2tilik", "lebanese", "female", "love", "intermediate"},
            {"تَعبان", "خسته‌ام (مذکر)", "ta3ban", "lebanese", "male", "emotion", "beginner"},
            {"تَعبانَة", "خسته‌ام (مونث)", "ta3bane", "lebanese", "female", "emotion", "beginner"},
            {"زَعْلان", "ناراحتم (مذکر)", "za3lan", "lebanese", "male", "emotion", "intermediate"},
            {"زَعْلانَة", "ناراحتم (مونث)", "za3lane", "lebanese", "female", "emotion", "intermediate"},
            {"فَرحان", "خوشحالم (مذکر)", "far7an", "lebanese", "male", "emotion", "intermediate"},
            {"فَرحانَة", "خوشحالم (مونث)", "far7ane", "lebanese", "female", "emotion", "intermediate"},
            {"خايف", "می‌ترسم (مذکر)", "khayif", "lebanese", "male", "emotion", "intermediate"},
            {"خايفَة", "می‌ترسم (مونث)", "khayfe", "lebanese", "female", "emotion", "intermediate"},
            {"نَعسان", "خوابم می‌آید (مذکر)", "na3san", "lebanese", "male", "emotion", "intermediate"},
            {"نَعسانَة", "خوابم می‌آید (مونث)", "na3sane", "lebanese", "female", "emotion", "intermediate"},
            {"جوعان", "گرسنه‌ام (مذکر)", "ju3an", "lebanese", "male", "emotion", "intermediate"},
        };

        // ==========================================
        // بخش سوم: لهجه آمریکایی (۱۳۰ جمله)
        // ==========================================
        String[][] american = {
            // ----- احوالپرسی (۲۰ جمله) -----
            {"Hello", "سلام", "hello", "american", "neutral", "greeting", "beginner"},
            {"Hi", "سلام", "hi", "american", "neutral", "greeting", "beginner"},
            {"Hey", "سلام (خودمانی)", "hey", "american", "neutral", "greeting", "beginner"},
            {"Good morning", "صبح بخیر", "good morning", "american", "neutral", "greeting", "beginner"},
            {"Good afternoon", "بعدازظهر بخیر", "good afternoon", "american", "neutral", "greeting", "beginner"},
            {"Good evening", "عصر بخیر", "good evening", "american", "neutral", "greeting", "beginner"},
            {"Good night", "شب بخیر", "good night", "american", "neutral", "greeting", "beginner"},
            {"How are you?", "چطوری؟", "how are you", "american", "neutral", "greeting", "beginner"},
            {"How are you doing?", "چطوری؟", "how are you doing", "american", "neutral", "greeting", "beginner"},
            {"How's it going?", "اوضاع چطوره؟", "how's it going", "american", "neutral", "greeting", "beginner"},
            {"What's up?", "چه خبر؟", "what's up", "american", "neutral", "greeting", "beginner"},
            {"What's new?", "چه خبر؟", "what's new", "american", "neutral", "greeting", "beginner"},
            {"How have you been?", "چطور بودی؟", "how have you been", "american", "neutral", "greeting", "intermediate"},
            {"Long time no see", "مدت طولانی ندیدمت", "long time no see", "american", "neutral", "greeting", "intermediate"},
            {"I'm fine, thanks", "خوبم، ممنون", "fine thanks", "american", "neutral", "greeting", "beginner"},
            {"I'm good", "خوبم", "I'm good", "american", "neutral", "greeting", "beginner"},
            {"Not bad", "بد نیست", "not bad", "american", "neutral", "greeting", "beginner"},
            {"Pretty good", "خیلی خوب", "pretty good", "american", "neutral", "greeting", "beginner"},
            {"Couldn't be better", "بهتر از این نمی‌شد", "couldn't be better", "american", "neutral", "greeting", "intermediate"},
            {"Same as always", "مثل همیشه", "same as always", "american", "neutral", "greeting", "intermediate"},
            
            // ----- معرفی (۱۵ جمله) -----
            {"What's your name?", "اسمت چیه؟", "what's your name", "american", "neutral", "introduction", "beginner"},
            {"My name is...", "اسم من ... است", "my name is", "american", "neutral", "introduction", "beginner"},
            {"I'm...", "من ... هستم", "I'm", "american", "neutral", "introduction", "beginner"},
            {"Nice to meet you", "از دیدنت خوشحالم", "nice to meet you", "american", "neutral", "introduction", "beginner"},
            {"Nice to meet you too", "من هم خوشحالم", "nice to meet you too", "american", "neutral", "introduction", "beginner"},
            {"Pleased to meet you", "از دیدنت خوشبختم", "pleased to meet you", "american", "neutral", "introduction", "intermediate"},
            {"Where are you from?", "اهل کجایی؟", "where are you from", "american", "neutral", "introduction", "beginner"},
            {"I'm from...", "اهل ... هستم", "I'm from", "american", "neutral", "introduction", "beginner"},
            {"Where do you live?", "کجا زندگی می‌کنی؟", "where do you live", "american", "neutral", "introduction", "intermediate"},
            {"I live in...", "در ... زندگی می‌کنم", "I live in", "american", "neutral", "introduction", "intermediate"},
            {"What do you do?", "چه کار می‌کنی؟", "what do you do", "american", "neutral", "introduction", "intermediate"},
            {"I'm a student", "دانشجو هستم", "I'm a student", "american", "neutral", "introduction", "intermediate"},
            {"I'm a teacher", "معلم هستم", "I'm a teacher", "american", "neutral", "introduction", "intermediate"},
            {"I'm an engineer", "مهندس هستم", "I'm an engineer", "american", "neutral", "introduction", "intermediate"},
            {"I work as a...", "به عنوان ... کار می‌کنم", "I work as a", "american", "neutral", "introduction", "intermediate"},
            
            // ----- سوالات روزمره (۲۰ جمله) -----
            {"What time is it?", "ساعت چند است؟", "what time", "american", "neutral", "daily", "beginner"},
            {"Do you have the time?", "ساعت داری؟", "have the time", "american", "neutral", "daily", "intermediate"},
            {"Where is the bathroom?", "دستشویی کجاست؟", "where bathroom", "american", "neutral", "daily", "beginner"},
            {"Where is the restroom?", "دستشویی کجاست؟ (مودبانه)", "where restroom", "american", "neutral", "daily", "intermediate"},
            {"Where is the airport?", "فرودگاه کجاست؟", "where airport", "american", "neutral", "daily", "beginner"},
            {"Where is the hotel?", "هتل کجاست؟", "where hotel", "american", "neutral", "daily", "beginner"},
            {"Where is the restaurant?", "رستوران کجاست؟", "where restaurant", "american", "neutral", "daily", "beginner"},
            {"Where is the hospital?", "بیمارستان کجاست؟", "where hospital", "american", "neutral", "daily", "intermediate"},
            {"Where is the school?", "مدرسه کجاست؟", "where school", "american", "neutral", "daily", "intermediate"},
            {"Where is the university?", "دانشگاه کجاست؟", "where university", "american", "neutral", "daily", "intermediate"},
            {"Where is the train station?", "ایستگاه قطار کجاست؟", "where train station", "american", "neutral", "daily", "intermediate"},
            {"Where is the bus stop?", "ایستگاه اتوبوس کجاست؟", "where bus stop", "american", "neutral", "daily", "intermediate"},
            {"Can you help me?", "می‌توانی کمکم کنی؟", "can you help", "american", "neutral", "daily", "beginner"},
            {"Could you help me?", "می‌توانی کمکم کنی؟ (مودبانه)", "could you help", "american", "neutral", "daily", "intermediate"},
            {"Can you speak more slowly?", "می‌توانی آهسته‌تر صحبت کنی؟", "speak slowly", "american", "neutral", "daily", "intermediate"},
            {"I don't understand", "متوجه نمی‌شوم", "don't understand", "american", "neutral", "daily", "beginner"},
            {"I'm sorry, I don't understand", "متاسفم، متوجه نمی‌شوم", "sorry don't understand", "american", "neutral", "daily", "intermediate"},
            {"Could you repeat that?", "می‌توانی دوباره بگویی؟", "repeat that", "american", "neutral", "daily", "intermediate"},
            {"What does that mean?", "معنی آن چیست؟", "what does that mean", "american", "neutral", "daily", "intermediate"},
            {"How do you say that?", "چطور آن را می‌گویید؟", "how say that", "american", "neutral", "daily", "intermediate"},
            
            // ----- خرید و پول (۱۵ جمله) -----
            {"How much is this?", "این چند است؟", "how much", "american", "neutral", "shopping", "beginner"},
            {"How much does this cost?", "قیمت این چقدر است؟", "how much cost", "american", "neutral", "shopping", "intermediate"},
            {"That's too expensive!", "خیلی گران است!", "too expensive", "american", "neutral", "shopping", "beginner"},
            {"That's cheap", "این ارزان است", "that's cheap", "american", "neutral", "shopping", "intermediate"},
            {"Do you have anything cheaper?", "چیز ارزان‌تری دارید؟", "anything cheaper", "american", "neutral", "shopping", "intermediate"},
            {"I want to buy this", "می‌خواهم این را بخرم", "want to buy", "american", "neutral", "shopping", "intermediate"},
            {"How much is the total?", "مجموعاً چند است؟", "total cost", "american", "neutral", "shopping", "intermediate"},
            {"Can I get a discount?", "می‌توانم تخفیف بگیرم؟", "get discount", "american", "neutral", "shopping", "advanced"},
            {"Can I pay with card?", "می‌توانم با کارت پرداخت کنم؟", "pay with card", "american", "neutral", "shopping", "intermediate"},
            {"Do you accept cash?", "نقدی قبول می‌کنید؟", "accept cash", "american", "neutral", "shopping", "intermediate"},
            {"Where is the mall?", "مرکز خرید کجاست؟", "where mall", "american", "neutral", "shopping", "beginner"},
            {"Where is the store?", "مغازه کجاست؟", "where store", "american", "neutral", "shopping", "intermediate"},
            {"I'd like to return this", "می‌خواهم این را پس بدهم", "return this", "american", "neutral", "shopping", "advanced"},
            {"I'd like to exchange this", "می‌خواهم این را عوض کنم", "exchange this", "american", "neutral", "shopping", "advanced"},
            {"Can I try this on?", "می‌توانم این را امتحان کنم؟", "try this on", "american", "neutral", "shopping", "intermediate"},
            
            // ----- غذا و رستوران (۲۰ جمله) -----
            {"I want to eat", "می‌خواهم بخورم", "want to eat", "american", "neutral", "food", "beginner"},
            {"I want to drink", "می‌خواهم بنوشم", "want to drink", "american", "neutral", "food", "beginner"},
            {"I'm hungry", "گرسنه‌ام", "hungry", "american", "neutral", "food", "beginner"},
            {"I'm thirsty", "تشنه‌ام", "thirsty", "american", "neutral", "food", "beginner"},
            {"What do you have?", "چه دارید؟", "what do you have", "american", "neutral", "food", "beginner"},
            {"What's on the menu?", "منو چیست؟", "what's on menu", "american", "neutral", "food", "intermediate"},
            {"I want chicken", "مرغ می‌خواهم", "chicken", "american", "neutral", "food", "intermediate"},
            {"I want fish", "ماهی می‌خواهم", "fish", "american", "neutral", "food", "intermediate"},
            {"I want meat", "گوشت می‌خواهم", "meat", "american", "neutral", "food", "intermediate"},
            {"I want vegetables", "سبزی می‌خواهم", "vegetables", "american", "neutral", "food", "intermediate"},
            {"I want fruit", "میوه می‌خواهم", "fruit", "american", "neutral", "food", "intermediate"},
            {"I want water", "آب می‌خواهم", "water", "american", "neutral", "food", "beginner"},
            {"I want tea", "چای می‌خواهم", "tea", "american", "neutral", "food", "beginner"},
            {"I want coffee", "قهوه می‌خواهم", "coffee", "american", "neutral", "food", "beginner"},
            {"I want juice", "آبمیوه می‌خواهم", "juice", "american", "neutral", "food", "intermediate"},
            {"Lunch", "ناهار", "lunch", "american", "neutral", "food", "beginner"},
            {"Dinner", "شام", "dinner", "american", "neutral", "food", "beginner"},
            {"Breakfast", "صبحانه", "breakfast", "american", "neutral", "food", "beginner"},
            {"The food is delicious", "غذا خوشمزه است", "delicious", "american", "neutral", "food", "intermediate"},
            {"Thank you for the food", "ممنون برای غذا", "thank you for food", "american", "neutral", "food", "intermediate"},
            
            // ----- مسیریابی (۱۰ جمله) -----
            {"Turn left", "به چپ بپیچ", "turn left", "american", "neutral", "directions", "beginner"},
            {"Turn right", "به راست بپیچ", "turn right", "american", "neutral", "directions", "beginner"},
            {"Go straight", "مستقیم برو", "go straight", "american", "neutral", "directions", "beginner"},
            {"How far is it?", "چقدر فاصله دارد؟", "how far", "american", "neutral", "directions", "intermediate"},
            {"It's far", "دور است", "far", "american", "neutral", "directions", "intermediate"},
            {"It's close", "نزدیک است", "close", "american", "neutral", "directions", "intermediate"},
            {"Take the first street", "اولین خیابان را برو", "first street", "american", "neutral", "directions", "intermediate"},
            {"Take the second street", "دومین خیابان را برو", "second street", "american", "neutral", "directions", "intermediate"},
            {"It's on the left", "سمت چپ است", "on the left", "american", "neutral", "directions", "intermediate"},
            {"It's on the right", "سمت راست است", "on the right", "american", "neutral", "directions", "intermediate"},
            
            // ----- خانواده (۱۰ جمله) -----
            {"Your father", "پدرت", "your father", "american", "neutral", "family", "beginner"},
            {"Your mother", "مادرت", "your mother", "american", "neutral", "family", "beginner"},
            {"Your brothers", "برادرانت", "your brothers", "american", "neutral", "family", "intermediate"},
            {"Your sisters", "خواهرانت", "your sisters", "american", "neutral", "family", "intermediate"},
            {"My family is big", "خانواده‌ام بزرگ است", "family big", "american", "neutral", "family", "intermediate"},
            {"I have one brother", "یک برادر دارم", "one brother", "american", "neutral", "family", "intermediate"},
            {"I have one sister", "یک خواهر دارم", "one sister", "american", "neutral", "family", "intermediate"},
            {"My grandfather", "پدربزرگم", "grandfather", "american", "neutral", "family", "intermediate"},
            {"My grandmother", "مادربزرگم", "grandmother", "american", "neutral", "family", "intermediate"},
            {"My parents", "والدینم", "parents", "american", "neutral", "family", "intermediate"},
            
            // ----- اصطلاحات رایج (۱۵ جمله) -----
            {"Please", "لطفاً", "please", "american", "neutral", "expression", "beginner"},
            {"Thank you", "متشکرم", "thank you", "american", "neutral", "expression", "beginner"},
            {"Thanks", "ممنون", "thanks", "american", "neutral", "expression", "beginner"},
            {"You're welcome", "خواهش می‌کنم", "you're welcome", "american", "neutral", "expression", "beginner"},
            {"Excuse me", "ببخشید", "excuse me", "american", "neutral", "expression", "beginner"},
            {"I'm sorry", "متاسفم", "I'm sorry", "american", "neutral", "expression", "beginner"},
            {"No problem", "مشکلی نیست", "no problem", "american", "neutral", "expression", "beginner"},
            {"That's okay", "اشکالی ندارد", "that's okay", "american", "neutral", "expression", "intermediate"},
            {"Good luck", "موفق باشی", "good luck", "american", "neutral", "expression", "beginner"},
            {"Take care", "خودت را حفظ کن", "take care", "american", "neutral", "expression", "beginner"},
            {"Have a nice day", "روز خوبی داشته باشی", "nice day", "american", "neutral", "expression", "beginner"},
            {"See you later", "بعداً می‌بینمت", "see you later", "american", "neutral", "expression", "beginner"},
            {"See you tomorrow", "فردا می‌بینمت", "see you tomorrow", "american", "neutral", "expression", "beginner"},
            {"See you soon", "به زودی می‌بینمت", "see you soon", "american", "neutral", "expression", "beginner"},
            {"Take it easy", "آروم باش", "take it easy", "american", "neutral", "expression", "intermediate"},
            
            // ----- عشق و احساسات (۱۵ جمله) -----
            {"I love you", "دوستت دارم", "love you", "american", "neutral", "love", "intermediate"},
            {"I miss you", "دلم برایت تنگ شده", "miss you", "american", "neutral", "love", "intermediate"},
            {"I'm tired", "خسته‌ام", "tired", "american", "neutral", "emotion", "beginner"},
            {"I'm happy", "خوشحالم", "happy", "american", "neutral", "emotion", "beginner"},
            {"I'm sad", "ناراحتم", "sad", "american", "neutral", "emotion", "beginner"},
            {"I'm angry", "عصبانی‌ام", "angry", "american", "neutral", "emotion", "intermediate"},
            {"I'm scared", "می‌ترسم", "scared", "american", "neutral", "emotion", "intermediate"},
            {"I'm sleepy", "خوابم می‌آید", "sleepy", "american", "neutral", "emotion", "intermediate"},
            {"I'm excited", "هیجان‌زده‌ام", "excited", "american", "neutral", "emotion", "intermediate"},
            {"I'm bored", "حوصله‌ام سر رفته", "bored", "american", "neutral", "emotion", "intermediate"},
            {"I'm hungry", "گرسنه‌ام", "hungry", "american", "neutral", "emotion", "beginner"},
            {"I'm thirsty", "تشنه‌ام", "thirsty", "american", "neutral", "emotion", "beginner"},
            {"I'm surprised", "متعجبم", "surprised", "american", "neutral", "emotion", "intermediate"},
            {"I'm worried", "نگرانم", "worried", "american", "neutral", "emotion", "intermediate"},
            {"I'm confused", "سردرگمم", "confused", "american", "neutral", "emotion", "intermediate"},
            
            // ----- خداحافظی (۱۰ جمله) -----
            {"Goodbye", "خداحافظ", "goodbye", "american", "neutral", "daily", "beginner"},
            {"Bye", "خداحافظ", "bye", "american", "neutral", "daily", "beginner"},
            {"See you", "می‌بینمت", "see you", "american", "neutral", "daily", "beginner"},
            {"See you later", "بعداً می‌بینمت", "see you later", "american", "neutral", "daily", "beginner"},
            {"See you tomorrow", "فردا می‌بینمت", "see you tomorrow", "american", "neutral", "daily", "beginner"},
            {"See you soon", "به زودی می‌بینمت", "see you soon", "american", "neutral", "daily", "beginner"},
            {"Take care", "خودت را حفظ کن", "take care", "american", "neutral", "daily", "beginner"},
            {"Have a good one", "روز خوبی داشته باشی", "good one", "american", "neutral", "daily", "intermediate"},
            {"Catch you later", "بعداً می‌بینمت", "catch you later", "american", "neutral", "daily", "intermediate"},
            {"Talk to you soon", "به زودی صحبت می‌کنیم", "talk soon", "american", "neutral", "daily", "intermediate"},
        };

        // ذخیره همه جملات در دیتابیس
        insertPhrases(db, iraqi);
        insertPhrases(db, lebanese);
        insertPhrases(db, american);
    }

    private void insertPhrases(SQLiteDatabase db, String[][] phrases) {
        for (String[] phrase : phrases) {
            ContentValues values = new ContentValues();
            values.put("arabic_text", phrase[0]);
            values.put("persian_text", phrase[1]);
            values.put("phonetic", phrase[2]);
            values.put("dialect", phrase[3]);
            values.put("gender", phrase[4]);
            values.put("category", phrase[5]);
            values.put("difficulty", phrase[6]);
            values.put("is_favorite", 0);
            db.insert(TABLE_PHRASES, null, values);
        }
    }

    // ===== متدهای عمومی =====
    public List<Phrase> getPhrases(String dialect, String gender) {
        List<Phrase> phraseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query;
        String[] selectionArgs;

        if (gender.equals("neutral")) {
            query = "SELECT * FROM " + TABLE_PHRASES + " WHERE dialect = ? ORDER BY category";
            selectionArgs = new String[]{dialect};
        } else {
            query = "SELECT * FROM " + TABLE_PHRASES +
                    " WHERE dialect = ? AND (gender = ? OR gender = 'neutral') ORDER BY category";
            selectionArgs = new String[]{dialect, gender};
        }

        Cursor cursor = db.rawQuery(query, selectionArgs);
        phraseList = cursorToPhraseList(cursor);
        cursor.close();
        db.close();
        return phraseList;
    }

    public List<Phrase> searchPhrases(String query, String dialect) {
        List<Phrase> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + TABLE_PHRASES +
                " WHERE dialect = ? AND (arabic_text LIKE ? OR persian_text LIKE ?)";
        String[] args = new String[]{dialect, "%" + query + "%", "%" + query + "%"};

        Cursor cursor = db.rawQuery(sql, args);
        results = cursorToPhraseList(cursor);
        cursor.close();
        db.close();
        return results;
    }

    public void toggleFavorite(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_PHRASES +
                " SET is_favorite = CASE WHEN is_favorite = 0 THEN 1 ELSE 0 END" +
                " WHERE id = ?";
        db.execSQL(sql, new Object[]{id});
        db.close();
    }

    public int getPhraseCount(String dialect) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_PHRASES + " WHERE dialect = ?";
        Cursor cursor = db.rawQuery(query, new String[]{dialect});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    public void deletePhrase(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PHRASES, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    private List<Phrase> cursorToPhraseList(Cursor cursor) {
        List<Phrase> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Phrase phrase = new Phrase();
                phrase.id = cursor.getInt(0);
                phrase.arabicText = cursor.getString(1);
                phrase.persianText = cursor.getString(2);
                phrase.phonetic = cursor.getString(3);
                phrase.dialect = cursor.getString(4);
                phrase.gender = cursor.getString(5);
                phrase.category = cursor.getString(6);
                phrase.difficulty = cursor.getString(7);
                phrase.isFavorite = cursor.getInt(8) == 1;
                list.add(phrase);
            } while (cursor.moveToNext());
        }
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHRASES);
        onCreate(db);
    }
}
