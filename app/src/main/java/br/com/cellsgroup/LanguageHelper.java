package br.com.cellsgroup;

import android.content.res.*;

import java.util.*;

public class LanguageHelper{
   public static Configuration configuration;
   static String locale;
   
   public static String getLocale(){
      configuration =  new Configuration(Resources.getSystem().getConfiguration());
      locale= configuration.locale.toString();
  
      if("ab".equals( locale )){
         return "ab";
      }else if("aa".equals( locale ) ) {
         //Afar
         return "aa";
      }else if("af_ZA".equals( locale ) ) {
         //Afrikaans
         return  "af_ZA";
      }else if("sq_AL".equals( locale ) ) {
        // Albanian
         return "sq_AL";
      }else if("am".equals( locale ) ) {
         //Amharic
         return "am";
      }else if("ar".equals( locale ) ) {
        // Arabic
        return "ar";
      }else if("ar_DZ".equals( locale ) ) {
         //Arabic( Algeria ) ar_DZ
        return "ar_DZ";
      }else if("ar_BH".equals( locale ) ) {
        // Arabic( Bahrain ) ar_BH
        return "ar_BH";
      }else if("ar_EG".equals( locale ) ) {
        // Arabic( Egypt ) ar_EG
         return "ar_EG";
      }else if("ar_IQ".equals( locale ) ) {
        // Arabic( Iraq ) ar_IQ
         return "ar_IQ";
      }else if("ar_JO".equals( locale ) ) {
        // Arabic( Jordan ) ar_JO
         return "ar_JO";
      }else if("ar_KW".equals( locale ) ) {
        // Arabic( Kuwait ) ar_KW
         return "ar_KW";
      }else if("ar_LB".equals( locale ) ) {
         //Arabic( Lebanon ) ar_LB
         return "ar_LB";
      }else if("ar_LY".equals( locale ) ) {
         //Arabic( Libya ) ar_LY
         return "ar_LY";
      }else if("ar_MA".equals( locale ) ) {
         //Arabic( Morocco ) ar_MA
         return "ar_MA";
      }else if("ar_OM".equals( locale ) ) {
        // Arabic( Oman ) ar_OM
         return "ar_OM";
      }else if("ar_QA".equals( locale ) ) {
        // Arabic( Qatar ) ar_QA
         return "ar_QA";
      }else if("ar_SA".equals( locale ) ) {
         //Arabic( Saudi Arabia ) ar_SA
         return "ar_SA";
      }else if("ar_SD".equals( locale ) ) {
         //Arabic( Sudan ) ar_SD
         return "ar_SD";
      }else if("ar_SY".equals( locale ) ) {
         //Arabic( Syria ) ar_SY
         return "ar_SY";
      }else if("ar_TN".equals( locale ) ) {
         //Arabic( Tunisia ) ar_TN
         return "ar_TN";
      }else if("ar_AE".equals( locale ) ) {
         //Arabic( Uae ) ar_AE
         return "ar_AE";
      }else if("ar_YE".equals( locale ) ) {
         //Arabic( Yemen ) ar_YE
         return "ar_YE";
      }else if("hy".equals( locale ) ) {
        // Armenian hy
         return "hy";
      }else if("as".equals( locale ) ) {
        // Assamese as
      }else if("ay".equals( locale ) ) {
         //Aymara ay
         return "ay";
      }else if("az".equals( locale ) ) {
         //Azeri az
         return "az";
      }else if("az".equals( locale ) ) {
         //Azeri( Cyrillic ) az
         return "az";
      }else if("az".equals( locale ) ) {
         //Azeri( Latin ) az
         return "az";
      }else if("ba".equals( locale ) ) {
         //Bashkir ba
         return "ba";
      }else if("eu_ES".equals( locale ) ) {
         //Basque eu_ES
         return "eu_ES";
      }else if("be_BY".equals( locale ) ) {
         //Belarusian be_BY
         return "be_BY";
      }else if("bn".equals( locale ) ) {
        // Bengali bn
         return "bn";
      }else if("dz".equals( locale ) ) {
        // Bhutani dz
         return "dz";
      }else if("".equals( locale ) ) {
        // Bihari bh
      }else if("bi".equals( locale ) ) {
         //Bislama bi
         return "bi";
      }else if("br".equals( locale ) ) {
        // Breton br
         return "br";
      }else if("bg_BG".equals( locale ) ) {
        // Bulgarian bg_BG
         return "my";
      }else if("".equals( locale ) ) {
        // Burmese my
         return "my";
      }else if("km".equals( locale ) ) {
        // Cambodian km
         return "km";
      }else if("ca_ES".equals( locale ) ) {
         //Catalan ca_ES
         return "ca_ES";
      }else if("zh_TW".equals( locale ) ) {
         //Chinese zh_TW
         return "zh_TW";
      }else if("zh_HK".equals( locale ) ) {
         //Chinese( Hongkong ) zh_HK
         return "zh_HK";
      }else if("zh_MO".equals( locale ) ) {
        // Chinese( Macau ) zh_MO
         return "zh_MO";
      }else if("zh_CN".equals( locale ) ) {
         //Chinese( Simplified ) zh_CN
         return "zh_CN";
      }else if("zh_SG".equals( locale ) ) {
         //Chinese( Singapore ) zh_SG
         return "zh_SG";
      }else if("zh_TW".equals( locale ) ) {
         //Chinese( Taiwan ) zh_TW
         return "zh_TW";
      }else if("zh_TW".equals( locale ) ) {
         //Chinese( Traditional ) zh_TW
         return "zh_TW";
      }else if("co".equals( locale ) ) {
         //Corsican co
         return "co";
      }else if("hr_H".equals( locale ) ) {
         //Croatian hr_HR
         return "hr_H";
      }else if("cs_CZ".equals( locale ) ) {
         //Czech cs_CZ
         return "cs_CZ";
      }else if("da_DK".equals( locale ) ) {
         //Danish da_DK
         return "da_DK";
      }else if("pt_BR".equals( locale ) ) {
         //Portuguese( Brazilian ) pt_BR
         return "pt_BR";
      }else if("nl_NL".equals( locale ) ) {
         //Dutch nl_NL
         return "nl_NL";
      }else if("nl_BE".equals( locale ) ) {
         //Dutch( Belgian ) nl_BE
         return "nl_BE";
      }else if("en_GB".equals( locale ) ) {
         //English en_GB
         return "en_GB";
      }else if("en_AU".equals( locale ) ) {
         //English( Australia ) en_AU
         return "en_AU";
      }else if("en_BZ".equals( locale ) ) {
         //English( Belize ) en_BZ
         return "en_BZ";
      }else if("en_BW".equals( locale ) ) {
         //English( Botswana ) en_BW
         return "en_BW";
      }else if("en_CA".equals( locale ) ) {
         //English( Canada ) en_CA
         return "en_CA";
      }else if("en_CB".equals( locale ) ) {
         //English( Caribbean ) en_CB
         return "en_CB";
      }else if("en_DK".equals( locale ) ) {
         //English( Denmark ) en_DK
         return "en_DK";
      }else if("en_IE".equals( locale ) ) {
         //English( Eire ) en_IE
         return "en_IE";
      }else if("en_JM".equals( locale ) ) {
         //English( Jamaica ) en_JM
         return "en_JM";
      }else if("en_NZ".equals( locale ) ) {
         //English( New Zealand ) en_NZ
         return "en_NZ";
      }else if("en_PH".equals( locale ) ) {
         //English( Philippines ) en_PH
         return "en_PH";
      }else if("en_ZA".equals( locale ) ) {
         //English( South Africa ) en_ZA
         return "en_ZA";
      }else if("en_TT".equals( locale ) ) {
         //English( Trinidad ) en_TT
         return "en_TT";
      }else if("en_GB".equals( locale ) ) {
         //English( U.K. ) en_GB
         return "en_GB";
      }else if("en_US".equals( locale ) ) {
         //English( U.S. ) en_US
         return "en_US";
      }else if("en_ZW".equals( locale ) ) {
         //English( Zimbabwe ) en_ZW
         return "en_ZW";
      }else if("eo".equals( locale ) ) {
         //Esperanto eo
         return "eo";
      }else if("et_EE".equals( locale ) ) {
         //Estonian et_EE
         return "et_EE";
      }else if("fo_FO".equals( locale ) ) {
         //Faeroese fo_FO
         return "";
      }else if("fa_IR".equals( locale ) ) {
         //Farsi fa_IR
         return "fa_IR";
      }else if("fj".equals( locale ) ) {
         //Fiji fj
         return "fj";
      }else if("fi_FI".equals( locale ) ) {
         //Finnish fi_FI
         return "fi_FI";
      }else if("fr_FR".equals( locale ) ) {
         //French fr_FR
         return "fr_FR";
      }else if("fr_BE".equals( locale ) ) {
         //French( Belgian ) fr_BE
         return "fr_BE";
      }else if("fr_CA".equals( locale ) ) {
         //French( Canadian ) fr_CA
         return "fr_CA";
      }else if("fr_LU".equals( locale ) ) {
         //French( Luxembourg ) fr_LU
         return "fr_LU";
      }else if("fr_MC".equals( locale ) ) {
         //French( Monaco ) fr_MC
         return "fr_MC";
      }else if("fr_CH".equals( locale ) ) {
         //French( Swiss ) fr_CH
         return "fr_CH";
      }else if("fy".equals( locale ) ) {
         //Frisian fy
         return "fy";
      }else if("gl_ES".equals( locale ) ) {
         //Galician gl_ES
         return "gl_ES";
      }else if("ka_GE".equals( locale ) ) {
         //Georgian ka_GE
         return "ka_GE";
      }else if("de_DE".equals( locale ) ) {
         //German de_DE
         return "de_DE";
      }else if("de_AT".equals( locale ) ) {
         //German( Austrian ) de_AT
         return "de_AT";
      }else if("de_BE".equals( locale ) ) {
         //German( Belgium ) de_BE
         return "de_BE";
      }else if("de_LI".equals( locale ) ) {
         //German( Liechtenstein ) de_LI
         return "de_LI";
      }else if("de_LU".equals( locale ) ) {
         //German( Luxembourg ) de_LU
         return "de_LU";
      }else if("".equals( locale ) ) {
         //German( Swiss ) de_CH
         return "";
      }else if("el_GR".equals( locale ) ) {
        // Greek el_GR
         return "el_GR";
      }else if("kl_GL".equals( locale ) ) {
         //Greenlandic kl_GL
         return "kl_GL";
      }else if("gn".equals( locale ) ) {
        // Guarani gn
         return "gn";
      }else if("gu".equals( locale ) ) {
         //Gujarati gu
         return "gu";
      }else if("ha".equals( locale ) ) {
         //Hausa ha
         return "ha";
      }else if("he_IL".equals( locale ) ) {
         //Hebrew he_IL
         return "he_IL";
      }else if("hi_IN".equals( locale ) ) {
         //Hindi hi_IN
         return "hi_IN";
      }else if("hu_HU".equals( locale ) ) {
         //Hungarian hu_HU
         return "hu_HU";
      }else if("is_IS".equals( locale ) ) {
         //Icelandic is_IS
         return "is_IS";
      }else if("id_ID".equals( locale ) ) {
         //Indonesian id_ID
         return "id_ID";
      }else if("ia".equals( locale ) ) {
         //Interlingua ia
         return "ia";
      }else if("ie".equals( locale ) ) {
         //Interlingue ie
         return "ie";
      }else if("ie".equals( locale ) ) {
         //Inuktitut iu
         return "iu";
      }else if("ik".equals( locale ) ) {
         //Inupiak ik
         return "ik";
      }else if("ga_IE".equals( locale ) ) {
        //Irish ga_IE
         return "ga_IE";
      }else if("it_IT".equals( locale ) ) {
         //Italian it_IT
         return "it_IT";
      }else if("it_CH".equals( locale ) ) {
         //Italian( Swiss ) it_CH
         return "it_CH";
      }else if("ja_JP".equals( locale ) ) {
         //Japanese ja_JP
         return "ja_JP";
      }else if("jw".equals( locale ) ) {
         //Javanese jw
         return "jw";
      }else if("kn".equals( locale ) ) {
         //Kannada kn
         return "kn";
      }else if("ks".equals( locale ) ) {
         //Kashmiri ks
         return "ks";
      }else if("ks_IN".equals( locale ) ) {
         //Kashmiri( India ) ks_IN
         return "ks_IN";
      }else if("kk".equals( locale ) ) {
         //Kazakh kk
         return "";
      }else if("kw_GB".equals( locale ) ) {
         //Kernewek kw_GB
         return "kw_GB";
      }else if("rw".equals( locale ) ) {
         //Kinyarwanda rw
         return "rw";
      }else if("ky".equals( locale ) ) {
         //Kirghiz ky
         return "ky";
      }else if("rn".equals( locale ) ) {
         //Kirundi rn
         return "rn";
      }else if("kok_IN".equals( locale ) ) {
         //Konkani kok_IN
         return "kok_IN";
      }else if("ko_KR".equals( locale ) ) {
         //Korean ko_KR
         return "ko_KR";
      }else if("ku_TR".equals( locale ) ) {
         //Kurdish ku_TR
         return "ku_TR";
      }else if("lo".equals( locale ) ) {
         //Laothian lo
         return "lo";
      }else if("la".equals( locale ) ) {
         //Latin la
         return "la";
      }else if("lv_LV".equals( locale ) ) {
         //Latvian lv_LV
         return "lv_LV";
      }else if("ln".equals( locale ) ) {
         //Lingala ln
         return "ln";
      }else if("lt_LT".equals( locale ) ) {
         //Lithuanian lt_LT
         return "lt_LT";
      }else if("mk_MK".equals( locale ) ) {
        // Macedonian mk_MK
         return "mk_MK";
      }else if("mg".equals( locale ) ) {
         //Malagasy mg
         return "mg";
      }else if("ms_MY".equals( locale ) ) {
         //Malay ms_MY
         return "ms_MY";
      }else if("ml".equals( locale ) ) {
         //Malayalam ml
         return "ml";
      }else if("ms_BN".equals( locale ) ) {
         //Malay( Brunei Darussalam ) ms_BN
         return "ms_BN";
      }else if("ms_MY".equals( locale ) ) {
         //Malay( Malaysia ) ms_MY
         return "ms_MY";
      }else if("mt_MT".equals( locale ) ) {
         //Maltese mt_MT
         return "mt_MT";
      }else if("mni".equals( locale ) ) {
         //Manipuri
         return "mni";
      }else if("mi".equals( locale ) ) {
         //Maori mi
         return "mi";
      }else if("mr_IN".equals( locale ) ) {
         //Marathi mr_IN
         return "mr_IN";
      }else if("mo".equals( locale ) ) {
         //Moldavian mo
         return "mo";
      }else if("mn".equals( locale ) ) {
         //Mongolian mn
         return "mn";
      }else if("na".equals( locale ) ) {
         //Nauru na
         return "na";
      }else if("ne_NP".equals( locale ) ) {
         //Nepali ne_NP
         return "ne_NP";
      }else if("ne_IN".equals( locale ) ) {
         //Nepali( India ) ne_IN
         return "ne_IN";
      }else if("nb_NO".equals( locale ) ) {
         //Norwegian( Bokmal ) nb_NO
         return "nb_NO";
      }else if("nn_NO".equals( locale ) ) {
        // Norwegian( Nynorsk ) nn_NO
         return "nn_NO";
      }else if("oc".equals( locale ) ) {
         //Occitan oc
         return "oc";
      }else if("or".equals( locale ) ) {
         //Oriya or
         return "or";
      }else if("om".equals( locale ) ) {
         //( Afan ) Oromo om
         return "om";
      }else if("ps".equals( locale ) ) {
         //Pashto , Pushto ps
         return "ps";
      }else if("pl_PL".equals( locale ) ) {
         //Polish pl_PL
         return "pl_PL";
      }else if("pt_PT".equals( locale ) ) {
         //Portuguese pt_PT
         return "pt_PT";
      }else if("pt_BR".equals( locale ) ) {
         //Portuguese( Brazilian ) pt_BR
         return "pt_BR";
      }else if("pa".equals( locale ) ) {
         //Punjabi pa
         return "pa";
      }else if("qu".equals( locale ) ) {
         //Quechua qu
         return "qu";
      }else if("rm".equals( locale ) ) {
         //Rhaeto - Romance rm
         return "rm";
      }else if("ro_RO".equals( locale ) ) {
         //Romanian ro_RO
         return "ro_RO";
      }else if("ru_RU".equals( locale ) ) {
        // Russian ru_RU
         return "ru_RU";
      }else if("u_UA".equals( locale ) ) {
        // Russian( Ukraine ) ru_UA
         return "u_UA";
      }else if("se_NO".equals( locale ) ) {
         //Northern Sami se_NO
         return "se_NO";
      }else if("sm".equals( locale ) ) {
         //Samoan sm
         return "sm";
      }else if("sg".equals( locale ) ) {
        // Sangho sg
         return "sg";
      }else if("sa".equals( locale ) ) {
         //Sanskrit sa
         return "sa";
      }else if("gd".equals( locale ) ) {
         //Scots Gaelic gd
         return "gd";
      }else if("sr_SR".equals( locale ) ) {
         //Serbian sr_SR
         return "sr_SR";
      }else if("sr_SR".equals( locale ) ) {
         //Serbian( Cyrillic ) sr_SR
         return "sr_SR";
      }else if("sr_SR".equals( locale ) ) {
        // Serbian( Latin ) sr_SR @latin
         return "sr_SR";
      }else if("sh".equals( locale ) ) {
         //Serbo - Croatian sh
         return "sh";
      }else if("st".equals( locale ) ) {
         //Sesotho st
         return "st";
      }else if("tn".equals( locale ) ) {
         //Setswana tn
         return "tn";
      }else if("sn".equals( locale ) ) {
         //Shona sn
         return "sn";
      }else if("sd".equals( locale ) ) {
         //Sindhi sd
         return "sd";
      }else if("si".equals( locale ) ) {
         //Sinhalese si
         return "si";
      }else if("ss".equals( locale ) ) {
         //Siswati ss
         return "ss";
      }else if("sk_SK".equals( locale ) ) {
         //Slovak sk_SK
         return "sk_SK";
      }else if("sl_SI".equals( locale ) ) {
        // Slovenian sl_SI
         return "sl_SI";
      }else if("so".equals( locale ) ) {
         //Somali so
         return "so";
      }else if("es_ES".equals( locale ) ) {
         //Spanish es_ES
         return "es_ES";
      }else if("es_AR".equals( locale ) ) {
         //Spanish( Argentina ) es_AR
         return "es_AR";
      }else if("es_BO".equals( locale ) ) {
         //Spanish( Bolivia ) es_BO
         return "es_BO";
      }else if("es_CL".equals( locale ) ) {
         //Spanish( Chile ) es_CL
         return "es_CL";
      }else if("es_CO".equals( locale ) ) {
         //Spanish( Colombia ) es_CO
         return "es_CO";
      }else if("es_CR".equals( locale ) ) {
         //Spanish( Costa Rica ) es_CR
         return "es_CR";
      }else if("es_DO".equals( locale ) ) {
         //Spanish( Dominican republic ) es_DO
         return "es_DO";
      }else if("es_EC".equals( locale ) ) {
         //Spanish( Ecuador ) es_EC
         return "es_EC";
      }else if("es_SV".equals( locale ) ) {
         //Spanish( El Salvador ) es_SV
         return "es_SV";
      }else if("es_GT".equals( locale ) ) {
         //Spanish( Guatemala ) es_GT
         return "es_GT";
      }else if("es_HN".equals( locale ) ) {
         //Spanish( Honduras ) es_HN
         return "es_HN";
      }else if("es_MX".equals( locale ) ) {
         //Spanish( Mexican ) es_MX
         return "es_MX";
      }else if("es_ES".equals( locale ) ) {
         //Spanish( Modern ) es_ES
         return "es_ES";
      }else if("es_NI".equals( locale ) ) {
         //Spanish( Nicaragua ) es_NI
         return "es_NI";
      }else if("es_PA".equals( locale ) ) {
         //Spanish( Panama ) es_PA
         return "es_PA";
      }else if("es_PY".equals( locale ) ) {
         //Spanish( Paraguay ) es_PY
         return "es_PY";
      }else if("es_PE".equals( locale ) ) {
         //Spanish( Peru ) es_PE
         return "es_PE";
      }else if("es_PR".equals( locale ) ) {
         //Spanish( Puerto Rico ) es_PR
         return "es_PR";
      }else if("es_UY".equals( locale ) ) {
         //Spanish( Uruguay ) es_UY
         return "es_UY";
      }else if("es_US".equals( locale ) ) {
         //Spanish( U.S. ) es_US
         return "es_US";
      }else if("es_VE".equals( locale ) ) {
         //Spanish( Venezuela ) es_VE
         return "es_VE";
      }else if("su".equals( locale ) ) {
         //Sundanese su
         return "su";
      }else if("".equals( locale ) ) {
         //Swahili sw_KE
         return "sw_KE";
      }else if("sv_SE".equals( locale ) ) {
         //Swedish sv_SE
         return "sv_SE";
      }else if("sv_FI".equals( locale ) ) {
         //Swedish( Finland ) sv_FI
         return "sv_FI";
      }else if("tl_PH".equals( locale ) ) {
         //Tagalog tl_PH
         return "tl_PH";
      }else if("tg".equals( locale ) ) {
         //Tajik tg
         return "tg";
      }else if("ta".equals( locale ) ) {
         //Tamil ta
         return "ta";
      }else if("tt".equals( locale ) ) {
         //Tatar tt
         return "tt";
      }else if("te".equals( locale ) ) {
         //Telugu te
         return "te";
      }else if("th_TH".equals( locale ) ) {
        // Thai th_TH
         return "th_TH";
      }else if("bo".equals( locale ) ) {
        // Tibetan bo
         return "bo";
      }else if("ti".equals( locale ) ) {
         //Tigrinya ti
         return "ti";
      }else if("to".equals( locale ) ) {
         //Tonga to
         return "to";
      }else if("ts".equals( locale ) ) {
         //Tsonga ts
         return "ts";
      }else if("tr_TR".equals( locale ) ) {
         //Turkish tr_TR
         return "tr_TR";
      }else if("tk".equals( locale ) ) {
         //Turkmen tk
         return "tk";
      }else if("tw".equals( locale ) ) {
         //Twi tw
         return "tw";
      }else if("ug".equals( locale ) ) {
         //Uighur ug
         return "ug";
      }else if("uk_UA".equals( locale ) ) {
         //Ukrainian uk_UA
         return "uk_UA";
      }else if("ur".equals( locale ) ) {
         //Urdu ur
         return "ur";
      }else if("ur_IN".equals( locale ) ) {
        // Urdu( India ) ur_IN
         return "ur_IN";
      }else if("ur_PK".equals( locale ) ) {
         //Urdu( Pakistan ) ur_PK
         return "ur_PK";
      }else if("uz".equals( locale ) ) {
         //Uzbek uz
         return "uz";
      }else if("uz".equals( locale ) ) {
         //Uzbek( Cyrillic ) uz
         return "uz";
      }else if("uz".equals( locale ) ) {
        // Uzbek( Latin ) uz
         return "uz";
      }else if("ca_ES".equals( locale ) ) {
         //Valencian ca_ES @valencia
         return "ca_ES";
      }else if("vi_VN".equals( locale ) ) {
         //Vietnamese vi_VN
         return "vi_VN";
      }else if("vo".equals( locale ) ) {
         //Volapuk vo
         return "vo";
      }else if("cy".equals( locale ) ) {
        // Welsh cy
         return "cy";
      }else if("wo".equals( locale ) ) {
        // Wolof wo
         return "wo";
      }else if("".equals( locale ) ) {
        // Xhosa xh
         return "xh";
      }else if("yi".equals( locale ) ) {
         //Yiddish yi
         return "yi";
      }else if("yo".equals( locale ) ) {
         //Yoruba yo
         return "yo";
      }else if("za".equals( locale ) ) {
         //Zhuang za
         return "za";
      }else if("zu".equals( locale ) ) {
         //Zulu zu
         return "zu";
      }
         //English( U.S. ) en_US
         return "en_US";
     
   }
   
   public static void changeLocale( Resources resources ,  String locale){

      Configuration config;
      config = new Configuration(resources.getConfiguration());

      switch(locale){
         case "es":
            config.locale = new Locale("es" );
            break;
         case "en":
            config.locale = Locale.ENGLISH;
            break;
         default:
            config.locale = new Locale("en_US");
      }
      resources.updateConfiguration(config, resources.getDisplayMetrics( ));
   }
   
   @Override
   public boolean equals(Object o) {

      if (this == o)
         return true;
      if (o == null)
         return false;
      if (getClass() != o.getClass())
         return false;

      // field comparison
      return Objects.equals("es_ES", locale)
         && Objects.equals("en_EN", locale);
   }
   
}
