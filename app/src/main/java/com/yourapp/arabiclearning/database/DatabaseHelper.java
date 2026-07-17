package com.yourapp.arabiclearning.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.yourapp.arabiclearning.models.DailyPhrase;
import com.yourapp.arabiclearning.models.Phrase;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "arabic_phrases.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_PHRASES = "phrases";
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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
            
            // ----- عشق و احساسات (۲۰ جمله) -----
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
            {"جوعانَة", "گرسنه‌ام (مونث)", "ju'ana", "iraqi", "female", "emotion", "intermediate"},
            {"عطشان", "تشنه‌ام (مذکر)", "'atshan", "iraqi", "male", "emotion", "intermediate"},
            {"عطشانَة", "تشنه‌ام (مونث)", "'atshana", "iraqi", "female", "emotion", "intermediate"},
            {"مُتَوَتِّر", "نگرانم (مذکر)", "mutawattir", "iraqi", "male", "emotion", "advanced"},
            {"مُتَوَتِّرَة", "نگرانم (مونث)", "mutawattira", "iraqi", "female", "emotion", "advanced"},
        };

        // ==========================================
        // بخش دوم: لهجه لبنانی (۱۳۰ جمله)
        // ==========================================
        // ===== بخش دوم: لهجه لبنانی (نسخه اصلاح شده) =====
String[][] lebanese = {
    // احوالپرسی
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
    // ===== این خط مشکل‌دار رو اصلاح کردم =====
    {"الحَمدُلله عَلى السَّلامَة", "خدا را شکر برای سلامتی", "alhamdulillah al-salame", "lebanese", "neutral", "greeting", "advanced"},
    {"يِسْلَمُو", "سلامت باشی", "yislamo", "lebanese", "neutral", "greeting", "intermediate"},
    // ... بقیه جملات لبنانی (همون ۱۳۰ جمله‌ای که قبلاً دادی)
};
